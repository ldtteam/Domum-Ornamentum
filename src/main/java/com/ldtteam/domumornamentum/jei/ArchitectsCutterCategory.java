package com.ldtteam.domumornamentum.jei;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.ldtteam.domumornamentum.IDomumOrnamentumApi;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockManager;
import com.ldtteam.domumornamentum.recipe.architectscutter.ArchitectsCutterRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredientRenderer;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
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
    public static final RecipeType<ArchitectsCutterRecipe> TYPE
            = RecipeType.create(MOD_ID, "architects_cutter", ArchitectsCutterRecipe.class);

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
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(IDomumOrnamentumApi.getInstance().getBlocks().getArchitectsCutter()));
        this.cachedDisplayData = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<>()
                {
                    @NotNull
                    @Override
                    public DisplayData load(@NotNull final ArchitectsCutterRecipe key)
                    {
                        return new DisplayData(key);
                    }
                });
    }

    @NotNull
    @Override
    public RecipeType<ArchitectsCutterRecipe> getRecipeType()
    {
        return TYPE;
    }

    @NotNull
    @Override
    public Component getTitle()
    {
        return Component.translatable(MOD_ID + ".architectscutter");
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

    @Nullable
    @Override
    public ResourceLocation getRegistryName(@NotNull final ArchitectsCutterRecipe recipe)
    {
        return recipe.getId();
    }

    @Override
    public void setRecipe(@NotNull final IRecipeLayoutBuilder builder,
                          @NotNull final ArchitectsCutterRecipe recipe,
                          @NotNull final IFocusGroup focuses)
    {
        final Block generatedBlock = ForgeRegistries.BLOCKS.getValue(recipe.getBlockName());

        if (!(generatedBlock instanceof final IMateriallyTexturedBlock materiallyTexturedBlock))
            return;

        final Collection<IMateriallyTexturedBlockComponent> components = materiallyTexturedBlock.getComponents();
        final List<List<ItemStack>> inputs = components.stream()
                .map(component -> ForgeRegistries.BLOCKS.tags().getTag(component.getValidSkins()).stream()
                        .map(ItemStack::new)
                        .collect(Collectors.collectingAndThen(
                                Collectors.toCollection(ArrayList::new),
                                list ->
                                {
                                    Collections.shuffle(list);
                                    return list;
                                })))
                .collect(Collectors.toList());

        final List<ItemStack> defaultInputs = components.stream()
                .map(component -> new ItemStack(component.getDefault()))
                .collect(Collectors.toList());

        final DisplayData displayData = cachedDisplayData.getUnchecked(recipe);
        final Container container = displayData.getIngredientContainer();

        for (int i = 0; i < defaultInputs.size(); ++i)
        {
            container.setItem(i, defaultInputs.get(i));
        }

        ItemStack output = recipe.assemble(container, null);
        if (output.isEmpty())   // wat?
        {
            output = recipe.getResultItem(null);
            if (output.isEmpty())   // WAT?
            {
                output = new ItemStack(generatedBlock);
            }
            output.setCount(Math.max(components.size(), recipe.getCount()));
        }
        displayData.setOutput(output);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 140, 21)
                .setCustomRenderer(VanillaTypes.ITEM_STACK, new OutputRenderer(plugin, displayData))
                .addItemStack(output);

        for (int slot = 0; slot < IMateriallyTexturedBlockManager.getInstance().getMaxTexturableComponentCount(); ++slot)
        {
            final int x = 5 + ((slot & 1) * 20);
            final int y = 2 + ((slot >> 1) * 20);
            builder.addSlot(RecipeIngredientRole.INPUT, x, y)
                    .setBackground(this.slot, -1, -1)
                    .addItemStacks(slot < inputs.size() ? inputs.get(slot) : Collections.emptyList());
        }
    }

    @Override
    public void draw(@NotNull final ArchitectsCutterRecipe recipe,
                     @NotNull final IRecipeSlotsView recipeSlotsView,
                     @NotNull final PoseStack stack,
                     final double mouseX, final double mouseY)
    {
        final DisplayData displayData = cachedDisplayData.getUnchecked(recipe);
        displayData.reassembleIfNeeded(recipeSlotsView.getSlotViews(RecipeIngredientRole.INPUT));

        this.thumb.draw(stack, 116, 3);

        this.button.draw(stack, 75, 21);
        stack.pushPose();
        stack.translate(75, 22, 0);
        final ItemStack buttonStack = displayData.getOutput().copy();
        buttonStack.setCount(1);
        this.plugin.getIngredientManager().getIngredientRenderer(VanillaTypes.ITEM_STACK)
                        .render(stack, buttonStack);
        stack.popPose();
    }

    private static class OutputRenderer implements IIngredientRenderer<ItemStack>
    {
        private final JEIPlugin plugin;
        private final DisplayData displayData;

        private IIngredientRenderer<ItemStack> renderer;

        public OutputRenderer(@NotNull final JEIPlugin plugin,
                              @NotNull final DisplayData displayData)
        {
            this.plugin = plugin;
            this.displayData = displayData;
        }

        private IIngredientRenderer<ItemStack> getRenderer()
        {
            if (renderer == null)
            {
                renderer = plugin.getIngredientManager().getIngredientRenderer(VanillaTypes.ITEM_STACK);
            }
            return renderer;
        }

        @Override
        public void render(@NotNull final PoseStack stack,
                           @NotNull final ItemStack ingredient)
        {
            getRenderer().render(stack, displayData.getOutput());
        }

        @NotNull
        @Override
        public List<Component> getTooltip(@NotNull final ItemStack ingredient,
                                          @NotNull final TooltipFlag tooltipFlag)
        {
            return getRenderer().getTooltip(displayData.getOutput(), tooltipFlag);
        }

        @NotNull
        @Override
        public Font getFontRenderer(@NotNull final Minecraft minecraft,
                                    @NotNull final ItemStack ingredient)
        {
            return getRenderer().getFontRenderer(minecraft, displayData.getOutput());
        }

        @Override
        public int getWidth()
        {
            return 16;
        }

        @Override
        public int getHeight()
        {
            return 16;
        }
    }

    private static class DisplayData
    {
        private final ArchitectsCutterRecipe recipe;

        private ItemStack output = ItemStack.EMPTY;

        private final Container ingredientContainer =
                new SimpleContainer(IMateriallyTexturedBlockManager.getInstance().getMaxTexturableComponentCount());

        public DisplayData(@NotNull final ArchitectsCutterRecipe recipe)
        {
            this.recipe = recipe;
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

        public void reassembleIfNeeded(@NotNull final List<IRecipeSlotView> slotViews)
        {
            boolean same = true;

            for (int i = 0; i < slotViews.size(); ++i)
            {
                final Optional<ItemStack> currentItem = slotViews.get(i).getDisplayedItemStack();

                if (currentItem.isPresent())
                {
                    if (!ItemStack.isSameItemSameTags(currentItem.get(), this.ingredientContainer.getItem(i)))
                    {
                        same = false;
                        this.ingredientContainer.setItem(i, currentItem.get());
                    }
                }
            }

            if (!same)
            {
                this.output = recipe.assemble(this.ingredientContainer, null);
            }
        }
    }
}
