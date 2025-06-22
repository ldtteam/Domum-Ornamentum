package com.ldtteam.domumornamentum.entity.block;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.decorative.DynamicTimberFrameBlock;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.client.model.properties.ModProperties;
import com.ldtteam.domumornamentum.client.render.ModRenderTypes;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.ldtteam.domumornamentum.entity.block.ModBlockEntityTypes.DYNAMIC_TIMBERFRAME;

public class DynamicTimberFrameBlockEntity extends BlockEntity implements IMateriallyTexturedBlockEntity
{
    /**
     * Cached resmap.
     */
    private MaterialTextureData textureDataCache = new MaterialTextureData(Map.of());

    private final Map<ResourceLocation, Block> textureMapping = new TreeMap<>();

    // Outline blocks
    /*north-up
    north-down
    south-up
    south-down
    east-down
    east-up
    west-down
    west-up
    north-east-down
    north-east-up
    north-east-corner
    north-west-down
    north-west-up
    north-west-corner
    south-east-down
    south-east-up
    south-east-corner
    south-west-down
    south-west-up
    south-west-corner


    // Center blocks
    center
    bottom-center
    bottom-south-center
    bottom-north-center
    top-north-center
    top-south-center
    top-center
    north-west-center
    north-east-center
    north-west-up-center
    north-east-up-center
    north-east-down-center
    north-west-down-center
    south-east-center
    south-west-center
    south-west-up-center
    south-east-up-center
    south-east-down-center
    south-west-down-center
    east-north-center
    east-south-center
    east-south-up-center
    east-north-up-center
    east-south-down-center
    east-north-down-center
    west-north-center
    west-south-center
    west-south-up-center
    west-north-up-center
    west-south-down-center
    west-north-down-center

     */

    private Object2BooleanOpenHashMap<DynamicTimberFrameBlock.Offset> offsets = new Object2BooleanOpenHashMap<>();

    private Block primaryBlock;
    private Block secondaryBlock;

    /**
     * If we did a double check after startup.
     */
    private boolean checkedAfterStartup = false;

