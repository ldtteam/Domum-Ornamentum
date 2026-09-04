package com.ldtteam.domumornamentum.datagen.extra;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.ExtraBlock;
import com.ldtteam.domumornamentum.block.types.ExtraBlockType;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import com.ldtteam.domumornamentum.datagen.global.DomumRecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;


public class ExtraRecipeProvider extends DomumRecipeProvider
{
    public ExtraRecipeProvider(HolderLookup.Provider registries, RecipeOutput output)
    {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        ModBlocks.getInstance().getExtraTopBlocks().forEach(extraBlock -> extraBlockRecipe(this.output, extraBlock));
    }

    private void extraBlockRecipe(RecipeOutput writer, ExtraBlock extraBlock) {
        final ExtraBlockType type = extraBlock.getType();
        final ShapedRecipeBuilder builder = shaped(RecipeCategory.TOOLS, extraBlock, 4);
        builder.pattern("X X");
        builder.pattern(" Z ");
        builder.pattern("X X");
        builder.define('X', type.getMaterial());
        if (type.getColor() == null) {
            builder.define('Z', type.getMaterial());
        } else {
            builder.define('Z', dyeItemFor(type.getColor()));
        }
        builder.unlockedBy("has_material", has(type.getMaterial()));
        if (type.getColor() != null) {
            builder.unlockedBy("has_dye", has(dyeItemFor(type.getColor())));
        }
        builder.save(writer);
    }

    private static Item dyeItemFor(final DyeColor color) {
        return Items.DYE.pick(color);
    }

}
