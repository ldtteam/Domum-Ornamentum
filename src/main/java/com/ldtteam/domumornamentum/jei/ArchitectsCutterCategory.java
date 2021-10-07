package com.ldtteam.domumornamentum.jei;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.ldtteam.domumornamentum.IDomumOrnamentumApi;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockManager;
import com.ldtteam.domumornamentum.recipe.ModRecipeSerializers;
import com.ldtteam.domumornamentum.recipe.architectscutter.ArchitectsCutterRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiIngredient;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

import static com.ldtteam.domumornamentum.util.Constants.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class ArchitectsCutterCategory implements IRecipeCategory<ArchitectsCutterRecipe>
{
    private final JEIPlugin plugin;
    private final IDrawable background;
    private final IDrawable thumb;
    private final IDrawable slot;
    private final IDrawable button;
    private final IDrawable icon;
    private final LoadingCache<ArchitectsCutterRecipe, DisplayData> cachedDisplayData;

    public ArchitectsCutterCategory(@NotNull final IGuiHelper guiHelper, @NotNull final JEIPlugin plugin)
    {
        this.plugin = plugin;
        final ResourceLocation texture = new ResourceLocation(MOD_ID, "textures/gui/container/architectscutter.png");
        this.background = guiHelper.createDrawable(texture, 3, 12, 170, 60);
        this.thumb = guiHelper.createDrawable(texture, 176, 0, 12, 15);
        this.slot = guiHelper.createDrawable(texture, 16, 166, 18, 18);
        this.button = guiHelper.createDrawable(texture, 0, 166, 16, 18);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(IDomumOrnamentumApi.getInstance().getBlocks().getArchitectsCutter()));
        this.cachedDisplayData = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<>()
                {
                    @Override
                    public DisplayData load(@NotNull final ArchitectsCutterRecipe key)
                    {
                        return new DisplayData(key);
                    }
                });
    }

    @NotNull
    @Override
    public ResourceLocation getUid()
    {
        return Objects.requireNonNull(ModRecipeSerializers.ARCHITECTS_CUTTER.getRegistryName());
    }

    @NotNull
    @Override
    public Class<? extends ArchitectsCutterRecipe> getRecipeClass()
    {
        return ArchitectsCutterRecipe.class;
    }

    @NotNull
    @Override
    public Component getTitle()
    {
        return new TranslatableComponent(MOD_ID + ".architectscutter");
    }

    @NotNull
    @Override
    public IDrawable getBackground()
    {
        return this.background;
    }

    @NotNull
    @Override
    public IDrawable getIcon()
    {
        return this.icon;
    }

    @Override
    public void setIngredients(@NotNull final ArchitectsCutterRecipe recipe,
                               @NotNull final IIngredients ingredients)
    {
        final Block generatedBlock = ForgeRegistries.BLOCKS.getValue(recipe.getBlockName());

        if (!(generatedBlock instanceof final IMateriallyTexturedBlock materiallyTexturedBlock))
            return;

        final Collection<IMateriallyTexturedBlockComponent> components = materiallyTexturedBlock.getComponents();
        final List<List<ItemStack>> inputs = components.stream()
                .map(component -> component.getValidSkins().getValues().stream()
                        .map(ItemStack::new)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);

        final List<ItemStack> defaultInputs = components.stream()
                .map(component -> new ItemStack(component.getDefault()))
                .collect(Collectors.toList());

        final DisplayData displayData = cachedDisplayData.getUnchecked(recipe);
        final Container container = displayData.getIngredientContainer();

        for (int i = 0; i < defaultInputs.size(); ++i)
        {
            container.setItem(i, defaultInputs.get(i));
        }

        // for the sake of not polluting JEI too much we'll only show one example output block
        ItemStack output = recipe.assemble(container);
        if (output.isEmpty())   // wat?
        {
            output = recipe.getResultItem();
            if (output.isEmpty())   // WAT?
            {
                output = new ItemStack(generatedBlock);
            }
            output.setCount(Math.max(components.size(), recipe.getCount()));
        }

        displayData.setOutput(output);
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }

    @Override
    public void setRecipe(@NotNull final IRecipeLayout recipeLayout,
                          @NotNull final ArchitectsCutterRecipe recipe,
                          @NotNull final IIngredients ingredients)
    {
        final IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, false, 139, 20);

        for (int slot = 0; slot < IMateriallyTexturedBlockManager.getInstance().getMaxTexturableComponentCount(); ++slot)
        {
            final int x = 5 + ((slot & 1) * 20);
            final int y = 2 + ((slot >> 1) * 20);
            itemStacks.init(1 + slot, true, x, y);
            itemStacks.setBackground(1 + slot, this.slot);
        }

        itemStacks.set(ingredients);

        final DisplayData displayData = cachedDisplayData.getUnchecked(recipe);
        displayData.setItemStacks(itemStacks);
    }

    @Override
    public void draw(@NotNull final ArchitectsCutterRecipe recipe,
                     @NotNull final PoseStack stack,
                     final double mouseX, final double mouseY)
    {
        final DisplayData displayData = cachedDisplayData.getUnchecked(recipe);

        this.thumb.draw(stack, 116, 3);

        this.button.draw(stack, 74, 21);
        this.plugin.getIngredientManager().getIngredientRenderer(VanillaTypes.ITEM)
            .render(stack, 74, 22, recipe.getResultItem());

        displayData.reassembleIfNeeded();
        Objects.requireNonNull(displayData.getItemStacks()).set(0, List.of(displayData.getOutput()));
    }

    private static class DisplayData
    {
        private final ArchitectsCutterRecipe recipe;

        @Nullable
        private IGuiItemStackGroup itemStacks;

        private ItemStack output = ItemStack.EMPTY;

        private final Container ingredientContainer =
                new SimpleContainer(IMateriallyTexturedBlockManager.getInstance().getMaxTexturableComponentCount());

        public DisplayData(@NotNull final ArchitectsCutterRecipe recipe)
        {
            this.recipe = recipe;
        }

        @Nullable
        public IGuiItemStackGroup getItemStacks()
        {
            return this.itemStacks;
        }
        public void setItemStacks(@NotNull final IGuiItemStackGroup itemStacks)
        {
            this.itemStacks = itemStacks;
        }

        @NotNull
        public Container getIngredientContainer()
        {
            return this.ingredientContainer;
        }

        @NotNull
        public ItemStack getOutput()
        {
            return this.output;
        }

        public void setOutput(@NotNull final ItemStack output)
        {
            this.output = output;
        }

        public void reassembleIfNeeded()
        {
            final Map<Integer, ? extends IGuiIngredient<ItemStack>> ingredients = this.itemStacks.getGuiIngredients();

            final List<ItemStack> inputs = new ArrayList<>(ingredients.size());
            for (int i = 0; i < ingredients.size(); ++i)
            {
                final IGuiIngredient<ItemStack> ingredient = ingredients.get(i);
                if (!ingredient.isInput()) continue;

                inputs.add(Objects.requireNonNullElse(ingredient.getDisplayedIngredient(), ItemStack.EMPTY));
            }

            if (!containerMatches(inputs))
            {
                for (int i = 0; i < inputs.size(); ++i)
                {
                    this.ingredientContainer.setItem(i, inputs.get(i));
                }

                this.output = recipe.assemble(this.ingredientContainer);
            }
        }

        private boolean containerMatches(@NotNull final List<ItemStack> inputs)
        {
            for (int i = 0; i < this.ingredientContainer.getContainerSize(); ++i)
            {
                if (!ItemStack.isSameItemSameTags(i < inputs.size() ? inputs.get(i) : ItemStack.EMPTY, this.ingredientContainer.getItem(i)))
                {
                    return false;
                }
            }
            return true;
        }
    }
}
