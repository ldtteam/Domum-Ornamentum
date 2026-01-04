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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.ldtteam.domumornamentum.entity.block.ModBlockEntityTypes.DYNAMIC_TIMBERFRAME;

public class DynamicTimberFrameBlockEntity extends AbstractMateriallyTexturedBlockEntity
{
    // Frame (wool)
    public static final ResourceLocation NORTH_UP          = new ResourceLocation("block/white_wool");
    public static final ResourceLocation NORTH_DOWN        = new ResourceLocation("block/orange_wool");
    public static final ResourceLocation SOUTH_UP          = new ResourceLocation("block/magenta_wool");
    public static final ResourceLocation SOUTH_DOWN        = new ResourceLocation("block/light_blue_wool");
    public static final ResourceLocation EAST_DOWN         = new ResourceLocation("block/yellow_wool");
    public static final ResourceLocation EAST_UP           = new ResourceLocation("block/lime_wool");
    public static final ResourceLocation WEST_DOWN         = new ResourceLocation("block/pink_wool");
    public static final ResourceLocation WEST_UP           = new ResourceLocation("block/gray_wool");
    public static final ResourceLocation NORTH_EAST_DOWN   = new ResourceLocation("block/light_gray_wool");
    public static final ResourceLocation NORTH_EAST_UP     = new ResourceLocation("block/cyan_wool");
    public static final ResourceLocation NORTH_EAST_CORNER = new ResourceLocation("block/purple_wool");
    public static final ResourceLocation NORTH_WEST_DOWN   = new ResourceLocation("block/blue_wool");
    public static final ResourceLocation NORTH_WEST_UP     = new ResourceLocation("block/brown_wool");
    public static final ResourceLocation NORTH_WEST_CORNER = new ResourceLocation("block/green_wool");
    public static final ResourceLocation SOUTH_EAST_DOWN   = new ResourceLocation("block/red_wool");
    public static final ResourceLocation SOUTH_EAST_UP     = new ResourceLocation("block/black_wool");

    // Frame (terracotta)
    public static final ResourceLocation SOUTH_EAST_CORNER = new ResourceLocation("block/white_terracotta");
    public static final ResourceLocation SOUTH_WEST_DOWN   = new ResourceLocation("block/orange_terracotta");
    public static final ResourceLocation SOUTH_WEST_UP     = new ResourceLocation("block/magenta_terracotta");
    public static final ResourceLocation SOUTH_WEST_CORNER = new ResourceLocation("block/light_blue_terracotta");

    // Center (terracotta)
    public static final ResourceLocation CENTER                 = new ResourceLocation("block/yellow_terracotta");
    public static final ResourceLocation BOTTOM_CENTER          = new ResourceLocation("block/lime_terracotta");
    public static final ResourceLocation BOTTOM_SOUTH_CENTER    = new ResourceLocation("block/pink_terracotta");
    public static final ResourceLocation BOTTOM_NORTH_CENTER    = new ResourceLocation("block/gray_terracotta");
    public static final ResourceLocation TOP_NORTH_CENTER       = new ResourceLocation("block/light_gray_terracotta");
    public static final ResourceLocation TOP_SOUTH_CENTER       = new ResourceLocation("block/cyan_terracotta");
    public static final ResourceLocation TOP_CENTER             = new ResourceLocation("block/purple_terracotta");
    public static final ResourceLocation NORTH_WEST_CENTER      = new ResourceLocation("block/blue_terracotta");
    public static final ResourceLocation NORTH_EAST_CENTER      = new ResourceLocation("block/brown_terracotta");
    public static final ResourceLocation NORTH_WEST_UP_CENTER   = new ResourceLocation("block/green_terracotta");
    public static final ResourceLocation NORTH_EAST_UP_CENTER   = new ResourceLocation("block/red_terracotta");
    public static final ResourceLocation NORTH_EAST_DOWN_CENTER = new ResourceLocation("block/black_terracotta");

