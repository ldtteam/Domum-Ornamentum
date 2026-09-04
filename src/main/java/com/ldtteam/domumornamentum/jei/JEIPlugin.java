package com.ldtteam.domumornamentum.jei;
import com.ldtteam.domumornamentum.IDomumOrnamentumApi;
import com.ldtteam.domumornamentum.block.IModBlocks;
import com.ldtteam.domumornamentum.client.screens.ArchitectsCutterScreen;
import com.ldtteam.domumornamentum.recipe.architectscutter.ArchitectsCutterRecipe;
import com.ldtteam.domumornamentum.recipe.ModRecipeTypes;
import com.ldtteam.domumornamentum.util.Constants;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeMap;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@JeiPlugin
public class JEIPlugin implements IModPlugin
{
    private IIngredientManager ingredientManager;

    @Nullable
    public IIngredientManager getIngredientManager()
    {
        return this.ingredientManager;
    }

    @NotNull
    @Override
    public Identifier getPluginUid()
    {
        return Constants.resLocDO(Constants.MOD_ID);
    }

    @Override
    public void registerItemSubtypes(@NotNull final ISubtypeRegistration registration)
    {
        final IModBlocks blocks = IDomumOrnamentumApi.getInstance().getBlocks();
        final MaterialSubtypeInterpreter interpreter = MaterialSubtypeInterpreter.getInstance();

        registration.registerSubtypeInterpreter(blocks.getDoor().asItem(), interpreter);
        registration.registerSubtypeInterpreter(blocks.getTrapdoor().asItem(), interpreter);
        registration.registerSubtypeInterpreter(blocks.getFancyDoor().asItem(), interpreter);
        registration.registerSubtypeInterpreter(blocks.getFancyTrapdoor().asItem(), interpreter);
        registration.registerSubtypeInterpreter(blocks.getPost().asItem(), interpreter);
        registration.registerSubtypeInterpreter(blocks.getPanel().asItem(), interpreter);
    }

    @Override
    public void registerCategories(@NotNull final IRecipeCategoryRegistration registration)
    {
        final ArchitectsCutterCategory category = new ArchitectsCutterCategory(registration.getJeiHelpers().getGuiHelper(), this);
        registration.addRecipeCategories(category);
    }

    @Override
    public void registerRecipes(@NotNull final IRecipeRegistration registration)
    {
        final RecipeMap clientRecipes = clientSyncedRecipes();
        final List<RecipeHolder<ArchitectsCutterRecipe>> recipes = clientRecipes == null ? List.of() : clientRecipes.values().stream()
            .filter(holder -> holder.value() instanceof ArchitectsCutterRecipe)
            .map(holder -> new RecipeHolder<>(holder.id(), (ArchitectsCutterRecipe) holder.value()))
            .toList();

        registration.addRecipes(ArchitectsCutterCategory.TYPE, recipes);
    }

    @Nullable
    private static RecipeMap clientSyncedRecipes()
    {
        try
        {
            final Class<?> internal = Class.forName("mezz.jei.common.Internal");
            if (!(internal.getMethod("hasClientSyncedRecipes").invoke(null) instanceof final Boolean available) || !available)
            {
                return null;
            }

            final Object recipes = internal.getMethod("getClientSyncedRecipes").invoke(null);
            return recipes instanceof final RecipeMap recipeMap ? recipeMap : null;
        }
        catch (final ReflectiveOperationException ignored)
        {
            return null;
        }
    }

    @Override
    public void registerRecipeCatalysts(@NotNull final IRecipeCatalystRegistration registration)
    {
        registration.addRecipeCatalyst(VanillaTypes.ITEM_STACK,
                new ItemStack(IDomumOrnamentumApi.getInstance().getBlocks().getArchitectsCutter()),
                ArchitectsCutterCategory.TYPE);
    }

    @Override
    public void registerGuiHandlers(@NotNull final IGuiHandlerRegistration registration)
    {
        registration.addGhostIngredientHandler(ArchitectsCutterScreen.class, new ArchitectsCutterGuiHandler());
    }

    @Override
    public void onRuntimeAvailable(@NotNull final IJeiRuntime jeiRuntime)
    {
        this.ingredientManager = jeiRuntime.getIngredientManager();
    }
}
