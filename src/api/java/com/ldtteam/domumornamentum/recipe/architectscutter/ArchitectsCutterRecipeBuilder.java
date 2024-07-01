package com.ldtteam.domumornamentum.recipe.architectscutter;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataComponentPatchBuilder;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Inspired by {@link RecipeBuilder}
 */
public class ArchitectsCutterRecipeBuilder
{
    private final RecipeCategory category;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    private final Block result;
    private int count = 1;
    private final DataComponentPatchBuilder components = new DataComponentPatchBuilder();

    /**
     * @param result main result block of recipe
     * @param category recipe category as in {@link VanillaRecipeProvider}
     */
    public <T extends Block & IMateriallyTexturedBlock> ArchitectsCutterRecipeBuilder(final T result, final RecipeCategory category)
    {
        this.result = result;
        this.category = category;
    }

    public <T extends Comparable<T>> ArchitectsCutterRecipeBuilder resultProperty(final Property<T> property, final T value)
    {
        components.update(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY, props -> props.with(property, value));
        return this;
    }

    public ArchitectsCutterRecipeBuilder textureData(final MaterialTextureData textureData)
    {
        if (textureData.isEmpty())
        {
            return this;
        }

        final CompoundTag serialized = new CompoundTag();
        serialized.put(Constants.BLOCK_ENTITY_TEXTURE_DATA, textureData.serializeNBT());

        BlockEntity.addEntityType(serialized,
            BuiltInRegistries.BLOCK_ENTITY_TYPE
                .get(Constants.BlockEntityTypes.MATERIALLY_RETEXTURABLE));

        components.set(DataComponents.BLOCK_ENTITY_DATA, CustomData.of(serialized));

        return this;
    }

    public ArchitectsCutterRecipeBuilder count(final int count)
    {
        this.count = count;
        return this;
    }

    public ArchitectsCutterRecipeBuilder unlockedBy(final String criterionId, final Criterion<?> criterion)
    {
        this.criteria.put(criterionId, criterion);
        return this;
    }

    public void save(final RecipeOutput output, final ResourceLocation recipeId)
    {
        final ArchitectsCutterRecipe recipe = new ArchitectsCutterRecipe(BuiltInRegistries.BLOCK.getKey(result),
            count,
            components.build());

        if (criteria.isEmpty())
        {
            output.accept(recipeId, recipe, null);
            return;
        }

        final Advancement.Builder advancement = output.advancement()
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
            .rewards(AdvancementRewards.Builder.recipe(recipeId))
            .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);

        output.accept(recipeId, recipe, advancement.build(recipeId.withPrefix("recipes/" + category.getFolderName() + "/")));
    }

    public void saveSuffix(final RecipeOutput output, final String suffix)
    {
        save(output, BuiltInRegistries.BLOCK.getKey(result).withSuffix("_" + suffix));
    }

    public void save(final RecipeOutput output, final String name)
    {
        save(output, BuiltInRegistries.BLOCK.getKey(result).withPath(name));
    }

    public void save(final RecipeOutput output)
    {
        save(output, BuiltInRegistries.BLOCK.getKey(result));
    }
}
