package com.ldtteam.domumornamentum.block.decorative;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.ldtteam.domumornamentum.block.*;
import com.ldtteam.domumornamentum.block.components.SimpleRetexturableComponent;
import com.ldtteam.domumornamentum.block.types.PillarShapeType;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.entity.block.MateriallyTexturedBlockEntity;
import com.ldtteam.domumornamentum.entity.block.ModBlockEntityTypes;
import com.ldtteam.domumornamentum.item.decoration.PillarBlockItem;
import com.ldtteam.domumornamentum.recipe.ModRecipeSerializers;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.BlockUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class PillarBlock extends AbstractBlock<PillarBlock> implements IMateriallyTexturedBlock, ICachedItemGroupBlock, EntityBlock
{
    public static final List<IMateriallyTexturedBlockComponent> COMPONENTS = ImmutableList.<IMateriallyTexturedBlockComponent>builder()
        .add(new SimpleRetexturableComponent(new ResourceLocation("block/oak_planks"), ModTags.PILLAR_MATERIALS, Blocks.OAK_PLANKS))
        .build();

    private final List<ItemStack> fillItemGroupCache = Lists.newArrayList();
    private static final Optional<VoxelShape> pillar_capital_shape = Stream.of(
     Shapes.box(0.4375, 0, 0.1875, 0.5625, 0.75, 0.8125),
     Shapes.box(0.4375, 0, 0.1875, 0.5625, 0.75, 0.8125),
     Shapes.box(0.4375, 0, 0.1875, 0.5625, 0.75, 0.8125),
     Shapes.box(0.1875, 0, 0.4375, 0.8125, 0.75, 0.5625),
     Shapes.box(0.1875, 0, 0.4375, 0.8125, 0.75, 0.5625),
     Shapes.box(0.1875, 0, 0.4375, 0.8125, 0.75, 0.5625),
     Shapes.box(0.1875, 0, 0.4375, 0.8125, 0.75, 0.5625),
     Shapes.box(0.4375, 0, 0.1875, 0.5625, 0.75, 0.8125)
     ).reduce((v1,v2) ->Shapes.join(v1, v2,BooleanOp.OR));

    /**
     * This block's name.
     */
    public static final String                      BLOCK_NAME     = "blockpillar";

    /**
     * The hardness this block has.
     */
    private static final float                      BLOCK_HARDNESS = 3F;

    /**
     * The resistance this block has.
     */
    private static final float                      RESISTANCE     = 1F;
    private static final Property column = EnumProperty.create("column", PillarShapeType.class);

    public PillarBlock()
    {
        super(BlockBehaviour.Properties.of(Material.GLASS).strength(BLOCK_HARDNESS, RESISTANCE));
        this.registerDefaultState(this.stateDefinition.any().setValue(column,PillarShapeType.full_pillar));
        setRegistryName(BLOCK_NAME);
    }

    /**
     * Registry block at game registry.
     *
     * @param registry the registry to use.
     */
    @Override
    public void registerItemBlock(final IForgeRegistry<Item> registry, final Item.Properties properties)
    {
        registry.register((new PillarBlockItem(this, properties)).setRegistryName(Objects.requireNonNull(this.getRegistryName())));
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter getter, BlockPos pos)
    {
        return pillar_capital_shape.orElse(Shapes.block());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(column);
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {

        LevelReader levelReader = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockPos blockAbove = blockPos.above();
        BlockPos blockBelow = blockPos.below();
        BlockState stateAbove = levelReader.getBlockState(blockAbove);
        BlockState stateBelow = levelReader.getBlockState(blockBelow);

        Boolean base = this.connectsTo(stateBelow);
        Boolean capital = this.connectsTo(stateAbove);
        if (base){
            updateBelow((Level) levelReader, blockBelow,stateBelow);
        }
        if (capital){
            updateAbove((Level) levelReader,blockAbove, stateAbove);
        }
        return updateShape(this.defaultBlockState(), base, capital);

    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid)
    {
        Comparable column_property = state.getValue(column);
        if (column_property == PillarShapeType.pillar_column)
        {
            if (world.getBlockState(pos.above()).getValue(column)== PillarShapeType.pillar_column)
            {
                world.setBlockAndUpdate(pos.above(),state.setValue(column,PillarShapeType.pillar_base));
            }
            else
            {
                world.setBlockAndUpdate(pos.above(),state.setValue(column,PillarShapeType.full_pillar));
            }

            if (world.getBlockState(pos.below()).getValue(column)== PillarShapeType.pillar_column)
            {
                world.setBlockAndUpdate(pos.below(),state.setValue(column,PillarShapeType.pillar_capital));
            }
            else
            {
                world.setBlockAndUpdate(pos.below(),state.setValue(column,PillarShapeType.full_pillar));
            }
        }
        if (column_property == PillarShapeType.pillar_base){
            if (world.getBlockState(pos.above()).getValue(column)== PillarShapeType.pillar_column)
            {
                world.setBlockAndUpdate(pos.above(),state.setValue(column,PillarShapeType.pillar_base));
            }
            else
            {
                world.setBlockAndUpdate(pos.above(),state.setValue(column,PillarShapeType.full_pillar));
            }

        }
        if (column_property == PillarShapeType.pillar_capital)
        {
            if (world.getBlockState(pos.below()).getValue(column)== PillarShapeType.pillar_column)
            {
                world.setBlockAndUpdate(pos.below(),state.setValue(column,PillarShapeType.pillar_capital));
            }
            else
            {
                world.setBlockAndUpdate(pos.below(),state.setValue(column,PillarShapeType.full_pillar));
            }
        }
        return super.onDestroyedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    private void updateBelow(Level level, BlockPos blockPos, BlockState state)
    {


        BlockPos checkBelow = blockPos.below();
        if (level.getBlockState(checkBelow).getBlock() instanceof PillarBlock)
        {
            level.setBlockAndUpdate(blockPos, state.setValue(column,PillarShapeType.pillar_column));
        }
        else
        {
            level.setBlockAndUpdate(blockPos,state.setValue(column,PillarShapeType.pillar_base));
        }
    }

    private void updateAbove(Level level, BlockPos blockPos,BlockState state)
    {

        BlockPos checkAbove = blockPos.above();
        if (level.getBlockState(checkAbove).getBlock() instanceof PillarBlock)
        {
            level.setBlockAndUpdate(blockPos, state.setValue(column,PillarShapeType.pillar_column));
        }
        else
        {
            level.setBlockAndUpdate(blockPos,state.setValue(column,PillarShapeType.pillar_capital));
        }
    }

    private BlockState updateShape(BlockState blockState, Boolean base, Boolean capital)
    {
        //someone tell me why early returns are working, but it wouldn't work when it was set up correctly. Was I missing an assignment or something?
        if (base && capital)
        {
            return blockState.setValue(column, PillarShapeType.pillar_column);
        }
        if (!base && capital)
        {
            return blockState.setValue(column,PillarShapeType.pillar_base);
        }
        if (base && !capital)
        {
            return blockState.setValue(column, PillarShapeType.pillar_capital);
        }
        blockState.setValue(column,PillarShapeType.full_pillar);
        return blockState;
    }

    private boolean connectsTo(BlockState state )
    {
        if (state.getBlock() instanceof PillarBlock)
        {
            return true;
        }
        return false;
    }


    @Override
    public @NotNull List<IMateriallyTexturedBlockComponent> getComponents()
    {
        return COMPONENTS;
    }

    @Override
    public void fillItemCategory(final @NotNull CreativeModeTab group, final @NotNull NonNullList<ItemStack> items)
    {
        if (!fillItemGroupCache.isEmpty()) {
            items.addAll(fillItemGroupCache);
            return;
        }

        try {
            final ItemStack result = new ItemStack(this);

            fillItemGroupCache.add(result);
        } catch (IllegalStateException exception)
        {
            //Ignored. Thrown during start up.
        }

        items.addAll(fillItemGroupCache);
    }

    @Override
    public void setPlacedBy(
            final @NotNull Level worldIn, final @NotNull BlockPos pos, final @NotNull BlockState state, @Nullable final LivingEntity placer, final @NotNull ItemStack stack)
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

    @Override
    public @NotNull List<ItemStack> getDrops(final @NotNull BlockState state, final @NotNull LootContext.Builder builder)
    {
        return BlockUtils.getMaterializedItemStack(builder);
    }

    @Override
    public ItemStack getCloneItemStack(final BlockState state, final HitResult target, final BlockGetter world, final BlockPos pos, final Player player)
    {
        return BlockUtils.getMaterializedItemStack(player, world, pos);
    }

    @Override
    public @NotNull Block getBlock() { return this; }

    @NotNull
    public Collection<FinishedRecipe> getValidCutterRecipes()
    {
        return Lists.newArrayList(
                new FinishedRecipe()
                {
                    @Override
                    public void serializeRecipeData(final @NotNull JsonObject json)
                    {
                        json.addProperty("count", COMPONENTS.size() * 3);
                    }

                    @Override
                    public @NotNull ResourceLocation getId()
                    {
                        return Objects.requireNonNull(getBlock().getRegistryName());
                    }

                    @Override
                    public @NotNull RecipeSerializer<?> getType()
                    {
                        return ModRecipeSerializers.ARCHITECTS_CUTTER;
                    }

                    @Nullable
                    @Override
                    public JsonObject serializeAdvancement()
                    {
                        return null;
                    }

                    @Nullable
                    @Override
                    public ResourceLocation getAdvancementId()
                    {
                        return null;
                    }
                }
        );
    }
}
