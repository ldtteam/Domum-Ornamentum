package com.ldtteam.domumornamentum.block.decorative;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ldtteam.domumornamentum.block.AbstractBlock;
import com.ldtteam.domumornamentum.block.ICachedItemGroupBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.components.SimpleRetexturableComponent;
import com.ldtteam.domumornamentum.block.types.TimberFrameType;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.entity.block.ModBlockEntityTypes;
import com.ldtteam.domumornamentum.entity.block.MateriallyTexturedBlockEntity;
import com.ldtteam.domumornamentum.item.decoration.TimberFrameBlockItem;
import com.ldtteam.domumornamentum.tag.ModTags;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;

/**
 * Decorative block
 */
public class TimberFrameBlock extends AbstractBlock<TimberFrameBlock> implements IMateriallyTexturedBlock, ICachedItemGroupBlock, EntityBlock
{

    public static final List<IMateriallyTexturedBlockComponent> COMPONENTS = ImmutableList.<IMateriallyTexturedBlockComponent>builder()
        .add(new SimpleRetexturableComponent(new ResourceLocation("block/oak_planks"), ModTags.TIMBERFRAMES_FRAME, Blocks.OAK_PLANKS))
        .add(new SimpleRetexturableComponent(new ResourceLocation("block/dark_oak_planks"), ModTags.TIMBERFRAMES_CENTER, Blocks.DARK_OAK_PLANKS))
        .build();

    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    /**
     * The hardness this block has.
     */
    private static final float                         BLOCK_HARDNESS = 3F;

    /**
     * The resistance this block has.
     */
    private static final float                         RESISTANCE     = 1F;

    /**
     * The type of this timber frame type.
     */
    private TimberFrameType timberFrameType;

    private final List<ItemStack> fillItemGroupCache = Lists.newArrayList();

    /**
     * Constructor for the TimberFrame
     */
    public TimberFrameBlock(final TimberFrameType timberFrameType)
    {
        super(Properties.of(Material.WOOD).strength(BLOCK_HARDNESS, RESISTANCE).noOcclusion());
        setRegistryName(getName(timberFrameType));

        this.timberFrameType = timberFrameType;
    }

    @Override
    public void registerItemBlock(final IForgeRegistry<Item> registry, final Item.Properties properties)
    {
        registry.register((new TimberFrameBlockItem(this, properties)).setRegistryName(Objects.requireNonNull(this.getRegistryName())));
    }

    public static String getName(final TimberFrameType timberFrameType)
    {
        return timberFrameType.getName();
    }

    @Override
    protected void createBlockStateDefinition(@NotNull final StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    @NotNull
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    public List<IMateriallyTexturedBlockComponent> getComponents()
    {
        return COMPONENTS;
    }

    @Override
    public void fillItemCategory(final CreativeModeTab group, final NonNullList<ItemStack> items)
    {
        if (!fillItemGroupCache.isEmpty()) {
            items.addAll(fillItemGroupCache);
            return;
        }

        IMateriallyTexturedBlockComponent outerComponent = getComponents().get(0);
        IMateriallyTexturedBlockComponent innerComponent = getComponents().get(1);

        final Tag<Block> outerCandidates = outerComponent.getValidSkins();
        final Tag<Block> innerCandidates = innerComponent.getValidSkins();

        try {
            outerCandidates.getValues().forEach(outer -> {
                innerCandidates.getValues().forEach(inner ->{
                    final Map<ResourceLocation, Block> textureData = Maps.newHashMap();

                    textureData.put(outerComponent.getId(), outer);
                    textureData.put(innerComponent.getId(), inner);

                    final MaterialTextureData materialTextureData = new MaterialTextureData(textureData);

                    final CompoundTag textureNbt = materialTextureData.serializeNBT();

                    final ItemStack result = new ItemStack(this);
                    result.getOrCreateTag().put("textureData", textureNbt);

                    fillItemGroupCache.add(result);
                });
            });
        } catch (IllegalStateException exception)
        {
            //Ignored. Thrown during start up.
        }

        items.addAll(fillItemGroupCache);
    }

    public TimberFrameType getTimberFrameType()
    {
        return this.timberFrameType;
    }

    @Override
    public void setPlacedBy(
      final Level worldIn, final BlockPos pos, final BlockState state, @Nullable final LivingEntity placer, final ItemStack stack)
    {
        super.setPlacedBy(worldIn, pos, state, placer, stack);

        final CompoundTag textureData = stack.getOrCreateTagElement("textureData");
        final BlockEntity tileEntity = worldIn.getBlockEntity(pos);

        if (tileEntity instanceof MateriallyTexturedBlockEntity)
            ((MateriallyTexturedBlockEntity) tileEntity).updateTextureDataWith(MaterialTextureData.deserializeFromNBT(textureData));
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(final @NotNull BlockPos blockPos, final @NotNull BlockState blockState)
    {
        return new MateriallyTexturedBlockEntity(ModBlockEntityTypes.MATERIALLY_TEXTURED, blockPos, blockState);
    }

    @Override
    public void resetCache()
    {
        fillItemGroupCache.clear();
    }
}
