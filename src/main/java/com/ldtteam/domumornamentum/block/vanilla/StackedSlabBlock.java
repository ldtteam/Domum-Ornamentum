package com.ldtteam.domumornamentum.block.vanilla;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.ldtteam.domumornamentum.block.AbstractBlock;
import com.ldtteam.domumornamentum.block.ICachedItemGroupBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.components.SimpleRetexturableComponent;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.entity.block.MateriallyTexturedBlockEntity;
import com.ldtteam.domumornamentum.recipe.FinishedDORecipe;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.BlockUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static net.minecraft.world.level.block.Blocks.STRIPPED_OAK_WOOD;

/**
 * Full-block slab pair produced by the architect's cutter from two slab materials.
 * <p>
 * This block intentionally uses the Domum materially textured block pipeline while
 * presenting itself with the vanilla block recipes, allowing top and bottom slab
 * textures to be chosen independently without adding a placement-time merge path.
 */
public class StackedSlabBlock extends AbstractBlock<StackedSlabBlock> implements IMateriallyTexturedBlock, EntityBlock, ICachedItemGroupBlock
{
    /**
     * Material components used by the model: oak planks address the top half and
     * dark oak planks address the bottom half.
     */
    public static final List<IMateriallyTexturedBlockComponent> COMPONENTS = ImmutableList.<IMateriallyTexturedBlockComponent>builder()
      .add(new SimpleRetexturableComponent(new ResourceLocation("block/oak_planks"), ModTags.SLAB_MATERIALS, STRIPPED_OAK_WOOD))
      .add(new SimpleRetexturableComponent(new ResourceLocation("block/dark_oak_planks"), ModTags.SLAB_MATERIALS, STRIPPED_OAK_WOOD))
      .build();

    private final List<ItemStack> fillItemGroupCache = Lists.newArrayList();

    /**
     * Creates the stacked slab block using wood-like physical properties.
     */
    public StackedSlabBlock()
    {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(2.0F, 3.0F));
    }

    @Override
    public @NotNull List<IMateriallyTexturedBlockComponent> getComponents()
    {
        return COMPONENTS;
    }

    /**
     * Transfers the selected top and bottom textures from the placed item onto the
     * block entity that drives the materially textured model.
     */
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

    /**
     * Creates the material texture storage entity for this block instance.
     */
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

    /**
     * Drops the block item with its selected slab materials preserved.
     */
    @Override
    public @NotNull List<ItemStack> getDrops(final @NotNull BlockState state, final @NotNull LootParams.Builder builder)
    {
        return BlockUtils.getMaterializedItemStack(builder);
    }

    /**
     * Preserves selected slab materials when the player picks the block.
     */
    @Override
    public ItemStack getCloneItemStack(final BlockState state, final HitResult target, final BlockGetter world, final BlockPos pos, final Player player)
    {
        return BlockUtils.getMaterializedItemStack(player, world, pos);
    }

    @Override
    public @NotNull Block getBlock()
    {
        return this;
    }

    /**
     * Registers a two-input architect's cutter recipe that yields four stacked
     * slabs, matching the existing timber frame 2:4 input-output convention.
     */
    @NotNull
    @Override
    public Collection<FinishedRecipe> getValidCutterRecipes()
    {
        return Lists.newArrayList(
          new FinishedDORecipe()
          {
              @Override
              public void serializeRecipeData(final @NotNull JsonObject json)
              {
                  json.addProperty("count", COMPONENTS.size() * 2);
              }

              @Override
              public @NotNull ResourceLocation getId()
              {
                  return Objects.requireNonNull(getRegistryName(getBlock()));
              }
          }
        );
    }

    @Override
    public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion)
    {
        return getDOExplosionResistance(super::getExplosionResistance, state, level, pos, explosion);
    }

    @Override
    public float getDestroyProgress(@NotNull BlockState state, @NotNull Player player, @NotNull BlockGetter level, @NotNull BlockPos pos)
    {
        return getDODestroyProgress(super::getDestroyProgress, state, player, level, pos);
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity)
    {
        return getDOSoundType(super::getSoundType, state, level, pos, entity);
    }

    /**
     * Uses the top slab material as the representative component for inherited
     * Domum block behavior.
     */
    @Override
    public IMateriallyTexturedBlockComponent getMainComponent()
    {
        return COMPONENTS.get(0);
    }

    /**
     * Adds materialized variants to creative/cutter displays using the cached
     * Domum item group generation.
     */
    @Override
    public void fillItemCategory(final @NotNull NonNullList<ItemStack> items)
    {
        fillDOItemCategory(this, items, fillItemGroupCache);
    }
}
