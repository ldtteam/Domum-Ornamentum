package com.ldtteam.domumornamentum.entity.block;

import com.ldtteam.domumornamentum.DomumOrnamentum;
import com.ldtteam.domumornamentum.block.decorative.DynamicTimberFrameBlock;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.client.model.properties.ModProperties;
import com.ldtteam.domumornamentum.component.ModDataComponents;
import com.ldtteam.domumornamentum.util.MaterialTextureDataUtil;
import com.mojang.serialization.DynamicOps;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.ldtteam.domumornamentum.entity.block.ModBlockEntityTypes.DYNAMIC_TIMBERFRAME;
import static com.ldtteam.domumornamentum.util.Constants.BLOCK_ENTITY_TEXTURE_DATA;
import static com.ldtteam.domumornamentum.util.Constants.OFFSETS;
import static com.ldtteam.domumornamentum.util.Constants.PRIMARY_BLOCK;
import static com.ldtteam.domumornamentum.util.Constants.SECONDARY_BLOCK;

public class DynamicTimberFrameBlockEntity extends AbstractMateriallyTexturedBlockEntity
{
    // Frame (wool)
    public static final Identifier NORTH_UP          = Identifier.withDefaultNamespace("block/white_wool");
    public static final Identifier NORTH_DOWN        = Identifier.withDefaultNamespace("block/orange_wool");
    public static final Identifier SOUTH_UP          = Identifier.withDefaultNamespace("block/magenta_wool");
    public static final Identifier SOUTH_DOWN        = Identifier.withDefaultNamespace("block/light_blue_wool");
    public static final Identifier EAST_DOWN         = Identifier.withDefaultNamespace("block/yellow_wool");
    public static final Identifier EAST_UP           = Identifier.withDefaultNamespace("block/lime_wool");
    public static final Identifier WEST_DOWN         = Identifier.withDefaultNamespace("block/pink_wool");
    public static final Identifier WEST_UP           = Identifier.withDefaultNamespace("block/gray_wool");
    public static final Identifier NORTH_EAST_DOWN   = Identifier.withDefaultNamespace("block/light_gray_wool");
    public static final Identifier NORTH_EAST_UP     = Identifier.withDefaultNamespace("block/cyan_wool");
    public static final Identifier NORTH_EAST_CORNER = Identifier.withDefaultNamespace("block/purple_wool");
    public static final Identifier NORTH_WEST_DOWN   = Identifier.withDefaultNamespace("block/blue_wool");
    public static final Identifier NORTH_WEST_UP     = Identifier.withDefaultNamespace("block/brown_wool");
    public static final Identifier NORTH_WEST_CORNER = Identifier.withDefaultNamespace("block/green_wool");
    public static final Identifier SOUTH_EAST_DOWN   = Identifier.withDefaultNamespace("block/red_wool");
    public static final Identifier SOUTH_EAST_UP     = Identifier.withDefaultNamespace("block/black_wool");

    // Frame (terracotta)
    public static final Identifier SOUTH_EAST_CORNER = Identifier.withDefaultNamespace("block/white_terracotta");
    public static final Identifier SOUTH_WEST_DOWN   = Identifier.withDefaultNamespace("block/orange_terracotta");
    public static final Identifier SOUTH_WEST_UP     = Identifier.withDefaultNamespace("block/magenta_terracotta");
    public static final Identifier SOUTH_WEST_CORNER = Identifier.withDefaultNamespace("block/light_blue_terracotta");

