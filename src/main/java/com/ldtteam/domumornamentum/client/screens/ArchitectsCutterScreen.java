package com.ldtteam.domumornamentum.client.screens;

import com.ldtteam.domumornamentum.DomumOrnamentum;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.container.ArchitectsCutterContainer;
import com.ldtteam.domumornamentum.item.interfaces.IDoItem;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ldtteam.domumornamentum.util.GuiConstants.*;

public class ArchitectsCutterScreen extends AbstractContainerScreen<ArchitectsCutterContainer>
{
    private static final ResourceLocation BACKGROUND_TEXTURE1 = Constants.resLocDO("textures/gui/container/architectscutter.png");
    private static final ResourceLocation BACKGROUND_TEXTURE2 = Constants.resLocDO("textures/gui/container/architectscutter2.png");

    private float recipeSliderProgress;

    /**
     * Is {@code true} if the player clicked on the scroll wheel in the GUI.
     */
    private boolean clickedOnRecipeScroll;

    /**
     * The index of the first recipe to display.
     * The number of recipes displayed at any time is 10 (10 recipes per row and 1 row). If the player scrolled down one
     * row, this value would be 10 (representing the index of the first slot on the second row).
     */
    private int recipeIndexOffset;

    private float typeSliderProgress;

    /**
     * Is {@code true} if the player clicked on the scroll wheel in the GUI.
     */
    private boolean clickedOnTypeScroll;

    /**
     * The index of the first recipe to display.
     * The number of recipes displayed at any time is 10 (10 recipes per row and 1 row). If the player scrolled down one
     * row, this value would be 10 (representing the index of the first slot on the second row).
     */
    private int typeIndexOffset;

    /**
     * Group index cache.
     */
    private static int groupIndexCache = 0;

    /**
     * Variant index cache.
     */
    private static int variantIndexCache = -1;

    public ArchitectsCutterScreen(ArchitectsCutterContainer containerIn, Inventory playerInv, Component titleIn) {
        super(containerIn, playerInv, titleIn);
        --this.titleLabelY;
        this.imageWidth = CUTTER_BG_W;
        this.imageHeight = CUTTER_BG_H;
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTicks, int x, int y) {
        int guiLeft = this.leftPos;
        int guiTop = this.topPos;

        graphics.blit(getBackGroundTexture(), guiLeft, guiTop, 0, 0, this.imageWidth, this.imageHeight);

        if (this.menu.getCurrentGroup() == null)
        {
            (this.menu).clickMenuButton(Objects.requireNonNull(Objects.requireNonNull(this.minecraft).player), groupIndexCache);
            Objects.requireNonNull(this.minecraft.gameMode).handleInventoryButtonClick(this.menu.containerId, groupIndexCache);
        }

        if (this.menu.getCurrentGroup() != null && this.menu.getCurrentVariant() == null)
        {
            if (variantIndexCache == -1)
            {
                variantIndexCache = ModBlocks.getInstance().itemGroups.size();
            }
            (this.menu).clickMenuButton(Objects.requireNonNull(Objects.requireNonNull(this.minecraft).player), variantIndexCache);
            Objects.requireNonNull(this.minecraft.gameMode).handleInventoryButtonClick(this.menu.containerId, variantIndexCache);
        }

        if (this.menu.getCurrentGroup() != null)
        {
            int sliderOffset1 = (int) (5.0F * this.recipeSliderProgress);
            graphics.blit(getBackGroundTexture(), guiLeft + CUTTER_SLIDER_X, guiTop + CUTTER_SLIDER_Y + CUTTER_RECIPE_SPACING + sliderOffset1, 0 + (this.canScrollRecipes() ? CUTTER_SLIDER_U_ENABLED : CUTTER_SLIDER_U_DISABLED), CUTTER_SLIDER_V, CUTTER_SLIDER_W, CUTTER_SLIDER_H);
        }

        int sliderOffset2 = (int)(5.0F * this.typeSliderProgress);
        graphics.blit(getBackGroundTexture(), guiLeft + CUTTER_SLIDER_X, guiTop + CUTTER_SLIDER_Y + sliderOffset2, 0 + (this.canScrollTypes() ? CUTTER_SLIDER_U_ENABLED : CUTTER_SLIDER_U_DISABLED), CUTTER_SLIDER_V, CUTTER_SLIDER_W, CUTTER_SLIDER_H);

        int recipeAreaLeft = this.leftPos + CUTTER_RECIPE_X;
        int recipeAreaTop = this.topPos + CUTTER_RECIPE_Y;


        this.drawSlotBackgrounds(graphics);
        this.drawRecipeButtonBackgrounds(graphics, x, y, recipeAreaLeft, recipeAreaTop);
        this.drawRecipesItems(graphics, recipeAreaLeft, recipeAreaTop);
    }

