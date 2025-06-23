package com.ldtteam.domumornamentum.entity.block;

import com.ldtteam.domumornamentum.block.decorative.DynamicTimberFrameBlock;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.client.model.properties.ModProperties;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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

    /**
     * Texture mapping of position at resource location to block (air, frame or center)
     */
    private final Map<ResourceLocation, Block> textureMapping = new TreeMap<>();

    /**
     * All the offsets that there are in a block at the moment.
     */
    private Object2BooleanOpenHashMap<DynamicTimberFrameBlock.Offset> offsets = new Object2BooleanOpenHashMap<>();

    /**
     * The block materials.
     */
    private Block centerBlock;
    private Block frameBlock;

    /**
     * If we did a double check after startup.
     */
    private boolean checkedAfterStartup = false;

    public DynamicTimberFrameBlockEntity(BlockPos pos, BlockState state)
    {
        super(DYNAMIC_TIMBERFRAME.get(), pos, state);
        centerBlock = Blocks.OAK_PLANKS;
        frameBlock = Blocks.DARK_OAK_PLANKS;
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
        compound.putString("primaryBlock", ForgeRegistries.BLOCKS.getKey(centerBlock).toString());
        compound.putString("secondaryBlock", ForgeRegistries.BLOCKS.getKey(frameBlock).toString());
        final ListTag listTag = new ListTag();
        for (final Object2BooleanMap.Entry<DynamicTimberFrameBlock.Offset> mapEntry : offsets.object2BooleanEntrySet())
        {
            final CompoundTag localCompound = new CompoundTag();
            localCompound.putInt("offset", mapEntry.getKey().ordinal());
            localCompound.putBoolean("bool", mapEntry.getBooleanValue());
            listTag.add(localCompound);
        }
        compound.put("offsets", listTag);
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
            this.centerBlock = ForgeRegistries.BLOCKS.getValue(primaryBlockName);
        }

        final ResourceLocation secondaryBlockName = new ResourceLocation(nbt.getString("secondaryBlock"));
        if (ForgeRegistries.BLOCKS.getValue(secondaryBlockName) != Blocks.AIR)
        {
            this.frameBlock = ForgeRegistries.BLOCKS.getValue(secondaryBlockName);
        }

        offsets.clear();
        for (final Tag tag : nbt.getList("offsets", Tag.TAG_COMPOUND))
        {
            final CompoundTag compoundTag = (CompoundTag) tag;
            offsets.put(DynamicTimberFrameBlock.Offset.values()[compoundTag.getInt("offset")], compoundTag.getBoolean("bool"));
        }

        if (level != null && level.isClientSide)
        {
            refreshTextureCache();
        }
    }

    @Override
    public void updateTextureDataWith(final MaterialTextureData materialTextureData)
    {
        centerBlock = materialTextureData.getTexturedComponents().get(new ResourceLocation("block/oak_planks"));
        frameBlock = materialTextureData.getTexturedComponents().get(new ResourceLocation("block/dark_oak_planks"));
        handleTextureMapping();
    }

    private void handleTextureMapping()
    {
        textureMapping.put(new ResourceLocation("block/white_wool"), frameBlock);
        textureMapping.put(new ResourceLocation("block/orange_wool"), frameBlock);
        textureMapping.put(new ResourceLocation("block/magenta_wool"), frameBlock);
        textureMapping.put(new ResourceLocation("block/light_blue_wool"), frameBlock);
        textureMapping.put(new ResourceLocation("block/yellow_wool"), frameBlock);
        textureMapping.put(new ResourceLocation("block/lime_wool"), frameBlock);
        textureMapping.put(new ResourceLocation("block/pink_wool"), frameBlock);
        textureMapping.put(new ResourceLocation("block/gray_wool"), frameBlock);
        textureMapping.put(new ResourceLocation("block/light_gray_wool"), frameBlock);
        textureMapping.put(new ResourceLocation("block/cyan_wool"), frameBlock);
        textureMapping.put(new ResourceLocation("block/purple_wool"), frameBlock);
        textureMapping.put(new ResourceLocation("block/blue_wool"), frameBlock);
        textureMapping.put(new ResourceLocation("block/brown_wool"), frameBlock);
        textureMapping.put(new ResourceLocation("block/green_wool"), frameBlock);
        textureMapping.put(new ResourceLocation("block/red_wool"), frameBlock);
        textureMapping.put(new ResourceLocation("block/black_wool"), frameBlock);

        textureMapping.put(new ResourceLocation("block/white_terracotta"), frameBlock);
        textureMapping.put(new ResourceLocation("block/orange_terracotta"), frameBlock);
        textureMapping.put(new ResourceLocation("block/magenta_terracotta"), frameBlock);
        textureMapping.put(new ResourceLocation("block/light_blue_terracotta"), frameBlock);


        textureMapping.put(new ResourceLocation("block/yellow_terracotta"), centerBlock);
        textureMapping.put(new ResourceLocation("block/lime_terracotta"), centerBlock);
        textureMapping.put(new ResourceLocation("block/pink_terracotta"), centerBlock);
        textureMapping.put(new ResourceLocation("block/gray_terracotta"), centerBlock);
        textureMapping.put(new ResourceLocation("block/light_gray_terracotta"), centerBlock);
        textureMapping.put(new ResourceLocation("block/cyan_terracotta"), centerBlock);
        textureMapping.put(new ResourceLocation("block/purple_terracotta"), centerBlock);
        textureMapping.put(new ResourceLocation("block/blue_terracotta"), Blocks.AIR);
        textureMapping.put(new ResourceLocation("block/brown_terracotta"), Blocks.AIR);
        textureMapping.put(new ResourceLocation("block/green_terracotta"), Blocks.AIR);
        textureMapping.put(new ResourceLocation("block/red_terracotta"), Blocks.AIR);
        textureMapping.put(new ResourceLocation("block/black_terracotta"), Blocks.AIR);

        textureMapping.put(new ResourceLocation("block/white_concrete"), Blocks.AIR);
        textureMapping.put(new ResourceLocation("block/orange_concrete"), Blocks.AIR);
        textureMapping.put(new ResourceLocation("block/magenta_concrete"), Blocks.AIR);
        textureMapping.put(new ResourceLocation("block/light_blue_concrete"), Blocks.AIR);
        textureMapping.put(new ResourceLocation("block/yellow_concrete"), Blocks.AIR);
        textureMapping.put(new ResourceLocation("block/lime_concrete"), Blocks.AIR);
        textureMapping.put(new ResourceLocation("block/pink_concrete"), Blocks.AIR);
        textureMapping.put(new ResourceLocation("block/gray_concrete"), Blocks.AIR);
        textureMapping.put(new ResourceLocation("block/light_gray_concrete"), Blocks.AIR);
        textureMapping.put(new ResourceLocation("block/cyan_concrete"), Blocks.AIR);
        textureMapping.put(new ResourceLocation("block/purple_concrete"), Blocks.AIR);
        textureMapping.put(new ResourceLocation("block/blue_concrete"), Blocks.AIR);
        textureMapping.put(new ResourceLocation("block/brown_concrete"), Blocks.AIR);
        textureMapping.put(new ResourceLocation("block/green_concrete"), Blocks.AIR);
        textureMapping.put(new ResourceLocation("block/red_concrete"), Blocks.AIR);
        textureMapping.put(new ResourceLocation("block/black_concrete"), Blocks.AIR);

        textureMapping.put(new ResourceLocation("block/glowstone"), Blocks.AIR);
        textureMapping.put(new ResourceLocation("block/cherry_planks"), Blocks.AIR);
        textureMapping.put(new ResourceLocation("block/shroomlight"), Blocks.AIR);
    }

    @Override
    public @NotNull MaterialTextureData getTextureData()
    {
        return textureDataCache;
    }

    public void refreshTextureCache()
    {
        // Reset and recalculate.
        textureMapping.clear();
        handleTextureMapping();

        for (final Object2BooleanMap.Entry<DynamicTimberFrameBlock.Offset> offset : offsets.object2BooleanEntrySet())
        {
            if (!offset.getBooleanValue())
            {
                continue;
            }
            switch (offset.getKey())
            {
                case UP ->
                {
                    textureMapping.put(new ResourceLocation("block/white_wool"), Blocks.AIR);
                    textureMapping.put(new ResourceLocation("block/magenta_wool"), Blocks.AIR);
                    textureMapping.put(new ResourceLocation("block/lime_wool"), Blocks.AIR);
                    textureMapping.put(new ResourceLocation("block/gray_wool"), Blocks.AIR);
                }
                case DOWN ->
                {
                    textureMapping.put(new ResourceLocation("block/orange_wool"), Blocks.AIR);
                    textureMapping.put(new ResourceLocation("block/light_blue_wool"), Blocks.AIR);
                    textureMapping.put(new ResourceLocation("block/yellow_wool"), Blocks.AIR);
                    textureMapping.put(new ResourceLocation("block/pink_wool"), Blocks.AIR);
                }
                case EAST ->
                {
                    // North East Corner
                    textureMapping.put(new ResourceLocation("block/purple_wool"), Blocks.AIR);
                    // South East Corner
                    textureMapping.put(new ResourceLocation("block/white_terracotta"), Blocks.AIR);

                    // Connections
                    // South East Center
                    textureMapping.put(new ResourceLocation("block/orange_concrete"), centerBlock);

                    // North East Center
                    textureMapping.put(new ResourceLocation("block/brown_terracotta"), centerBlock);
                }
                case WEST ->
                {
                    // North West Corner
                    textureMapping.put(new ResourceLocation("block/green_wool"), Blocks.AIR);
                    // South West Corner
                    textureMapping.put(new ResourceLocation("block/light_blue_terracotta"), Blocks.AIR);

                    // Connections
                    // South West Center
                    textureMapping.put(new ResourceLocation("block/magenta_concrete"), centerBlock);

                    // North West Center
                    textureMapping.put(new ResourceLocation("block/blue_terracotta"), centerBlock);
                }
                case NORTH ->
                {
                    // Both North Corners
                    textureMapping.put(new ResourceLocation("block/purple_wool"), Blocks.AIR);
                    textureMapping.put(new ResourceLocation("block/green_wool"), Blocks.AIR);

                    // Connections
                    // East North Center
                    textureMapping.put(new ResourceLocation("block/gray_concrete"), centerBlock);

                    // West North Center
                    textureMapping.put(new ResourceLocation("block/green_concrete"), centerBlock);
                }
                case SOUTH ->
                {


                    // Both South Corners
                    textureMapping.put(new ResourceLocation("block/white_terracotta"), Blocks.AIR);
                    textureMapping.put(new ResourceLocation("block/light_blue_terracotta"), Blocks.AIR);

                    // Connections
                    // East South Center
                    textureMapping.put(new ResourceLocation("block/light_gray_concrete"), centerBlock);

                    // West South Center
                    textureMapping.put(new ResourceLocation("block/red_concrete"), centerBlock);
                }
            }
        }

        if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.NORTH))
        {
            textureMapping.put(new ResourceLocation("block/white_wool"), centerBlock);
            textureMapping.put(new ResourceLocation("block/orange_wool"), centerBlock);
        }
        if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.SOUTH))
        {
            textureMapping.put(new ResourceLocation("block/magenta_wool"), centerBlock);
            textureMapping.put(new ResourceLocation("block/light_blue_wool"), centerBlock);
        }
        if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.EAST))
        {
            textureMapping.put(new ResourceLocation("block/lime_wool"), centerBlock);
            textureMapping.put(new ResourceLocation("block/yellow_wool"), centerBlock);
        }
        if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.WEST))
        {
            textureMapping.put(new ResourceLocation("block/gray_wool"), centerBlock);
            textureMapping.put(new ResourceLocation("block/pink_wool"), centerBlock);
        }

        if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.EAST) && offsets.getBoolean(DynamicTimberFrameBlock.Offset.UP))
        {
            if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.UP_EAST))
            {
                // Disable South & North East Up.
                textureMapping.put(new ResourceLocation("block/black_wool"), Blocks.AIR);
                textureMapping.put(new ResourceLocation("block/cyan_wool"), Blocks.AIR);

                // Enable South & North East Up Center
                textureMapping.put(new ResourceLocation("block/yellow_concrete"), centerBlock);
                textureMapping.put(new ResourceLocation("block/red_terracotta"), centerBlock);
            }
            textureMapping.put(new ResourceLocation("block/lime_wool"), frameBlock);
        }

        if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.NORTH) && offsets.getBoolean(DynamicTimberFrameBlock.Offset.UP))
        {
            if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.UP_NORTH))
            {
                // Disable North West & North East Up.
                textureMapping.put(new ResourceLocation("block/brown_wool"), Blocks.AIR);
                textureMapping.put(new ResourceLocation("block/cyan_wool"), Blocks.AIR);

                // Enable North West & North East Up Center
                textureMapping.put(new ResourceLocation("block/glowstone"), centerBlock);
                textureMapping.put(new ResourceLocation("block/purple_concrete"), centerBlock);
            }
            textureMapping.put(new ResourceLocation("block/white_wool"), frameBlock);
        }

        if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.SOUTH) && offsets.getBoolean(DynamicTimberFrameBlock.Offset.UP))
        {
            if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.UP_SOUTH))
            {
                // Disable South West & South East Up.
                textureMapping.put(new ResourceLocation("block/magenta_terracotta"), Blocks.AIR);
                textureMapping.put(new ResourceLocation("block/black_wool"), Blocks.AIR);

                // Enable South West & South East Up Center
                textureMapping.put(new ResourceLocation("block/cyan_concrete"), centerBlock);
                textureMapping.put(new ResourceLocation("block/black_concrete"), centerBlock);
            }
            textureMapping.put(new ResourceLocation("block/magenta_wool"), frameBlock);
        }

        if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.WEST) && offsets.getBoolean(DynamicTimberFrameBlock.Offset.UP))
        {
            if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.UP_WEST))
            {
                // Disable South & North West Up.
                textureMapping.put(new ResourceLocation("block/brown_wool"), Blocks.AIR);
                textureMapping.put(new ResourceLocation("block/magenta_terracotta"), Blocks.AIR);

                // Enable South & North West Up Center
                textureMapping.put(new ResourceLocation("block/green_terracotta"), centerBlock);
                textureMapping.put(new ResourceLocation("block/light_blue_concrete"), centerBlock);
            }
            textureMapping.put(new ResourceLocation("block/gray_wool"), frameBlock);
        }

        if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.EAST) && offsets.getBoolean(DynamicTimberFrameBlock.Offset.DOWN))
        {
            if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.DOWN_EAST))
            {
                // Disable South & North East Down.
                textureMapping.put(new ResourceLocation("block/light_gray_wool"), Blocks.AIR);
                textureMapping.put(new ResourceLocation("block/red_wool"), Blocks.AIR);

                // Enable South & North East Down Center
                textureMapping.put(new ResourceLocation("block/black_terracotta"), centerBlock);
                textureMapping.put(new ResourceLocation("block/lime_concrete"), centerBlock);
            }
            textureMapping.put(new ResourceLocation("block/yellow_wool"), frameBlock);
        }

        if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.NORTH) && offsets.getBoolean(DynamicTimberFrameBlock.Offset.DOWN))
        {
            if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.DOWN_NORTH))
            {
                // Disable North West & North East Down.
                textureMapping.put(new ResourceLocation("block/blue_wool"), Blocks.AIR);
                textureMapping.put(new ResourceLocation("block/light_gray_wool"), Blocks.AIR);

                // Enable North West & North East Down Center
                textureMapping.put(new ResourceLocation("block/shroomlight"), centerBlock);
                textureMapping.put(new ResourceLocation("block/brown_concrete"), centerBlock);
            }
            textureMapping.put(new ResourceLocation("block/orange_wool"), frameBlock);
        }

        if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.SOUTH) && offsets.getBoolean(DynamicTimberFrameBlock.Offset.DOWN))
        {
            if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.DOWN_SOUTH))
            {
                // Disable South West & South East Down.
                textureMapping.put(new ResourceLocation("block/orange_terracotta"), Blocks.AIR);
                textureMapping.put(new ResourceLocation("block/red_wool"), Blocks.AIR);

                // Enable South West & South East Down Center
                textureMapping.put(new ResourceLocation("block/cherry_planks"), centerBlock);
                textureMapping.put(new ResourceLocation("block/blue_concrete"), centerBlock);
            }
            textureMapping.put(new ResourceLocation("block/light_blue_wool"), frameBlock);
        }

        if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.WEST) && offsets.getBoolean(DynamicTimberFrameBlock.Offset.DOWN))
        {
            if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.DOWN_WEST))
            {
                // Disable South & North West Down.
                textureMapping.put(new ResourceLocation("block/orange_terracotta"), Blocks.AIR);
                textureMapping.put(new ResourceLocation("block/blue_wool"), Blocks.AIR);

                // Enable South & North West Down Center
                textureMapping.put(new ResourceLocation("block/pink_concrete"), centerBlock);
                textureMapping.put(new ResourceLocation("block/white_concrete"), centerBlock);
            }
            textureMapping.put(new ResourceLocation("block/pink_wool"), frameBlock);
        }

        this.textureDataCache = new MaterialTextureData(textureMapping);
        this.requestModelDataUpdate();
        if (level != null)
        {
            setChanged();
            level.getChunk(worldPosition.getX() >> 4, worldPosition.getZ() >> 4).setUnsaved(true);
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

    public void onNeighborUpdate(final DynamicTimberFrameBlockEntity newNeighbor, final DynamicTimberFrameBlock.Offset offset, final boolean added)
    {
        // Only connect to frames with the same content.
        if (newNeighbor != null && (newNeighbor.frameBlock != this.frameBlock || newNeighbor.centerBlock != this.centerBlock))
        {
            return;
        }
        this.offsets.put(offset, added);
        refreshTextureCache();
    }
}
