package com.ldtteam.domumornamentum.block;

import com.google.common.collect.Maps;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.StreamSupport;

public interface IMateriallyTexturedBlock
{
    @NotNull
    Collection<IMateriallyTexturedBlockComponent> getComponents();

    void buildRecipes(RecipeOutput recipeOutput);

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
}