    private ResourceLocation getBackGroundTexture()
    {
        return this.menu.getCurrentGroup() == null ? BACKGROUND_TEXTURE1 : BACKGROUND_TEXTURE2;
    }

    @Override
    protected void renderTooltip(@NotNull GuiGraphics graphics, int x, int y) {
        super.renderTooltip(graphics, x, y);
        {
            int i = this.leftPos + CUTTER_RECIPE_X;
            int j = this.topPos + CUTTER_RECIPE_Y;
            int k = this.typeIndexOffset + 10;
            final List<ResourceLocation> list = new ArrayList<>(ModBlocks.getInstance().getOrComputeItemGroups().keySet());
            for (int l = this.typeIndexOffset; l < k && l < list.size(); ++l)
            {
                int i1 = l - this.typeIndexOffset;
                int j1 = i + i1 % 10 * CUTTER_RECIPE_W;
                int k1 = j + i1 / 10 * CUTTER_RECIPE_H + 2;
                if (x >= j1 && x < j1 + CUTTER_RECIPE_W && y >= k1 && y < k1 + CUTTER_RECIPE_H)
                {
                    graphics.renderTooltip(this.font, Component.translatable("cuttergroup." + list.get(l).getNamespace() + "." + list.get(l).getPath()), x, y);
                }
            }
        }

        if (this.menu.getCurrentGroup() != null)
        {
            List<ItemStack> list = ModBlocks.getInstance().getOrComputeItemGroups().get(this.menu.getCurrentGroup());
            int i = this.leftPos + CUTTER_RECIPE_X;
            int j = this.topPos + CUTTER_RECIPE_Y + CUTTER_RECIPE_SPACING;
            int k = this.recipeIndexOffset + 10;

            for(int l = this.recipeIndexOffset; l < k && l < list.size(); ++l) {
                int i1 = l - this.recipeIndexOffset;
                int j1 = i + i1 % 10 * CUTTER_RECIPE_W;
                int k1 = j + i1 / 10 * CUTTER_RECIPE_H + 2;
                if (x >= j1 && x < j1 + CUTTER_RECIPE_W && y >= k1 && y < k1 + CUTTER_RECIPE_H) {
                    final ItemStack stack;
                    if (this.menu.outputInventorySlot.hasItem())
                    {
                        final ItemStack input = list.get(l).copy();
                        texturizeVariantUsingCurrentInput(input);
                        stack = input;
                    }
                    else
                    {
                        stack = list.get(l);
                    }
                    graphics.renderTooltip(this.font, stack, x, y);
                }
            }
        }
    }

