package com.ldtteam.domumornamentum.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.entity.block.IMateriallyTexturedBlockEntity;
import com.ldtteam.domumornamentum.recipe.ModRecipeSerializers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.StreamSupport;

public interface IMateriallyTexturedBlock
{
    @NotNull
    Block getBlock();

    @NotNull
    Collection<IMateriallyTexturedBlockComponent> getComponents();

    @NotNull
    default Collection<FinishedRecipe> getValidCutterRecipes() {
        return Lists.newArrayList(
          new FinishedRecipe() {
              @Override
              public void serializeRecipeData(final @NotNull JsonObject json)
              {
              }

              @Override
              public @NotNull ResourceLocation getId()
              {
                  return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(getBlock()));
              }

              @Override
              public @NotNull RecipeSerializer<?> getType()
              {
                  return ModRecipeSerializers.ARCHITECTS_CUTTER.get();
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

    /**
     * Method to tell mods like minecolonies if a tool is the right tool.
     * @param state the state to mine.
     * @param stack the stack trying to mine it.
     * @param level the level the block is in.
     * @param pos the position the block is at.
     * @return true if correct tool.
     */
    default boolean isCorrectToolForDrops(BlockState state, final ItemStack stack, BlockGetter level, BlockPos pos)
    {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof IMateriallyTexturedBlockEntity mtbe) {
            Block block = mtbe.getTextureData().getTexturedComponents().get(getMainComponent().getId());
            if (block != null)
            {
                return stack.isCorrectToolForDrops(block.defaultBlockState());
            }
        }
        return stack.isCorrectToolForDrops(state);
    }

    default float getDOExplosionResistance(final Block inputBlock, BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof IMateriallyTexturedBlockEntity mtbe) {
            Block block = mtbe.getTextureData().getTexturedComponents().get(getMainComponent().getId());
            if (block != null)
            {
                return block.getExplosionResistance(state, level, pos, explosion);
            }
        }
        return inputBlock.getExplosionResistance(state, level, pos, explosion);
    }

    default float getDODestroyProgress(final Block inputBlock, BlockState state, Player player, BlockGetter level, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof IMateriallyTexturedBlockEntity mtbe) {
            Block block = mtbe.getTextureData().getTexturedComponents().get(getMainComponent().getId());
            if (block != null)
            {
                return block.getDestroyProgress(block.defaultBlockState(), player, level, pos);
            }
        }
        return inputBlock.getDestroyProgress(state, player, level, pos);
    }

    default SoundType getDOSoundType(final Block inputBlock, BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof IMateriallyTexturedBlockEntity mtbe) {
            Block block = mtbe.getTextureData().getTexturedComponents().get(getMainComponent().getId());
            if (block != null)
            {
                return block.getSoundType(state, level, pos, entity);
            }
        }
        return inputBlock.getSoundType(state, level, pos, entity);
    }

    default void fillDOItemCategory(final Block inputBlock, final @NotNull NonNullList<ItemStack> items, List<ItemStack> fillItemGroupCache) {
        if (!fillItemGroupCache.isEmpty()) {
            items.addAll(fillItemGroupCache);
            return;
        }

        try {
            final ItemStack result = new ItemStack(inputBlock);
            fillItemGroupCache.add(result);
        }
        catch (IllegalStateException exception) {
            //Ignored. Thrown during start up.
        }

        items.addAll(fillItemGroupCache);
    }

    /**
     * Get the main component of the block.
     * @return the main component.
     */
    IMateriallyTexturedBlockComponent getMainComponent();

    @NotNull
    default MaterialTextureData getRandomMaterials()
    {
        final Map<ResourceLocation, Block> textureData = Maps.newHashMap();
        for (final IMateriallyTexturedBlockComponent component : getComponents())
        {
            final List<Block> candidates = new ArrayList<>(
              StreamSupport
                .stream(BuiltInRegistries.BLOCK.getTagOrEmpty(component.getValidSkins()).spliterator(), false)
                .map(Holder::value).toList());
            if (candidates.isEmpty()) continue;

            final Block texture = candidates.get(ThreadLocalRandom.current().nextInt(candidates.size()));
            textureData.put(component.getId(), texture);
        }
        return new MaterialTextureData(textureData);
    }

    /**
     * Method to tell if the block tinting is world specific.
     *
     * @return true if world specific tinting.
     */
    default boolean usesWorldSpecificTinting() {
        return true;
    }
}