    public DynamicTimberFrameBlockEntity(BlockPos pos, BlockState state)
    {
        super(DYNAMIC_TIMBERFRAME.get(), pos, state);

        textureMapping.put(new ResourceLocation("block/white_wool"), Blocks.OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/orange_wool"), Blocks.OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/magenta_wool"), Blocks.OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/light_blue_wool"), Blocks.OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/yellow_wool"), Blocks.OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/lime_wool"), Blocks.OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/pink_wool"), Blocks.OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/gray_wool"), Blocks.OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/light_gray_wool"), Blocks.OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/cyan_wool"), Blocks.OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/purple_wool"), Blocks.OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/blue_wool"), Blocks.OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/brown_wool"), Blocks.OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/green_wool"), Blocks.OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/red_wool"), Blocks.OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/black_wool"), Blocks.OAK_PLANKS);

        textureMapping.put(new ResourceLocation("block/white_terracotta"), Blocks.OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/orange_terracotta"), Blocks.OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/magenta_terracotta"), Blocks.OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/light_blue_terracotta"), Blocks.OAK_PLANKS);


        textureMapping.put(new ResourceLocation("block/yellow_terracotta"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/lime_terracotta"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/pink_terracotta"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/gray_terracotta"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/light_gray_terracotta"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/cyan_terracotta"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/purple_terracotta"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/blue_terracotta"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/brown_terracotta"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/green_terracotta"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/red_terracotta"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/black_terracotta"), Blocks.DARK_OAK_PLANKS);

        textureMapping.put(new ResourceLocation("block/white_concrete"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/orange_concrete"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/magenta_concrete"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/light_blue_concrete"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/yellow_concrete"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/lime_concrete"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/pink_concrete"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/gray_concrete"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/light_gray_concrete"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/cyan_concrete"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/purple_concrete"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/blue_concrete"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/brown_concrete"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/green_concrete"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/red_concrete"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/black_concrete"), Blocks.DARK_OAK_PLANKS);

        textureMapping.put(new ResourceLocation("block/glowstone"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/noteblock"), Blocks.DARK_OAK_PLANKS);
        textureMapping.put(new ResourceLocation("block/shroomlight"), Blocks.DARK_OAK_PLANKS);

        primaryBlock = Blocks.OAK_PLANKS;
        secondaryBlock = Blocks.DARK_OAK_PLANKS;
    }

    @Override
    public @NotNull CompoundTag getUpdateTag()
    {
        return this.saveWithId();
    }

    @Override
    public void onDataPacket(final Connection net, final ClientboundBlockEntityDataPacket packet)
    {
        this.load(Objects.requireNonNull(packet.getTag()));
    }

    @Override
    public void handleUpdateTag(final CompoundTag tag)
    {
        this.load(tag);
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
        compound.put("textureData", textureDataCache.serializeNBT());
        compound.putString("primaryBlock", ForgeRegistries.BLOCKS.getKey(primaryBlock).toString());
        compound.putString("secondaryBlock", ForgeRegistries.BLOCKS.getKey(secondaryBlock).toString());
    }

    @Override
    public void load(@NotNull final CompoundTag nbt)
    {
        super.load(nbt);

        this.textureDataCache = new MaterialTextureData();
        if (nbt.contains("textureData", Tag.TAG_COMPOUND))
        {
            this.textureDataCache.deserializeNBT(nbt.getCompound("textureData"));
        }

        final ResourceLocation primaryBlockName = new ResourceLocation(nbt.getString("primaryBlock"));
        if (ForgeRegistries.BLOCKS.getValue(primaryBlockName) != Blocks.AIR)
        {
            this.primaryBlock = ForgeRegistries.BLOCKS.getValue(primaryBlockName);
        }

        final ResourceLocation secondaryBlockName = new ResourceLocation(nbt.getString("secondaryBlock"));
        if (ForgeRegistries.BLOCKS.getValue(secondaryBlockName) != Blocks.AIR)
        {
            this.secondaryBlock = ForgeRegistries.BLOCKS.getValue(secondaryBlockName);
        }

        this.requestModelDataUpdate();
    }

    @Override
    public void updateTextureDataWith(final MaterialTextureData materialTextureData)
    {
        primaryBlock = materialTextureData.getTexturedComponents().get(new ResourceLocation("block/oak_planks"));
        secondaryBlock = materialTextureData.getTexturedComponents().get(new ResourceLocation("block/dark_oak_planks"));
    }

    @Override
    public @NotNull MaterialTextureData getTextureData()
    {
        return textureDataCache;
    }

    public void refreshTextureCache()
    {
        for (final Object2BooleanMap.Entry<DynamicTimberFrameBlock.Offset> offset : offsets.object2BooleanEntrySet())
        {
            switch (offset.getKey())
            {
                case UP ->
                {
                    textureMapping.put(new ResourceLocation("block/white_wool"), offset.getBooleanValue() ? Blocks.AIR : primaryBlock);
                    textureMapping.put(new ResourceLocation("block/magenta_wool"), offset.getBooleanValue() ? Blocks.AIR : primaryBlock);
                    textureMapping.put(new ResourceLocation("block/lime_wool"), offset.getBooleanValue() ? Blocks.AIR : primaryBlock);
                    textureMapping.put(new ResourceLocation("block/gray_wool"), offset.getBooleanValue() ? Blocks.AIR : primaryBlock);
                }
                case DOWN ->
                {
                    textureMapping.put(new ResourceLocation("block/orange_wool"), offset.getBooleanValue() ? Blocks.AIR : primaryBlock);
                    textureMapping.put(new ResourceLocation("block/light_blue_wool"), offset.getBooleanValue() ? Blocks.AIR : primaryBlock);
                    textureMapping.put(new ResourceLocation("block/yellow_wool"), offset.getBooleanValue() ? Blocks.AIR : primaryBlock);
                    textureMapping.put(new ResourceLocation("block/pink_wool"), offset.getBooleanValue() ? Blocks.AIR : primaryBlock);
                }
            }
        }


        this.textureDataCache = new MaterialTextureData(textureMapping);
        this.requestModelDataUpdate();
        if (level != null)
        {
            level.sendBlockUpdated(getBlockPos(), Blocks.AIR.defaultBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    @NotNull
    @Override
    public ModelData getModelData()
    {
        if (!checkedAfterStartup && level != null)
        {
            checkedAfterStartup = true;
            refreshTextureCache();
        }

        return ModelData.builder()
            .with(ModProperties.MATERIAL_TEXTURE_PROPERTY, textureDataCache)
            .build();
    }

    public void onNeighborUpdate(final DynamicTimberFrameBlock.Offset offset, final boolean added)
    {
        this.offsets.put(offset, added);
        refreshTextureCache();
    }
}