    @Override
    protected void renderLabels(GuiGraphics p_281635_, int p_282681_, int p_283686_) {
        p_281635_.drawString(this.font, Component.translatable(Constants.MOD_ID + ".group"), 7, 22, 4210752, false);
        p_281635_.drawString(this.font, Component.translatable(Constants.MOD_ID + ".variant"), 7, 45, 4210752, false);
        p_281635_.drawString(this.font, this.title, this.titleLabelX + 70, this.titleLabelY, 4210752, false);
        p_281635_.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX + 32, this.inventoryLabelY  + 36, 4210752, false);
    }

    private void drawRecipeButtonBackgrounds(GuiGraphics graphics, int x, int y, int recipeAreaLeft, int recipeAreaTop) {

        final List<ResourceLocation> groups = new ArrayList<>(ModBlocks.getInstance().getOrComputeItemGroups().keySet());
        for(int i = this.typeIndexOffset; i < this.typeIndexOffset + 10 && i < groups.size(); ++i) {
            int drawIndex = i - this.typeIndexOffset;
            int drawLeft = recipeAreaLeft + drawIndex % 10 * CUTTER_RECIPE_W;
            int rowIndex = drawIndex / 10;
            int drawTop = recipeAreaTop + rowIndex * CUTTER_RECIPE_H + 2;
            int zOffset = CUTTER_RECIPE_U_NORMAL;
            if (this.menu.getCurrentGroup() != null && i == groups.indexOf(this.menu.getCurrentGroup())) {
                zOffset = CUTTER_RECIPE_U_SELECTED;
            } else if (x >= drawLeft && y >= drawTop && x < drawLeft + CUTTER_RECIPE_W && y < drawTop + CUTTER_RECIPE_H) {
                zOffset = CUTTER_RECIPE_U_HOVERED;
            }

            graphics.blit(BACKGROUND_TEXTURE1, drawLeft, drawTop - 1, zOffset, CUTTER_RECIPE_V, CUTTER_RECIPE_W, CUTTER_RECIPE_H);
        }

        if (this.menu.getCurrentGroup() != null)
        {
            List<ItemStack> list = ModBlocks.getInstance().getOrComputeItemGroups().get(this.menu.getCurrentGroup());
            for (int i = this.recipeIndexOffset; i < recipeIndexOffset + 10 && i < list.size(); ++i)
            {
                int drawIndex = i - this.recipeIndexOffset;
                int drawLeft = recipeAreaLeft + drawIndex % 10 * CUTTER_RECIPE_W;
                int rowIndex = drawIndex / 10;
                int drawTop = recipeAreaTop + CUTTER_RECIPE_SPACING + rowIndex * CUTTER_RECIPE_H + 2;
                int zOffset = CUTTER_RECIPE_U_NORMAL;
                if (this.menu.getCurrentVariant() != null && i == list.indexOf(this.menu.getCurrentVariant())) {
                    zOffset = CUTTER_RECIPE_U_SELECTED;
                } else if (x >= drawLeft && y >= drawTop && x < drawLeft + CUTTER_RECIPE_W && y < drawTop + CUTTER_RECIPE_H) {
                    zOffset = CUTTER_RECIPE_U_HOVERED;
                }

                graphics.blit(BACKGROUND_TEXTURE1, drawLeft, drawTop - 1, zOffset, CUTTER_RECIPE_V, CUTTER_RECIPE_W, CUTTER_RECIPE_H);
            }
        }
    }

    private void drawSlotBackgrounds(GuiGraphics graphics)
    {
        if (this.menu.getCurrentVariant() != null && this.menu.getCurrentVariant().getItem() instanceof BlockItem item && item.getBlock() instanceof IMateriallyTexturedBlock block)
        {
            final int numComponents = block.getComponents().size();
            final List<ResourceLocation> input = new ArrayList<>();
            if (item instanceof IDoItem doItem)
            {
                input.addAll(doItem.getInputIds());
            }

            for (int i = 0; i < 2; i++)
            {
                int drawLeft = CUTTER_INPUT_X - 1 + this.leftPos;
                int drawTop = this.topPos + CUTTER_INPUT_Y - 1 + i * CUTTER_INPUT_SPACING;
                if (i < input.size())
                {
                    graphics.drawString(this.font, Component.translatable(input.get(i).getNamespace() + ".desc." + input.get(i).getPath(), Component.translatable(Constants.MOD_ID + ".desc.material", "")), drawLeft - 88, drawTop + 5, 4210752, false);
                }
                graphics.blit(BACKGROUND_TEXTURE1, drawLeft, drawTop, CUTTER_SLOT_U + (i >= numComponents ? CUTTER_SLOT_W : 0), CUTTER_SLOT_V, CUTTER_SLOT_W, CUTTER_SLOT_H);
            }
        }
    }

    private void drawRecipesItems(final @NotNull GuiGraphics graphics, int left, int top) {

        final List<ResourceLocation> typeList = new ArrayList<>(ModBlocks.getInstance().getOrComputeItemGroups().keySet());
        for(int i = this.typeIndexOffset; i < this.typeIndexOffset + 10 && i < typeList.size(); ++i) {
            int j = i - this.typeIndexOffset;
            int k = left + j % 10 * CUTTER_RECIPE_W;
            int l = j / 10;
            int i1 = top + l * CUTTER_RECIPE_H + 2;

            final ResourceLocation type = typeList.get(i);
            if (ModBlocks.getInstance().getOrComputeItemGroups().get(type).isEmpty())
            {
                DomumOrnamentum.LOGGER.error("Empty Item Category: " + type);
                continue;
            }

            graphics.renderItem(ModBlocks.getInstance().getOrComputeItemGroups().get(typeList.get(i)).get(0), k, i1);
        }

        if (this.menu.getCurrentGroup() != null)
        {
            List<ItemStack> list = ModBlocks.getInstance().getOrComputeItemGroups().get(this.menu.getCurrentGroup());
            for (int i = this.recipeIndexOffset; i < this.recipeIndexOffset + 10 && i < list.size(); ++i)
            {
                int j = i - this.recipeIndexOffset;
                int k = left + j % 10 * CUTTER_RECIPE_W;
                int l = j / 10;
                int i1 = top + CUTTER_RECIPE_SPACING + l * CUTTER_RECIPE_H + 2;

                if (this.menu.outputInventorySlot.hasItem())
                {
                    final ItemStack input = list.get(i).copy();
                    texturizeVariantUsingCurrentInput(input);
                    graphics.renderItem(input, k, i1);
                }
                else
                {
                    graphics.renderItem(list.get(i), k, i1);
                }
            }
        }
    }

    private void texturizeVariantUsingCurrentInput(final ItemStack variantItemStack)
    {
        if (!(variantItemStack.getItem() instanceof final BlockItem bi && bi.getBlock() instanceof final IMateriallyTexturedBlock block))
        {
            return;
        }

        final MaterialTextureData.Builder textureData = MaterialTextureData.builder();
        int i = 0;
        for (final IMateriallyTexturedBlockComponent component : block.getComponents())
        {
            if (this.menu.inputInventory.getItem(i).getItem() instanceof final BlockItem blockItem)
            {
                textureData.setComponent(component.getId(), blockItem.getBlock());
            }
            i++;
        }
        textureData.putIntoItemStack(variantItemStack);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.clickedOnRecipeScroll = false;
        this.clickedOnTypeScroll = false;

        if (this.menu.getCurrentGroup() != null) {
            int leftOffset = this.leftPos + CUTTER_RECIPE_X + 1;
            int topOffset = this.topPos + CUTTER_RECIPE_Y + CUTTER_RECIPE_SPACING;
            int scrollOffset = this.recipeIndexOffset + 10;

            for(int index = this.recipeIndexOffset; index < scrollOffset; ++index) {
                int rowIndex = index - this.recipeIndexOffset;
                double mouseXOffset = mouseX - (double)(leftOffset + rowIndex % 10 * CUTTER_RECIPE_W);
                double mouseYOffset = mouseY - (double)(topOffset + rowIndex / 10 * CUTTER_RECIPE_H);
                if (mouseXOffset >= 0.0D && mouseYOffset >= 0.0D && mouseXOffset < CUTTER_RECIPE_W && mouseYOffset < CUTTER_RECIPE_H && (this.menu).clickMenuButton(Objects.requireNonNull(Objects.requireNonNull(this.minecraft).player), index + ModBlocks.getInstance().itemGroups.size())) {
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                    Objects.requireNonNull(this.minecraft.gameMode).handleInventoryButtonClick(this.menu.containerId, index + ModBlocks.getInstance().itemGroups.size());
                    variantIndexCache = index + ModBlocks.getInstance().itemGroups.size();
                    return true;
                }
            }

            leftOffset = this.leftPos + CUTTER_SLIDER_X;
            if (mouseX >= (double)leftOffset && mouseX < (double)(leftOffset + CUTTER_SLIDER_W) && mouseY >= (double)topOffset && mouseY < (double)(topOffset + CUTTER_RECIPE_H)) {
                this.clickedOnRecipeScroll = true;
            }
        }

        if (!clickedOnRecipeScroll)
        {
            int leftOffset = this.leftPos + CUTTER_RECIPE_X + 1;
            int topOffset = this.topPos + CUTTER_RECIPE_Y;
            int scrollOffset = this.typeIndexOffset + 10;

            for(int index = this.typeIndexOffset; index < scrollOffset; ++index) {
                int rowIndex = index - this.typeIndexOffset;
                double mouseXOffset = mouseX - (double)(leftOffset + rowIndex % 10 * CUTTER_RECIPE_W);
                double mouseYOffset = mouseY - (double)(topOffset + rowIndex / 10 * CUTTER_RECIPE_H);
                if (mouseXOffset >= 0.0D && mouseYOffset >= 0.0D && mouseXOffset < CUTTER_RECIPE_W && mouseYOffset < CUTTER_RECIPE_H && (this.menu).clickMenuButton(Objects.requireNonNull(Objects.requireNonNull(this.minecraft).player), index)) {
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                    Objects.requireNonNull(this.minecraft.gameMode).handleInventoryButtonClick(this.menu.containerId, index);
                    groupIndexCache = index;
                    recipeIndexOffset = 0;
                    recipeSliderProgress = 0;
                    return true;
                }
            }

            leftOffset = this.leftPos + CUTTER_SLIDER_X;
            if (mouseX >= (double)leftOffset && mouseX < (double)(leftOffset + CUTTER_SLIDER_W) && mouseY >= (double)topOffset && mouseY < (double)(topOffset + CUTTER_RECIPE_H)) {
                this.clickedOnTypeScroll = true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.clickedOnRecipeScroll && this.canScrollRecipes()) {
            int i = this.topPos + CUTTER_RECIPE_Y + CUTTER_RECIPE_SPACING;
            int j = i + 10;
            this.recipeSliderProgress = ((float)mouseY - (float)i - 7.5F) / ((float)(j - i) - 5.0F);
            this.recipeSliderProgress = Mth.clamp(this.recipeSliderProgress, 0.0F, 1.0F);
            this.recipeIndexOffset = (int)((double)(this.recipeSliderProgress * (float)this.getHiddenRecipeRows()) + 0.5D) * 10;
            return true;
        }
        else if (this.clickedOnTypeScroll && this.canScrollTypes()) {
            int i = this.topPos + CUTTER_RECIPE_Y;
            int j = i + 10;
            this.typeSliderProgress = ((float)mouseY - (float)i - 7.5F) / ((float)(j - i) - 5.0F);
            this.typeSliderProgress = Mth.clamp(this.typeSliderProgress, 0.0F, 1.0F);
            this.typeIndexOffset = (int)((double)(this.typeSliderProgress * (float)this.getHiddenTypeRows()) + 0.5D) * 10;
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {

        boolean onlyTypes = false;
        if (mouseX >= this.leftPos + 55 && mouseY >= this.topPos + 15 && mouseX < this.leftPos + CUTTER_SLIDER_X && mouseY < this.topPos + 35) {
            onlyTypes = true;
        }

        boolean onlyRecipes = false;
        if (mouseX >= this.leftPos + 55 && mouseY >= this.topPos + 40 && mouseX < this.leftPos + CUTTER_SLIDER_X && mouseY < this.topPos + 60) {
            onlyRecipes = true;
        }

        if (this.canScrollRecipes() && !onlyTypes) {
            int i = this.getHiddenRecipeRows();
            this.recipeSliderProgress = (float)((double)this.recipeSliderProgress - deltaY / (double)i);
            this.recipeSliderProgress = Mth.clamp(this.recipeSliderProgress, 0.0F, 1.0F);
            this.recipeIndexOffset = (int)((double)(this.recipeSliderProgress * (float)i) + 0.5D) * 10;
        }

        if (this.canScrollTypes() && !onlyRecipes) {
            int i = this.getHiddenTypeRows();
            this.typeSliderProgress = (float)((double)this.typeSliderProgress - deltaY / (double)i);
            this.typeSliderProgress = Mth.clamp(this.typeSliderProgress, 0.0F, 1.0F);
            this.typeIndexOffset = (int)((double)(this.typeSliderProgress * (float)i) + 0.5D) * 10;
        }

        return true;
    }

    private boolean canScrollRecipes() {
        return this.menu.getCurrentGroup() != null && ModBlocks.getInstance().getOrComputeItemGroups().get(this.menu.getCurrentGroup()).size() > 10;
    }

    private boolean canScrollTypes() {
        return ModBlocks.getInstance().getOrComputeItemGroups().size() > 10;
    }

    protected int getHiddenRecipeRows() {
        return this.menu.getCurrentGroup() == null ? 0 : (ModBlocks.getInstance().getOrComputeItemGroups().get(this.menu.getCurrentGroup()).size() + 10 - 1) / 10 - 1;
    }

    protected int getHiddenTypeRows() {
        return (ModBlocks.getInstance().getOrComputeItemGroups().size() + 10 - 1) / 10 - 1;
    }
}
