package com.ldtteam.domumornamentum.datagen.extra;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.ExtraBlock;
import com.ldtteam.domumornamentum.block.types.ExtraBlockType;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class ExtraRecipeProvider extends RecipeProvider
{
    public ExtraRecipeProvider(Provider lookupProvider, RecipeOutput recipeOutput)
    {
        super(lookupProvider, recipeOutput);
    }

    @Override
    protected void buildRecipes() {
        ModBlocks.getInstance().getExtraTopBlocks().forEach(extraBlock -> extraBlockRecipe(extraBlock));
    }

    private ItemLike getDyeItem(DyeColor color) {
        final String dyeName = color.getName() + "_dye";
        final Identifier dyeId = Identifier.fromNamespaceAndPath("minecraft", dyeName);
        return BuiltInRegistries.ITEM.get(dyeId).map(Holder::value).orElseThrow();
    }

    private void extraBlockRecipe(ExtraBlock extraBlock) {
        final ExtraBlockType type = extraBlock.getType();
        final ShapedRecipeBuilder builder = this.shaped(RecipeCategory.TOOLS, extraBlock, 4);
        builder.pattern("X X");
        builder.pattern(" Z ");
        builder.pattern("X X");
        builder.define('X', type.getMaterial());
        if (type.getColor() == null) {
            builder.define('Z', type.getMaterial());
        } else {
            builder.define('Z', getDyeItem(type.getColor()));
        }
        builder.unlockedBy("has_material", this.has(type.getMaterial()));
        if (type.getColor() != null) {
            builder.unlockedBy("has_dye", this.has(getDyeItem(type.getColor())));
        }
        final String itemName = BuiltInRegistries.ITEM.getKey(extraBlock.asItem().asItem()).getPath();
        builder.save(this.output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "extra_" + itemName)));
    }

    public static void register(GatherDataEvent.Server event)
    {
        event.addProvider(new RecipeProvider.Runner(event.getGenerator().getPackOutput(), event.getLookupProvider()) {
            @Override
            protected RecipeProvider createRecipeProvider(net.minecraft.core.HolderLookup.Provider registries, RecipeOutput output) {
                return new ExtraRecipeProvider(registries, output);
            }
            @Override
            public String getName() { return "Extra Recipe Provider"; }
        });
    }
}
