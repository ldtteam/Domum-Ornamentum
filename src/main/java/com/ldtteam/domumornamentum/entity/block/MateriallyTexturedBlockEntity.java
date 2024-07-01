package com.ldtteam.domumornamentum.entity.block;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.client.model.properties.ModProperties;
import com.ldtteam.domumornamentum.component.ModDataComponents;
import com.ldtteam.domumornamentum.util.MaterialTextureDataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

import static com.ldtteam.domumornamentum.entity.block.ModBlockEntityTypes.MATERIALLY_TEXTURED;
import static com.ldtteam.domumornamentum.util.Constants.BLOCK_ENTITY_TEXTURE_DATA;

public class MateriallyTexturedBlockEntity extends BlockEntity implements IMateriallyTexturedBlockEntity
{

    private MaterialTextureData textureData = MaterialTextureData.EMPTY;

    public MateriallyTexturedBlockEntity(BlockPos pos, BlockState state)
    {
        super(MATERIALLY_TEXTURED.get(), pos, state);
    }

    @Override
    public void updateTextureDataWith(final MaterialTextureData materialTextureData)
    {
        this.textureData = materialTextureData;

        if (this.textureData.isEmpty())
        {
            this.textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(this.getBlockState().getBlock());
        }
        else if (getBlockState().getBlock() instanceof final IMateriallyTexturedBlock materiallyTexturedBlock)
        {
            textureData = textureData.retainComponentsFromBlock(materiallyTexturedBlock);
        }

        this.requestModelDataUpdate();
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(final HolderLookup.Provider provider)
    {
        return this.saveWithoutMetadata(provider);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void saveAdditional(@NotNull final CompoundTag compound, final HolderLookup.Provider provider)
    {
        super.saveAdditional(compound, provider);
        // this is still needed even with data components as of 1.21
        compound.put(BLOCK_ENTITY_TEXTURE_DATA, textureData.serializeNBT());
    }

    @Override
    public void loadAdditional(@NotNull final CompoundTag nbt, final HolderLookup.Provider provider)
    {
        super.loadAdditional(nbt, provider);
        // keep this as DFU
        updateTextureDataWith(MaterialTextureData.deserializeFromNBT(nbt.getCompound(BLOCK_ENTITY_TEXTURE_DATA)));
    }

    @Override
    public void requestModelDataUpdate()
    {
        super.requestModelDataUpdate();

        // manually ask level to recompile rendering
        if (level != null && level.isClientSide)
        {
            level.setBlocksDirty(worldPosition, Blocks.AIR.defaultBlockState(), getBlockState());
        }
    }

    @Override
    public void onLoad()
    {
        // noop (dont call requestModelDataUpdate)
    }

    @NotNull
    @Override
    public ModelData getModelData()
    {
        return ModelData.builder()
          .with(ModProperties.MATERIAL_TEXTURE_PROPERTY, this.textureData)
          .build();
    }

    @Override
    @NotNull
    public MaterialTextureData getTextureData()
    {
        return textureData;
    }

    @Override
    protected void applyImplicitComponents(final BlockEntity.DataComponentInput componentInput)
    {
        super.applyImplicitComponents(componentInput);
        updateTextureDataWith(componentInput.getOrDefault(ModDataComponents.TEXTURE_DATA, MaterialTextureData.EMPTY));
    }

    @Override
    protected void collectImplicitComponents(final DataComponentMap.Builder componentBuilder)
    {
        super.collectImplicitComponents(componentBuilder);
        componentBuilder.set(ModDataComponents.TEXTURE_DATA, textureData);
    }

    @Override
    public void removeComponentsFromTag(final CompoundTag itemStackTag)
    {
        itemStackTag.remove(BLOCK_ENTITY_TEXTURE_DATA);
    }
}
