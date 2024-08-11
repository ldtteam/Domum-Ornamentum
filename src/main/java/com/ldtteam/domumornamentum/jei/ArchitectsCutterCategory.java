package com.ldtteam.domumornamentum.jei;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.ldtteam.domumornamentum.IDomumOrnamentumApi;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockManager;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.item.interfaces.IDoItem;
import com.ldtteam.domumornamentum.recipe.ModRecipeTypes;
import com.ldtteam.domumornamentum.recipe.architectscutter.ArchitectsCutterRecipe;
import com.ldtteam.domumornamentum.recipe.architectscutter.ArchitectsCutterRecipeInput;
import com.ldtteam.domumornamentum.util.Constants;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

import static com.ldtteam.domumornamentum.util.Constants.MOD_ID;
import static com.ldtteam.domumornamentum.util.GuiConstants.*;

@OnlyIn(Dist.CLIENT)
public class ArchitectsCutterCategory implements IRecipeCategory<RecipeHolder<ArchitectsCutterRecipe>>
{
    public static final RecipeType<RecipeHolder<ArchitectsCutterRecipe>> TYPE = RecipeType.createFromVanilla(ModRecipeTypes.ARCHITECTS_CUTTER.get());

    /**
     * Horizontal offset between the real cutter display and the JEI display, since we only show a portion.
     */
    private static final int JEI_OFFSET_X = 55;
    /**
     * Vertical offset between the real cutter display and the JEI display, since we only show a portion.
     */
    private static final int JEI_OFFSET_Y = 14;

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
        final ResourceLocation texture = Constants.resLocDO("textures/gui/container/architectscutter2.png");
        this.background = guiHelper.createDrawable(texture, JEI_OFFSET_X, JEI_OFFSET_Y, CUTTER_BG_W - JEI_OFFSET_X - 9, 88);
        this.thumb = guiHelper.createDrawable(texture, CUTTER_SLIDER_U_DISABLED, CUTTER_SLIDER_V, CUTTER_SLIDER_W, CUTTER_SLIDER_H);
        this.slot = guiHelper.createDrawable(texture, CUTTER_SLOT_U, CUTTER_SLOT_V, CUTTER_SLOT_W, CUTTER_SLOT_H);
        this.button = guiHelper.createDrawable(texture, CUTTER_RECIPE_U_NORMAL, CUTTER_RECIPE_V, CUTTER_RECIPE_W, CUTTER_RECIPE_H);
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
    public RecipeType<RecipeHolder<ArchitectsCutterRecipe>> getRecipeType()
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

