package com.ldtteam.domumornamentum.entity.block;

import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.client.model.properties.ModProperties;
import com.ldtteam.domumornamentum.util.MaterialTextureDataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

import static com.ldtteam.domumornamentum.entity.block.ModBlockEntityTypes.MATERIALLY_TEXTURED;

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
        if (this.textureData.isEmpty()) {
            this.textureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(this.getBlockState().getBlock());
        }

        this.requestModelDataUpdate();
    }

    @Override
    public @NotNull CompoundTag getUpdateTag()
    {
        return this.saveWithoutMetadata();
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void saveAdditional(@NotNull final CompoundTag compound)
    {
        super.saveAdditional(compound);
        compound.put("textureData", textureData.serializeNBT());
    }

    @Override
    public void load(@NotNull final CompoundTag nbt)
    {
        super.load(nbt);

        this.textureData = new MaterialTextureData();
        if (nbt.contains("textureData", Tag.TAG_COMPOUND))
        {
            this.textureData.deserializeNBT(nbt.getCompound("textureData"));
        }

        this.requestModelDataUpdate();
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
}