    // Center (terracotta)
    public static final Identifier CENTER                 = Identifier.withDefaultNamespace("block/yellow_terracotta");
    public static final Identifier BOTTOM_CENTER          = Identifier.withDefaultNamespace("block/lime_terracotta");
    public static final Identifier BOTTOM_SOUTH_CENTER    = Identifier.withDefaultNamespace("block/pink_terracotta");
    public static final Identifier BOTTOM_NORTH_CENTER    = Identifier.withDefaultNamespace("block/gray_terracotta");
    public static final Identifier TOP_NORTH_CENTER       = Identifier.withDefaultNamespace("block/light_gray_terracotta");
    public static final Identifier TOP_SOUTH_CENTER       = Identifier.withDefaultNamespace("block/cyan_terracotta");
    public static final Identifier TOP_CENTER             = Identifier.withDefaultNamespace("block/purple_terracotta");
    public static final Identifier NORTH_WEST_CENTER      = Identifier.withDefaultNamespace("block/blue_terracotta");
    public static final Identifier NORTH_EAST_CENTER      = Identifier.withDefaultNamespace("block/brown_terracotta");
    public static final Identifier NORTH_WEST_UP_CENTER   = Identifier.withDefaultNamespace("block/green_terracotta");
    public static final Identifier NORTH_EAST_UP_CENTER   = Identifier.withDefaultNamespace("block/red_terracotta");
    public static final Identifier NORTH_EAST_DOWN_CENTER = Identifier.withDefaultNamespace("block/black_terracotta");

    // Center (concrete)
    public static final Identifier NORTH_WEST_DOWN_CENTER = Identifier.withDefaultNamespace("block/white_concrete");
    public static final Identifier SOUTH_EAST_CENTER      = Identifier.withDefaultNamespace("block/orange_concrete");
    public static final Identifier SOUTH_WEST_CENTER      = Identifier.withDefaultNamespace("block/magenta_concrete");
    public static final Identifier SOUTH_WEST_UP_CENTER   = Identifier.withDefaultNamespace("block/light_blue_concrete");
    public static final Identifier SOUTH_EAST_UP_CENTER   = Identifier.withDefaultNamespace("block/yellow_concrete");
    public static final Identifier SOUTH_EAST_DOWN_CENTER = Identifier.withDefaultNamespace("block/lime_concrete");
    public static final Identifier SOUTH_WEST_DOWN_CENTER = Identifier.withDefaultNamespace("block/pink_concrete");
    public static final Identifier EAST_NORTH_CENTER      = Identifier.withDefaultNamespace("block/gray_concrete");
    public static final Identifier EAST_SOUTH_CENTER      = Identifier.withDefaultNamespace("block/light_gray_concrete");
    public static final Identifier EAST_SOUTH_UP_CENTER   = Identifier.withDefaultNamespace("block/cyan_concrete");
    public static final Identifier EAST_NORTH_UP_CENTER   = Identifier.withDefaultNamespace("block/purple_concrete");
    public static final Identifier EAST_SOUTH_DOWN_CENTER = Identifier.withDefaultNamespace("block/blue_concrete");
    public static final Identifier EAST_NORTH_DOWN_CENTER = Identifier.withDefaultNamespace("block/brown_concrete");
    public static final Identifier WEST_NORTH_CENTER      = Identifier.withDefaultNamespace("block/green_concrete");
    public static final Identifier WEST_SOUTH_CENTER      = Identifier.withDefaultNamespace("block/red_concrete");
    public static final Identifier WEST_SOUTH_UP_CENTER   = Identifier.withDefaultNamespace("block/black_concrete");
    public static final Identifier WEST_NORTH_UP_CENTER   = Identifier.withDefaultNamespace("block/glowstone");
    public static final Identifier WEST_SOUTH_DOWN_CENTER = Identifier.withDefaultNamespace("block/cherry_planks");
    public static final Identifier WEST_NORTH_DOWN_CENTER = Identifier.withDefaultNamespace("block/shroomlight");

    /**
     * Cached resmap.
     */
    private MaterialTextureData textureDataCache = new MaterialTextureData(Map.of());

    /**
     * Texture mapping of position at resource location to block (air, frame or center)
     */
    private final Map<Identifier, Block> textureMapping = new TreeMap<>();

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
    public CompoundTag getUpdateTag(final HolderLookup.Provider registries)
    {
        return this.saveWithFullMetadata(registries);
    }

    @Override
    public void onDataPacket(final Connection net, final ValueInput valueInput)
    {
        this.loadAdditional(valueInput);
    }

