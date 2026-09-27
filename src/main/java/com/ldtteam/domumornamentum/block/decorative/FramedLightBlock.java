package com.ldtteam.domumornamentum.block.decorative;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.ldtteam.domumornamentum.block.AbstractBlock;
import com.ldtteam.domumornamentum.block.ICachedItemGroupBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.components.SimpleRetexturableComponent;
import com.ldtteam.domumornamentum.block.types.FramedLightType;
import com.ldtteam.domumornamentum.entity.block.MateriallyTexturedBlockEntity;
import com.ldtteam.domumornamentum.recipe.architectscutter.ArchitectsCutterRecipeBuilder;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.BlockUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndLightGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * Decorative block
 */
public class FramedLightBlock extends AbstractBlock<FramedLightBlock> implements IMateriallyTexturedBlock, ICachedItemGroupBlock, EntityBlock
{

    public static final List<IMateriallyTexturedBlockComponent> COMPONENTS = ImmutableList.<IMateriallyTexturedBlockComponent>builder()
        .add(new SimpleRetexturableComponent(Identifier.withDefaultNamespace("block/oak_planks"), ModTags.TIMBERFRAMES_FRAME, Blocks.OAK_PLANKS))
        .add(new SimpleRetexturableComponent(Identifier.withDefaultNamespace("block/glowstone"), ModTags.FRAMED_LIGHT_CENTER, Blocks.GLOWSTONE))
        .build();

    /**
     * The hardness this block has.
     */
    private static final float                         BLOCK_HARDNESS = 3F;

    /**
     * The resistance this block has.
     */
    private static final float                         RESISTANCE     = 1F;

    /**
     * The type of this framed light type.
     */
    private final FramedLightType framedLightType;

    private final List<ItemStack> fillItemGroupCache = Lists.newArrayList();

    /**
     * Constructor for the Framed Light
     * @param framedLightType the framed light type.
     * @param properties the block properties.
     */
    public FramedLightBlock(final FramedLightType framedLightType, final Properties properties)
    {
        super(properties.mapColor(MapColor.WOOD).pushReaction(PushReaction.PUSH_ONLY).strength(BLOCK_HARDNESS, RESISTANCE).noOcclusion().lightLevel(state -> 15));
        this.framedLightType = framedLightType;
    }

    public static String getName(final FramedLightType framedLightType)
    {
        return framedLightType.getName();
    }

    @Override
    public @NotNull List<IMateriallyTexturedBlockComponent> getComponents()
    {
        return COMPONENTS;
    }

    @Override
    public boolean shouldDisplayFluidOverlay(final @NonNull BlockState state, final @NonNull BlockAndLightGetter level, final @NonNull BlockPos pos, final @NonNull FluidState fluidState)
    {
        return true;
    }

    public FramedLightType getFramedLightType()
    {
        return this.framedLightType;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(final @NotNull BlockPos blockPos, final @NotNull BlockState blockState)
    {
        return new MateriallyTexturedBlockEntity(blockPos, blockState);
    }

        public void resetCache()
    {
        fillItemGroupCache.clear();
    }

    @SuppressWarnings("deprecation")
    @Override
    protected @NonNull ItemStack getCloneItemStack(final LevelReader level, final @NonNull BlockPos pos, final @NonNull BlockState state, final boolean includeData)
    {
        return BlockUtils.getMaterializedItemStack(level.getBlockEntity(pos), level.registryAccess());
    }

        @Override

        public void buildRecipes(final RecipeOutput recipeOutput)
    {
        new ArchitectsCutterRecipeBuilder(this, RecipeCategory.BUILDING_BLOCKS).count(COMPONENTS.size()).save(recipeOutput);
    }

    @Override
    public float getExplosionResistance(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull Explosion explosion) {
        return getDOExplosionResistance(super::getExplosionResistance, state, level, pos, explosion);
    }

    @Override
    public float getDestroyProgress(@NotNull BlockState state, @NotNull Player player, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return getDODestroyProgress(super::getDestroyProgress, state, player, level, pos);
    }

    @Override
    public @NonNull SoundType getSoundType(@NonNull BlockState state, @NonNull LevelReader level, @NonNull BlockPos pos, @Nullable Entity entity) {
        return getDOSoundType(super::getSoundType, state, level, pos, entity);
    }

    @Override
    public IMateriallyTexturedBlockComponent getMainComponent() {
        return COMPONENTS.get(0);
    }

    @Override
    public void fillItemCategory(final @NotNull NonNullList<ItemStack> items) {
        fillDOItemCategory(this, items, fillItemGroupCache);
    }
}
