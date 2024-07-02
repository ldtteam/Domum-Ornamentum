package com.ldtteam.domumornamentum.block.decorative;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.ldtteam.domumornamentum.block.AbstractPanelBlockTrapdoor;
import com.ldtteam.domumornamentum.block.ICachedItemGroupBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.components.SimpleRetexturableComponent;
import com.ldtteam.domumornamentum.block.types.TrapdoorType;
import com.ldtteam.domumornamentum.entity.block.MateriallyTexturedBlockEntity;
import com.ldtteam.domumornamentum.recipe.architectscutter.ArchitectsCutterRecipeBuilder;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.BlockUtils;
import com.ldtteam.domumornamentum.util.Constants;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.world.level.block.Blocks.OAK_PLANKS;

public class PanelBlock extends AbstractPanelBlockTrapdoor<PanelBlock> implements IMateriallyTexturedBlock, ICachedItemGroupBlock, EntityBlock
{
    public static final MapCodec<PanelBlock> CODEC = simpleCodec(PanelBlock::new);
    public static final EnumProperty<TrapdoorType>              TYPE       = EnumProperty.create(Constants.TYPE_BLOCK_PROPERTY, TrapdoorType.class);
    public static final List<IMateriallyTexturedBlockComponent> COMPONENTS = ImmutableList.<IMateriallyTexturedBlockComponent>builder()
                                                                               .add(new SimpleRetexturableComponent(ResourceLocation.withDefaultNamespace("block/oak_planks"), ModTags.TRAPDOORS_MATERIALS, OAK_PLANKS))
                                                                               .build();

    private final List<ItemStack> fillItemGroupCache = Lists.newArrayList();

    public PanelBlock()
    {
        this(Properties.of().mapColor(MapColor.WOOD).strength(3.0F).noOcclusion().isValidSpawn((state, blockGetter, pos, type) -> false));
    }

    public PanelBlock(final Properties props)
    {
        super(props);
        this.registerDefaultState(this.defaultBlockState().setValue(TYPE, TrapdoorType.FULL));
    }

    @Override
    protected MapCodec<PanelBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.@NotNull Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(TYPE);
    }

    @Override
    public @NotNull List<IMateriallyTexturedBlockComponent> getComponents()
    {
        return COMPONENTS;
    }

    @Override
    public void fillItemCategory(final @NotNull NonNullList<ItemStack> items)
    {
        if (!fillItemGroupCache.isEmpty()) {
            items.addAll(fillItemGroupCache);
            return;
        }

        try {
            for (final TrapdoorType trapdoorType : TrapdoorType.values())
            {
                final ItemStack result = new ItemStack(this);
                BlockUtils.putPropertyIntoBlockStateTag(result, TYPE, trapdoorType);

                fillItemGroupCache.add(result);
            }
        } catch (IllegalStateException exception)
        {
            //Ignored. Thrown during start up.
        }

        items.addAll(fillItemGroupCache);
    }

    @Override
    public BlockState getStateForPlacement(final @NotNull BlockPlaceContext context)
    {
        final BlockState state = super.getStateForPlacement(context);
        final Vec3 offsetPos = context.getClickLocation().subtract(Vec3.atLowerCornerOf(context.getClickedPos()));
        if (context.getClickedFace().getAxis().isHorizontal())
        {
            if (offsetPos.y > 0.80)
            {
                return state.setValue(OPEN, false);
            }
            else if (offsetPos.y < 0.20)
            {
                return state.setValue(OPEN, false).setValue(HALF, Half.BOTTOM);
            }

            if (context.getClickedFace().getAxis() == Direction.Axis.X)
            {
                if (offsetPos.z > 0.80)
                {
                    return state.setValue(FACING, Direction.NORTH);
                }
                else if (offsetPos.z < 0.20)
                {
                    return state.setValue(FACING, Direction.SOUTH);
                }
            }
            else
            {
                if (offsetPos.x > 0.80)
                {
                    return state.setValue(FACING, Direction.WEST);
                }
                else if (offsetPos.x < 0.20)
                {
                    return state.setValue(FACING, Direction.EAST);
                }
            }
        }
        else
        {
            if (offsetPos.z > 0.80)
            {
                return state.setValue(OPEN, true).setValue(FACING, Direction.NORTH);
            }
            else if (offsetPos.z < 0.20)
            {
                return state.setValue(OPEN, true).setValue(FACING, Direction.SOUTH);
            }

            if (offsetPos.x > 0.80)
            {
                return state.setValue(OPEN, true).setValue(FACING, Direction.WEST);
            }
            else if (offsetPos.x < 0.20)
            {
                return state.setValue(OPEN, true).setValue(FACING, Direction.EAST);
            }
        }

        return state;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(final @NotNull BlockPos blockPos, final @NotNull BlockState blockState)
    {
        return new MateriallyTexturedBlockEntity(blockPos, blockState);
    }

    @Override
    public ItemStack getCloneItemStack(final BlockState state, final HitResult target, final LevelReader world, final BlockPos pos, final Player player)
    {
        return BlockUtils.getMaterializedItemStack(world.getBlockEntity(pos), world.registryAccess(), TYPE);
    }

    @Override
    public void resetCache()
    {
        fillItemGroupCache.clear();
    }

    @Override
    public void buildRecipes(final RecipeOutput recipeOutput)
    {
        for (final TrapdoorType value : TrapdoorType.values())
        {
            new ArchitectsCutterRecipeBuilder(this, RecipeCategory.DECORATIONS).resultProperty(TYPE, value)
                .count(COMPONENTS.size() * 4)
                .saveSuffix(recipeOutput, value.getSerializedName());
        }
    }

    @Override
    public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        return getDOExplosionResistance(super::getExplosionResistance, state, level, pos, explosion);
    }

    @Override
    public float getDestroyProgress(@NotNull BlockState state, @NotNull Player player, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return getDODestroyProgress(super::getDestroyProgress, state, player, level, pos);
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return getDOSoundType(super::getSoundType, state, level, pos, entity);
    }

    @Override
    public IMateriallyTexturedBlockComponent getMainComponent() {
        return COMPONENTS.get(0);
    }
}