    @Override
    public void handleUpdateTag(final ValueInput input)
    {
        this.loadAdditional(input);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }



    @Override
    protected void saveAdditional(final ValueOutput output)
    {
        super.saveAdditional(output);

        var blockRegistryCodec = getLevel().registryAccess().lookupOrThrow(Registries.BLOCK)
                .byNameCodec();

        output.store(BLOCK_ENTITY_TEXTURE_DATA, MaterialTextureData.CODEC, originalTextureData);
        output.store(PRIMARY_BLOCK, blockRegistryCodec, centerBlock);
        output.store(SECONDARY_BLOCK, blockRegistryCodec, frameBlock);

        var rawOffsets = output.childrenList(OFFSETS);
        for (final Object2BooleanMap.Entry<DynamicTimberFrameBlock.Offset> mapEntry : offsets.object2BooleanEntrySet())
        {
            ValueOutput child = rawOffsets.addChild();
            child.putInt("offset", mapEntry.getKey().ordinal());
            child.putBoolean("bool", mapEntry.getBooleanValue());
        }

        if (rawOffsets.isEmpty())
        {
            output.discard(OFFSETS);
        }
    }

    @Override
    protected void loadAdditional(final ValueInput input)
    {
        super.loadAdditional(input);

        input.read(BLOCK_ENTITY_TEXTURE_DATA, MaterialTextureData.CODEC)
            .or(() -> input.read("originalTextureData", MaterialTextureData.CODEC))
            .ifPresent(this::updateTextureDataWith);

        final String primaryBlockName = input.getStringOr("primaryBlock", "");
        BuiltInRegistries.BLOCK.getOptional(Identifier.parse(primaryBlockName))
            .filter(block -> block != Blocks.AIR)
            .ifPresent(block -> this.centerBlock = block);

        final String secondaryBlockName = input.getStringOr("secondaryBlock", "");
        BuiltInRegistries.BLOCK.getOptional(Identifier.parse(secondaryBlockName))
            .filter(block -> block != Blocks.AIR)
            .ifPresent(block -> this.frameBlock = block);

        offsets.clear();
        input.childrenListOrEmpty(OFFSETS).forEach(child -> {
            int ordinal = child.getIntOr("offset", 0);
            DynamicTimberFrameBlock.Offset[] values = DynamicTimberFrameBlock.Offset.values();
            if (ordinal >= 0 && ordinal < values.length)
        {
                offsets.put(values[ordinal], child.getBooleanOr("bool", false));
        }
        });

        if (level != null && level.isClientSide())
        {
            refreshTextureCache();
        }
    }

    @Override
    protected void applyImplicitComponents(final DataComponentGetter componentInput)
    {
        super.applyImplicitComponents(componentInput);
        MaterialTextureData testTextureData = componentInput.getOrDefault(ModDataComponents.TEXTURE_DATA, MaterialTextureData.EMPTY);
        if (testTextureData.isEmpty())
        {
            testTextureData = MaterialTextureDataUtil.generateRandomTextureDataFrom(this.getBlockState().getBlock());
        }

        updateTextureDataWith(testTextureData);
    }

    @Override
    protected void collectImplicitComponents(final DataComponentMap.Builder componentBuilder)
    {
        super.collectImplicitComponents(componentBuilder);
        componentBuilder.set(ModDataComponents.TEXTURE_DATA, originalTextureData);
    }

    @Override
    public void removeComponentsFromTag(final ValueOutput itemStackTag)
    {
        itemStackTag.discard(BLOCK_ENTITY_TEXTURE_DATA);
    }

    @Override
    public void updateTextureDataWith(final MaterialTextureData materialTextureData)
    {
        centerBlock = materialTextureData.getTexturedComponents().get(Identifier.withDefaultNamespace("block/dark_oak_planks"));
        frameBlock = materialTextureData.getTexturedComponents().get(Identifier.withDefaultNamespace("block/oak_planks"));
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
            level.getChunk(worldPosition.getX() >> 4, worldPosition.getZ() >> 4).markUnsaved();
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