    // Center (concrete)
    public static final ResourceLocation NORTH_WEST_DOWN_CENTER = new ResourceLocation("block/white_concrete");
    public static final ResourceLocation SOUTH_EAST_CENTER      = new ResourceLocation("block/orange_concrete");
    public static final ResourceLocation SOUTH_WEST_CENTER      = new ResourceLocation("block/magenta_concrete");
    public static final ResourceLocation SOUTH_WEST_UP_CENTER   = new ResourceLocation("block/light_blue_concrete");
    public static final ResourceLocation SOUTH_EAST_UP_CENTER   = new ResourceLocation("block/yellow_concrete");
    public static final ResourceLocation SOUTH_EAST_DOWN_CENTER = new ResourceLocation("block/lime_concrete");
    public static final ResourceLocation SOUTH_WEST_DOWN_CENTER = new ResourceLocation("block/pink_concrete");
    public static final ResourceLocation EAST_NORTH_CENTER      = new ResourceLocation("block/gray_concrete");
    public static final ResourceLocation EAST_SOUTH_CENTER      = new ResourceLocation("block/light_gray_concrete");
    public static final ResourceLocation EAST_SOUTH_UP_CENTER   = new ResourceLocation("block/cyan_concrete");
    public static final ResourceLocation EAST_NORTH_UP_CENTER   = new ResourceLocation("block/purple_concrete");
    public static final ResourceLocation EAST_SOUTH_DOWN_CENTER = new ResourceLocation("block/blue_concrete");
    public static final ResourceLocation EAST_NORTH_DOWN_CENTER = new ResourceLocation("block/brown_concrete");
    public static final ResourceLocation WEST_NORTH_CENTER      = new ResourceLocation("block/green_concrete");
    public static final ResourceLocation WEST_SOUTH_CENTER      = new ResourceLocation("block/red_concrete");
    public static final ResourceLocation WEST_SOUTH_UP_CENTER   = new ResourceLocation("block/black_concrete");
    public static final ResourceLocation WEST_NORTH_UP_CENTER   = new ResourceLocation("block/glowstone");
    public static final ResourceLocation WEST_SOUTH_DOWN_CENTER = new ResourceLocation("block/cherry_planks");
    public static final ResourceLocation WEST_NORTH_DOWN_CENTER = new ResourceLocation("block/shroomlight");

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

    /**
     * Actual texture data to use.
     */
    private @NotNull MaterialTextureData originalTextureData = new MaterialTextureData(new HashMap<>());

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
        compound.put("originalTextureData", originalTextureData.serializeNBT());

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

        if (nbt.contains("originalTextureData"))
        {
            this.originalTextureData = new MaterialTextureData();
            this.originalTextureData.deserializeNBT(nbt.getCompound("originalTextureData"));
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
        centerBlock = materialTextureData.getTexturedComponents().get(new ResourceLocation("block/dark_oak_planks"));
        frameBlock = materialTextureData.getTexturedComponents().get(new ResourceLocation("block/oak_planks"));
        handleTextureMapping();
        originalTextureData = materialTextureData;
    }

