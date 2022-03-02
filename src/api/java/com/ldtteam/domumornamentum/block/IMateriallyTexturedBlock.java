package com.ldtteam.domumornamentum.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.recipe.ModRecipeSerializers;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
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

    @NotNull
    default MaterialTextureData getRandomMaterials()
    {
        final Map<ResourceLocation, Block> textureData = Maps.newHashMap();
        for (final IMateriallyTexturedBlockComponent component : getComponents())
        {
            final List<Block> candidates = new ArrayList<>(
              StreamSupport
                .stream(Registry.BLOCK.getTagOrEmpty(component.getValidSkins()).spliterator(), false)
                .map(Holder::value).toList());
            if (candidates.isEmpty()) continue;

            final Block texture = candidates.get(ThreadLocalRandom.current().nextInt(candidates.size()));
            textureData.put(component.getId(), texture);
        }
        return new MaterialTextureData(textureData);
    }
}