    @Override
    public void setRecipe(@NotNull final IRecipeLayoutBuilder builder,
                          @NotNull final RecipeHolder<ArchitectsCutterRecipe> holder,
                          @NotNull final IFocusGroup focuses)
    {
        final ArchitectsCutterRecipe recipe = holder.value();
        final Block generatedBlock = recipe.getBlock();

        if (!(generatedBlock instanceof final IMateriallyTexturedBlock materiallyTexturedBlock))
            return;

        final Collection<IMateriallyTexturedBlockComponent> components = materiallyTexturedBlock.getComponents();
        final List<List<ItemStack>> inputs = components.stream()
                .map(component -> BuiltInRegistries.BLOCK.getTag(component.getValidSkins()).orElseThrow().stream()
                        .map(Holder::value)
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

        ItemStack output = recipe.assemble(new ArchitectsCutterRecipeInput(container), null);
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

        builder.addSlot(RecipeIngredientRole.OUTPUT, CUTTER_OUTPUT_X - JEI_OFFSET_X, CUTTER_OUTPUT_Y - JEI_OFFSET_Y)
                .setCustomRenderer(VanillaTypes.ITEM_STACK, new OutputRenderer(plugin, displayData))
                .addItemStack(output);

        for (int slot = 0; slot < IMateriallyTexturedBlockManager.getInstance().getMaxTexturableComponentCount(); ++slot)
        {
            final int x = CUTTER_INPUT_X - JEI_OFFSET_X;
            final int y = CUTTER_INPUT_Y - JEI_OFFSET_Y + (slot * CUTTER_INPUT_SPACING);
            builder.addSlot(RecipeIngredientRole.INPUT, x, y)
                    .setBackground(this.slot, -1, -1)
                    .addItemStacks(slot < inputs.size() ? inputs.get(slot) : Collections.emptyList());
        }
    }

    @NotNull
    @Override
    public List<Component> getTooltipStrings(@NotNull final RecipeHolder<ArchitectsCutterRecipe> holder,
                                             @NotNull final IRecipeSlotsView recipeSlotsView,
                                             final double mouseX, final double mouseY)
    {
        final ArchitectsCutterRecipe recipe = holder.value();
        final List<Component> tooltips = new ArrayList<>();

        final Rect2i groupButton = new Rect2i(CUTTER_RECIPE_X - JEI_OFFSET_X, CUTTER_RECIPE_Y + 1 - JEI_OFFSET_Y, this.button.getWidth(), this.button.getHeight());
        if (groupButton.contains((int) mouseX, (int) mouseY))
        {
            final DisplayData displayData = cachedDisplayData.getUnchecked(recipe);
            tooltips.add(Component.translatable("cuttergroup." +
                    displayData.getGroupId().getNamespace() + "." + displayData.getGroupId().getPath()));
        }

        final Rect2i recipeButton = new Rect2i(CUTTER_RECIPE_X - JEI_OFFSET_X, CUTTER_RECIPE_Y + 1 - JEI_OFFSET_Y + CUTTER_RECIPE_SPACING, this.button.getWidth(), this.button.getHeight());
        if (recipeButton.contains((int) mouseX, (int) mouseY))
        {
            final DisplayData displayData = cachedDisplayData.getUnchecked(recipe);
            tooltips.add(displayData.getOutput().getHoverName());
        }

        return tooltips;
    }

    @Override
    public void draw(@NotNull final RecipeHolder<ArchitectsCutterRecipe> holder,
                     @NotNull final IRecipeSlotsView recipeSlotsView,
                     @NotNull final GuiGraphics stack,
                     final double mouseX, final double mouseY)
    {
        final ArchitectsCutterRecipe recipe = holder.value();
        final DisplayData displayData = cachedDisplayData.getUnchecked(recipe);
        displayData.reassembleIfNeeded(recipeSlotsView.getSlotViews(RecipeIngredientRole.INPUT));

        this.thumb.draw(stack, CUTTER_SLIDER_X - JEI_OFFSET_X, CUTTER_RECIPE_Y + 1 - JEI_OFFSET_Y);
        this.thumb.draw(stack, CUTTER_SLIDER_X - JEI_OFFSET_X, CUTTER_RECIPE_Y + 1 + CUTTER_RECIPE_SPACING - JEI_OFFSET_Y);

        drawButton(stack, CUTTER_RECIPE_X - JEI_OFFSET_X, CUTTER_RECIPE_Y + 1 - JEI_OFFSET_Y, displayData.getGroup());
        drawButton(stack, CUTTER_RECIPE_X - JEI_OFFSET_X, CUTTER_RECIPE_Y + 1 - JEI_OFFSET_Y + CUTTER_RECIPE_SPACING, displayData.getOutput());
    }

    private void drawButton(@NotNull final GuiGraphics stack, final int x, final int y, @NotNull final ItemStack item)
    {
        this.button.draw(stack, x, y);
        final PoseStack pose = stack.pose();
        pose.pushPose();
        pose.translate(x, y + 1, 0);
        final ItemStack buttonStack = item.copy();
        buttonStack.setCount(1);
        this.plugin.getIngredientManager().getIngredientRenderer(VanillaTypes.ITEM_STACK).render(stack, buttonStack);
        pose.popPose();
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
        public void render(@NotNull final GuiGraphics stack,
                           @NotNull final ItemStack ingredient)
        {
            getRenderer().render(stack, displayData.getOutput());
        }

        @NotNull
        @Override
        @SuppressWarnings("removal")
        public List<Component> getTooltip(@NotNull final ItemStack ingredient,
                                          @NotNull final TooltipFlag tooltipFlag)
        {
            return getRenderer().getTooltip(displayData.getOutput(), tooltipFlag);
        }

        @Override
        public void getTooltip(final ITooltipBuilder tooltip, final ItemStack ingredient, final TooltipFlag tooltipFlag)
        {
            getRenderer().getTooltip(tooltip, ingredient, tooltipFlag);
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

        private ResourceLocation groupId = ResourceLocation.withDefaultNamespace("");
        private ItemStack group = ItemStack.EMPTY;
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
        public ResourceLocation getGroupId()
        {
            return this.groupId;
        }

        @NotNull
        public ItemStack getGroup()
        {
            return this.group;
        }

        @NotNull
        public ItemStack getOutput()
        {
            return this.output;
        }

        public void setOutput(@NotNull final ItemStack output)
        {
            if (output.getItem() instanceof IDoItem doItem)
            {
                this.groupId = doItem.getGroup();
                this.group = ModBlocks.getInstance().getOrComputeItemGroups()
                        .getOrDefault(this.groupId, Collections.singletonList(ItemStack.EMPTY))
                        .get(0);
            }
            else
            {
                this.groupId = ResourceLocation.withDefaultNamespace("");
                this.group = ItemStack.EMPTY;
            }

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
                    if (!ItemStack.isSameItemSameComponents(currentItem.get(), this.ingredientContainer.getItem(i)))
                    {
                        same = false;
                        this.ingredientContainer.setItem(i, currentItem.get());
                    }
                }
            }

            if (!same)
            {
                this.output = recipe.assemble(new ArchitectsCutterRecipeInput(this.ingredientContainer), null);
            }
        }
    }
}
