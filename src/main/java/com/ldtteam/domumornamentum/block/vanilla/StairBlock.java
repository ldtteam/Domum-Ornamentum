package com.ldtteam.domumornamentum.block.vanilla;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.ldtteam.domumornamentum.block.ICachedItemGroupBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.components.SimpleRetexturableComponent;
import com.ldtteam.domumornamentum.block.interfaces.IDOBlock;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.entity.block.MateriallyTexturedBlockEntity;
import com.ldtteam.domumornamentum.recipe.architectscutter.ArchitectsCutterRecipeBuilder;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.BlockUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.world.level.block.Blocks.OAK_PLANKS;

public class StairBlock extends net.minecraft.world.level.block.StairBlock implements IMateriallyTexturedBlock, EntityBlock, ICachedItemGroupBlock, IDOBlock<StairBlock>
{
    private static IMateriallyTexturedBlockComponent MATERIAL_COMPONENT = new SimpleRetexturableComponent(new ResourceLocation("minecraft:block/oak_planks"), ModTags.STAIRS_MATERIALS, OAK_PLANKS);
    public static final List<IMateriallyTexturedBlockComponent> COMPONENTS = ImmutableList.<IMateriallyTexturedBlockComponent>builder()
                                                                               .add(MATERIAL_COMPONENT)
                                                                               .build();

    private final List<ItemStack> fillItemGroupCache = Lists.newArrayList();

    public StairBlock()
    {
        super(OAK_PLANKS::defaultBlockState, Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(2.0F, 3.0F));
    }

    @Override
    public ResourceLocation getRegistryName()
    {
        return getRegistryName(this);
    }