    private void handleTextureMapping()
    {
        textureMapping.put(NORTH_UP, frameBlock);
        textureMapping.put(NORTH_DOWN, frameBlock);
        textureMapping.put(SOUTH_UP, frameBlock);
        textureMapping.put(SOUTH_DOWN, frameBlock);
        textureMapping.put(EAST_DOWN, frameBlock);
        textureMapping.put(EAST_UP, frameBlock);
        textureMapping.put(WEST_DOWN, frameBlock);
        textureMapping.put(WEST_UP, frameBlock);
        textureMapping.put(NORTH_EAST_DOWN, frameBlock);
        textureMapping.put(NORTH_EAST_UP, frameBlock);
        textureMapping.put(NORTH_EAST_CORNER, frameBlock);
        textureMapping.put(NORTH_WEST_DOWN, frameBlock);
        textureMapping.put(NORTH_WEST_UP, frameBlock);
        textureMapping.put(NORTH_WEST_CORNER, frameBlock);
        textureMapping.put(SOUTH_EAST_DOWN, frameBlock);
        textureMapping.put(SOUTH_EAST_UP, frameBlock);

        textureMapping.put(SOUTH_EAST_CORNER, frameBlock);
        textureMapping.put(SOUTH_WEST_DOWN, frameBlock);
        textureMapping.put(SOUTH_WEST_UP, frameBlock);
        textureMapping.put(SOUTH_WEST_CORNER, frameBlock);

        textureMapping.put(CENTER, centerBlock);
        textureMapping.put(BOTTOM_CENTER, centerBlock);
        textureMapping.put(BOTTOM_SOUTH_CENTER, centerBlock);
        textureMapping.put(BOTTOM_NORTH_CENTER, centerBlock);
        textureMapping.put(TOP_NORTH_CENTER, centerBlock);
        textureMapping.put(TOP_SOUTH_CENTER, centerBlock);
        textureMapping.put(TOP_CENTER, centerBlock);
        textureMapping.put(NORTH_WEST_CENTER, Blocks.AIR);
        textureMapping.put(NORTH_EAST_CENTER, Blocks.AIR);
        textureMapping.put(NORTH_WEST_UP_CENTER, Blocks.AIR);
        textureMapping.put(NORTH_EAST_UP_CENTER, Blocks.AIR);
        textureMapping.put(NORTH_EAST_DOWN_CENTER, Blocks.AIR);

        textureMapping.put(NORTH_WEST_DOWN_CENTER, Blocks.AIR);
        textureMapping.put(SOUTH_EAST_CENTER, Blocks.AIR);
        textureMapping.put(SOUTH_WEST_CENTER, Blocks.AIR);
        textureMapping.put(SOUTH_WEST_UP_CENTER, Blocks.AIR);
        textureMapping.put(SOUTH_EAST_UP_CENTER, Blocks.AIR);
        textureMapping.put(SOUTH_EAST_DOWN_CENTER, Blocks.AIR);
        textureMapping.put(SOUTH_WEST_DOWN_CENTER, Blocks.AIR);
        textureMapping.put(EAST_NORTH_CENTER, Blocks.AIR);
        textureMapping.put(EAST_SOUTH_CENTER, Blocks.AIR);
        textureMapping.put(EAST_SOUTH_UP_CENTER, Blocks.AIR);
        textureMapping.put(EAST_NORTH_UP_CENTER, Blocks.AIR);
        textureMapping.put(EAST_SOUTH_DOWN_CENTER, Blocks.AIR);
        textureMapping.put(EAST_NORTH_DOWN_CENTER, Blocks.AIR);
        textureMapping.put(WEST_NORTH_CENTER, Blocks.AIR);
        textureMapping.put(WEST_SOUTH_CENTER, Blocks.AIR);
        textureMapping.put(WEST_SOUTH_UP_CENTER, Blocks.AIR);
        textureMapping.put(WEST_NORTH_UP_CENTER, Blocks.AIR);
        textureMapping.put(WEST_SOUTH_DOWN_CENTER, Blocks.AIR);
        textureMapping.put(WEST_NORTH_DOWN_CENTER, Blocks.AIR);
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
                    textureMapping.put(NORTH_UP, Blocks.AIR);
                    textureMapping.put(EAST_UP, Blocks.AIR);
                    textureMapping.put(SOUTH_UP, Blocks.AIR);
                    textureMapping.put(WEST_UP, Blocks.AIR);
                }
                case DOWN ->
                {
                    textureMapping.put(NORTH_DOWN, Blocks.AIR);
                    textureMapping.put(EAST_DOWN, Blocks.AIR);
                    textureMapping.put(SOUTH_DOWN, Blocks.AIR);
                    textureMapping.put(WEST_DOWN, Blocks.AIR);
                }
                case EAST ->
                {
                    textureMapping.put(NORTH_EAST_CORNER, Blocks.AIR);
                    textureMapping.put(SOUTH_EAST_CORNER, Blocks.AIR);

                    // Connections
                    textureMapping.put(SOUTH_EAST_CENTER, centerBlock);
                    textureMapping.put(NORTH_EAST_CENTER, centerBlock);
                }
                case WEST ->
                {
                    textureMapping.put(NORTH_WEST_CORNER, Blocks.AIR);
                    textureMapping.put(SOUTH_WEST_CORNER, Blocks.AIR);

                    // Connections
                    textureMapping.put(SOUTH_WEST_CENTER, centerBlock);
                    textureMapping.put(NORTH_WEST_CENTER, centerBlock);
                }
                case NORTH ->
                {
                    // Both North Corners
                    textureMapping.put(NORTH_EAST_CORNER, Blocks.AIR);
                    textureMapping.put(NORTH_WEST_CORNER, Blocks.AIR);

                    // Connections
                    textureMapping.put(EAST_NORTH_CENTER, centerBlock);
                    textureMapping.put(WEST_NORTH_CENTER, centerBlock);
                }
                case SOUTH ->
                {
                    // Both South Corners
                    textureMapping.put(SOUTH_EAST_CORNER, Blocks.AIR);
                    textureMapping.put(SOUTH_WEST_CORNER, Blocks.AIR);

                    // Connections
                    textureMapping.put(EAST_SOUTH_CENTER, centerBlock);
                    textureMapping.put(WEST_SOUTH_CENTER, centerBlock);
                }
            }
        }

        if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.NORTH))
        {
            textureMapping.put(NORTH_UP, centerBlock);
            textureMapping.put(NORTH_DOWN, centerBlock);
        }
        if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.SOUTH))
        {
            textureMapping.put(SOUTH_UP, centerBlock);
            textureMapping.put(SOUTH_DOWN, centerBlock);
        }
        if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.EAST))
        {
            textureMapping.put(EAST_UP, centerBlock);
            textureMapping.put(EAST_DOWN, centerBlock);
        }
        if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.WEST))
        {
            textureMapping.put(WEST_UP, centerBlock);
            textureMapping.put(WEST_DOWN, centerBlock);
        }

        if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.EAST) && offsets.getBoolean(DynamicTimberFrameBlock.Offset.UP))
        {
            if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.UP_EAST))
            {
                // Disable South & North East Up.
                textureMapping.put(SOUTH_EAST_UP, Blocks.AIR);
                textureMapping.put(NORTH_EAST_UP, Blocks.AIR);

                // Enable South & North East Up Center
                textureMapping.put(SOUTH_EAST_UP_CENTER, centerBlock);
                textureMapping.put(NORTH_EAST_UP_CENTER, centerBlock);
            }
            textureMapping.put(EAST_UP, frameBlock);
        }

        if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.NORTH) && offsets.getBoolean(DynamicTimberFrameBlock.Offset.UP))
        {
            if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.UP_NORTH))
            {
                // Disable North West & North East Up.
                textureMapping.put(NORTH_WEST_UP, Blocks.AIR);
                textureMapping.put(NORTH_EAST_UP, Blocks.AIR);

                // Enable North West & North East Up Center
                textureMapping.put(WEST_NORTH_UP_CENTER, centerBlock);
                textureMapping.put(EAST_NORTH_UP_CENTER, centerBlock);
            }
            textureMapping.put(NORTH_UP, frameBlock);
        }

        if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.SOUTH) && offsets.getBoolean(DynamicTimberFrameBlock.Offset.UP))
        {
            if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.UP_SOUTH))
            {
                // Disable South West & South East Up.
                textureMapping.put(SOUTH_WEST_UP, Blocks.AIR);
                textureMapping.put(SOUTH_EAST_UP, Blocks.AIR);

                // Enable South West & South East Up Center
                textureMapping.put(EAST_SOUTH_UP_CENTER, centerBlock);
                textureMapping.put(WEST_SOUTH_UP_CENTER, centerBlock);
            }
            textureMapping.put(SOUTH_UP, frameBlock);
        }

        if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.WEST) && offsets.getBoolean(DynamicTimberFrameBlock.Offset.UP))
        {
            if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.UP_WEST))
            {
                // Disable South & North West Up.
                textureMapping.put(NORTH_WEST_UP, Blocks.AIR);
                textureMapping.put(SOUTH_WEST_UP, Blocks.AIR);

                // Enable South & North West Up Center
                textureMapping.put(NORTH_WEST_UP_CENTER, centerBlock);
                textureMapping.put(SOUTH_WEST_UP_CENTER, centerBlock);
            }
            textureMapping.put(WEST_UP, frameBlock);
        }

        if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.EAST) && offsets.getBoolean(DynamicTimberFrameBlock.Offset.DOWN))
        {
            if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.DOWN_EAST))
            {
                // Disable South & North East Down.
                textureMapping.put(NORTH_EAST_DOWN, Blocks.AIR);
                textureMapping.put(SOUTH_EAST_DOWN, Blocks.AIR);

                // Enable South & North East Down Center
                textureMapping.put(NORTH_EAST_DOWN_CENTER, centerBlock);
                textureMapping.put(SOUTH_EAST_DOWN_CENTER, centerBlock);
            }
            textureMapping.put(EAST_DOWN, frameBlock);
        }

        if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.NORTH) && offsets.getBoolean(DynamicTimberFrameBlock.Offset.DOWN))
        {
            if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.DOWN_NORTH))
            {
                // Disable North West & North East Down.
                textureMapping.put(NORTH_WEST_DOWN, Blocks.AIR);
                textureMapping.put(NORTH_EAST_DOWN, Blocks.AIR);

                // Enable North West & North East Down Center
                textureMapping.put(WEST_NORTH_DOWN_CENTER, centerBlock);
                textureMapping.put(EAST_NORTH_DOWN_CENTER, centerBlock);
            }
            textureMapping.put(NORTH_DOWN, frameBlock);
        }

        if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.SOUTH) && offsets.getBoolean(DynamicTimberFrameBlock.Offset.DOWN))
        {
            if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.DOWN_SOUTH))
            {
                // Disable South West & South East Down.
                textureMapping.put(SOUTH_WEST_DOWN, Blocks.AIR);
                textureMapping.put(SOUTH_EAST_DOWN, Blocks.AIR);

                // Enable South West & South East Down Center
                textureMapping.put(WEST_SOUTH_DOWN_CENTER, centerBlock);
                textureMapping.put(EAST_SOUTH_DOWN_CENTER, centerBlock);
            }
            textureMapping.put(SOUTH_DOWN, frameBlock);
        }

        if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.WEST) && offsets.getBoolean(DynamicTimberFrameBlock.Offset.DOWN))
        {
            if (offsets.getBoolean(DynamicTimberFrameBlock.Offset.DOWN_WEST))
            {
                // Disable South & North West Down.
                textureMapping.put(SOUTH_WEST_DOWN, Blocks.AIR);
                textureMapping.put(NORTH_WEST_DOWN, Blocks.AIR);

                // Enable South & North West Down Center
                textureMapping.put(SOUTH_WEST_DOWN_CENTER, centerBlock);
                textureMapping.put(NORTH_WEST_DOWN_CENTER, centerBlock);
            }
            textureMapping.put(WEST_DOWN, frameBlock);
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

    @NotNull
    @Override
    public MaterialTextureData getTextureData()
    {
        return originalTextureData;
    }

    /**
     * Hook to notify block entity about changed conditions
     *
     * @param newNeighbor the new neighbor.
     * @param offset      the offset of the neighbor.
     * @param added       if added or removed.
     */
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

    /**
     * Reset all offsets for reload.
     */
    public void resetOffsets()
    {
        this.offsets.clear();
    }

    /**
     * Get the frame block.
     * @return the frame block.
     */
    public Block getFrameBlock()
    {
        return frameBlock;
    }

    /**
     * Get the center block.
     * @return the center block.
     */
    public Block getCenterBlock()
    {
        return centerBlock;
    }

    /**
     * Rotate in direction.
     * @param rotation the number of rotations.
     */
    public void rotate(final int rotation) {
        final Object2BooleanOpenHashMap<DynamicTimberFrameBlock.Offset> resultMap = new Object2BooleanOpenHashMap<>();
        for (Map.Entry<DynamicTimberFrameBlock.Offset, Boolean> entry : this.offsets.entrySet()) {
            DynamicTimberFrameBlock.Offset offset = entry.getKey();
            for (int i = 0; i < rotation; i++) {
                offset = offset.rotate();
            }
            resultMap.put(offset, entry.getValue());
        }
        this.offsets.clear();
        this.offsets.putAll(resultMap);
    }
}
