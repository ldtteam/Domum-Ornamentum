package com.ldtteam.domumornamentum.datagen.global;

import com.google.gson.JsonObject;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.recipe.ModRecipeSerializers;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public class MateriallyTexturedBlockRecipeProvider extends RecipeProvider
{

    public MateriallyTexturedBlockRecipeProvider(final DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    @Override
    protected void buildShapelessRecipes(final Consumer<IFinishedRecipe> consumer)
    {
        ForgeRegistries.BLOCKS.forEach(
          block -> {
              if (Objects.requireNonNull(block.getRegistryName()).getNamespace().equals(Constants.MOD_ID) && block instanceof IMateriallyTexturedBlock) {
                  consumer.accept(new IFinishedRecipe() {
                      @Override
                      public void serializeRecipeData(final JsonObject json)
                      {

                      }

                      @Override
                      public ResourceLocation getId()
                      {
                          return block.getRegistryName();
                      }

                      @Override
                      public IRecipeSerializer<?> getType()
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
                  });
              }
          }
        );
    }

    @Override
    public String getName()
    {
        return "Materially textured block recipes";
    }
}