    @Override
    public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof MateriallyTexturedBlockEntity mtbe) {
            Block block = mtbe.getTextureData().getTexturedComponents().get(COMPONENTS.get(0).getId());
            if (block != null)
            {
                return block.getExplosionResistance(state, level, pos, explosion);
            }
        }
        return super.getExplosionResistance(state, level, pos, explosion);
    }

    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof MateriallyTexturedBlockEntity mtbe) {
            Block block = mtbe.getTextureData().getTexturedComponents().get(COMPONENTS.get(0).getId());
            if (block != null)
            {
                return block.getDestroyProgress(block.defaultBlockState(), player, level, pos);
            }
        }
        return super.getDestroyProgress(state, player, level, pos);
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
            final ItemStack result = new ItemStack(this);

            fillItemGroupCache.add(result);
        } catch (IllegalStateException exception)
        {
            //Ignored. Thrown during start up.
        }

        items.addAll(fillItemGroupCache);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(final @NotNull BlockPos blockPos, final @NotNull BlockState blockState)
    {
        return new MateriallyTexturedBlockEntity(blockPos, blockState);
    }

    @Override
    public void resetCache()
    {
        fillItemGroupCache.clear();
    }

    @Override
    public void animateTick(final @NotNull BlockState p_56914_, final @NotNull Level p_56915_, final @NotNull BlockPos p_56916_, final @NotNull RandomSource p_56917_)
    {
        final BlockState state = getBlockState(p_56915_, p_56916_);
        state.getBlock().animateTick(state, p_56915_, p_56916_, p_56917_);
    }

    @Override
    public void attack(final @NotNull BlockState p_56896_, final @NotNull Level p_56897_, final @NotNull BlockPos p_56898_, final @NotNull Player p_56899_)
    {
        final BlockState state = getBlockState(p_56897_, p_56898_);
        state.attack(p_56897_, p_56898_, p_56899_);
    }

    @Override
    public void destroy(final @NotNull LevelAccessor p_56882_, final @NotNull BlockPos p_56883_, final @NotNull BlockState p_56884_)
    {
        final BlockState state = getBlockState(p_56882_, p_56883_);
        state.getBlock().destroy(p_56882_, p_56883_, state);
    }

    @Override
    public void onPlace(final @NotNull BlockState p_56961_, final @NotNull Level p_56962_, final @NotNull BlockPos p_56963_, final @NotNull BlockState p_56964_, final boolean p_56965_)
    {
        final BlockState state = getBlockState(p_56962_, p_56963_);
        state.onPlace(p_56962_, p_56963_, p_56964_, p_56965_);
    }

    @Override
    public void onRemove(final @NotNull BlockState p_56908_, final @NotNull Level p_56909_, final @NotNull BlockPos p_56910_, final @NotNull BlockState p_56911_, final boolean p_56912_)
    {
        final BlockState state = getBlockState(p_56909_, p_56910_);
        state.onRemove(p_56909_, p_56910_, p_56911_, p_56912_);
    }

    @Override
    public void stepOn(final @NotNull Level p_154720_, final @NotNull BlockPos p_154721_, final @NotNull BlockState p_154722_, final @NotNull Entity p_154723_)
    {
        final BlockState state = getBlockState(p_154720_, p_154721_);
        state.getBlock().stepOn(p_154720_, p_154721_, state, p_154723_);
    }

    @Override
    public void randomTick(final @NotNull BlockState p_56951_, final @NotNull ServerLevel p_56952_, final @NotNull BlockPos p_56953_, final @NotNull RandomSource p_56954_)
    {
        final BlockState state = getBlockState(p_56952_, p_56953_);
        state.randomTick(p_56952_, p_56953_, p_56954_);
    }

    @Override
    public void tick(final @NotNull BlockState p_56886_, final @NotNull ServerLevel p_56887_, final @NotNull BlockPos p_56888_, final @NotNull RandomSource p_56889_)
    {
        final BlockState state = getBlockState(p_56887_, p_56888_);
        state.tick(p_56887_, p_56888_, p_56889_);
    }

    @Override
    public @NotNull InteractionResult use(
      final @NotNull BlockState p_56901_, final @NotNull Level p_56902_, final @NotNull BlockPos p_56903_, final @NotNull Player p_56904_, final @NotNull InteractionHand p_56905_, final @NotNull BlockHitResult p_56906_)
    {
        final BlockState state = getBlockState(p_56902_, p_56903_);
        return state.use(p_56902_, p_56904_, p_56905_, p_56906_);
    }

    @Override
    public void wasExploded(final @NotNull Level p_56878_, final @NotNull BlockPos p_56879_, final @NotNull Explosion p_56880_)
    {
        final BlockState state = getBlockState(p_56878_, p_56879_);
        state.getBlock().wasExploded(p_56878_, p_56879_, p_56880_);
    }

    @Override
    public @NotNull List<ItemStack> getDrops(final @NotNull BlockState state, final @NotNull LootParams.Builder builder)
    {
        return BlockUtils.getMaterializedDrops(builder);
    }

    @Override
    public ItemStack getCloneItemStack(final BlockState state, final HitResult target, final LevelReader world, final BlockPos pos, final Player player)
    {
        return BlockUtils.getMaterializedItemStack(world.getBlockEntity(pos));
    }

    private BlockState getBlockState(final BlockGetter blockGetter, final BlockPos blockPos) {
        final BlockEntity blockEntity = blockGetter.getBlockEntity(blockPos);

        if (blockEntity instanceof MateriallyTexturedBlockEntity) {
            final MaterialTextureData data = ((MateriallyTexturedBlockEntity) blockEntity).getTextureData();
            final Block block = data.getTexturedComponents().get(MATERIAL_COMPONENT.getId());
            if (block != null) {
                return block.defaultBlockState();
            }
        }

        return Blocks.AIR.defaultBlockState();
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof MateriallyTexturedBlockEntity mtbe) {
            Block block = mtbe.getTextureData().getTexturedComponents().get(COMPONENTS.get(0).getId());
            if (block != null)
            {
                return block.getSoundType(state, level, pos, entity);
            }
        }
        return super.getSoundType(state, level, pos, entity);
    }

    @Override
    public void buildRecipes(final RecipeOutput recipeOutput)
    {
        new ArchitectsCutterRecipeBuilder(this, RecipeCategory.BUILDING_BLOCKS).save(recipeOutput);
    }
}
