package com.ldtteam.domumornamentum.client.screens;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.ModBlocks;
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

public class ArchitectsCutterScreen extends AbstractContainerScreen<ArchitectsCutterContainer>
{
    private static final ResourceLocation BACKGROUND_TEXTURE1 = new ResourceLocation(Constants.MOD_ID, "textures/gui/container/architectscutter.png");
    private static final ResourceLocation BACKGROUND_TEXTURE2 = new ResourceLocation(Constants.MOD_ID, "textures/gui/container/architectscutter2.png");

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
    private static int groupIndexCache = -1;

    /**
     * Variant index cache.
     */
    private static int variantIndexCache = -1;

    public ArchitectsCutterScreen(ArchitectsCutterContainer containerIn, Inventory playerInv, Component titleIn) {
        super(containerIn, playerInv, titleIn);
        --this.titleLabelY;
        this.imageWidth = 242;
        this.imageHeight = 202;
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTicks, int x, int y) {
        this.renderBackground(graphics);
        int guiLeft = this.leftPos;
        int guiTop = this.topPos;

        graphics.blit(getBackGroundTexture(), guiLeft, guiTop, 0, 0, this.imageWidth, this.imageHeight);

        if (this.menu.getCurrentGroup() == null && groupIndexCache != -1)
        {
            (this.menu).clickMenuButton(Objects.requireNonNull(Objects.requireNonNull(this.minecraft).player), groupIndexCache);
            Objects.requireNonNull(this.minecraft.gameMode).handleInventoryButtonClick(this.menu.containerId, groupIndexCache);
        }

        if (this.menu.getCurrentGroup() != null && this.menu.getCurrentVariant() == null && variantIndexCache != -1)
        {
            (this.menu).clickMenuButton(Objects.requireNonNull(Objects.requireNonNull(this.minecraft).player), variantIndexCache);
            Objects.requireNonNull(this.minecraft.gameMode).handleInventoryButtonClick(this.menu.containerId, variantIndexCache);
        }

        if (this.menu.getCurrentGroup() != null)
        {
            int sliderOffset1 = (int) (5.0F * this.recipeSliderProgress);
            graphics.blit(getBackGroundTexture(), guiLeft + 220, guiTop + 40 + sliderOffset1, 0 + (this.canScrollRecipes() ? 0 : 12), 222, 12, 15);
        }

        int sliderOffset2 = (int)(5.0F * this.typeSliderProgress);
        graphics.blit(getBackGroundTexture(), guiLeft + 220, guiTop + 17 + sliderOffset2, 0 + (this.canScrollTypes() ? 0 : 12), 222, 12, 15);

        int recipeAreaLeft = this.leftPos + 8 + 49;
        int recipeAreaTop = this.topPos + 16;


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
            int i = this.leftPos + 8 + 49;
            int j = this.topPos + 16;
            int k = this.typeIndexOffset + 10;
            final List<ResourceLocation> list = new ArrayList<>(ModBlocks.getInstance().getOrComputeItemGroups().keySet());
            for (int l = this.typeIndexOffset; l < k && l < list.size(); ++l)
            {
                int i1 = l - this.typeIndexOffset;
                int j1 = i + i1 % 10 * 16;
                int k1 = j + i1 / 10 * 18 + 2;
                if (x >= j1 && x < j1 + 16 && y >= k1 && y < k1 + 18)
                {
                    graphics.renderTooltip(this.font, Component.translatable("cuttergroup." + list.get(l).getNamespace() + "." + list.get(l).getPath()), x, y);
                }
            }
        }

        if (this.menu.getCurrentGroup() != null)
        {
            List<ItemStack> list = ModBlocks.getInstance().getOrComputeItemGroups().get(this.menu.getCurrentGroup());
            int i = this.leftPos + 8 + 49;
            int j = this.topPos + 16 + 23;
            int k = this.recipeIndexOffset + 10;

            for(int l = this.recipeIndexOffset; l < k && l < list.size(); ++l) {
                int i1 = l - this.recipeIndexOffset;
                int j1 = i + i1 % 10 * 16;
                int k1 = j + i1 / 10 * 18 + 2;
                if (x >= j1 && x < j1 + 16 && y >= k1 && y < k1 + 18) {
                    final ItemStack stack;
                    if (this.menu.outputInventorySlot.hasItem())
                    {
                        final ItemStack input = list.get(l).copy();
                        if (input.hasTag() && this.menu.outputInventorySlot.getItem().hasTag() && this.menu.outputInventorySlot.getItem().getTag().contains("textureData"))
                        {
                            input.getTag().put("textureData", this.menu.outputInventorySlot.getItem().getTag().get("textureData"));
                        }
                        graphics.renderItem(input, k, i1);
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
            int drawLeft = recipeAreaLeft + drawIndex % 10 * 16;
            int rowIndex = drawIndex / 10;
            int drawTop = recipeAreaTop + rowIndex * 18 + 2;
            int zOffset = 32;
            if (this.menu.getCurrentGroup() != null && i == groups.indexOf(this.menu.getCurrentGroup())) {
                zOffset = 0;
            } else if (x >= drawLeft && y >= drawTop && x < drawLeft + 16 && y < drawTop + 18) {
                zOffset = 16;
            }

            graphics.blit(BACKGROUND_TEXTURE1, drawLeft, drawTop - 1, zOffset, this.imageHeight, 16, 18);
        }

        if (this.menu.getCurrentGroup() != null)
        {
            List<ItemStack> list = ModBlocks.getInstance().getOrComputeItemGroups().get(this.menu.getCurrentGroup());
            for (int i = this.recipeIndexOffset; i < recipeIndexOffset + 10 && i < list.size(); ++i)
            {
                int drawIndex = i - this.recipeIndexOffset;
                int drawLeft = recipeAreaLeft + drawIndex % 10 * 16;
                int rowIndex = drawIndex / 10;
                int drawTop = recipeAreaTop + 23 + rowIndex * 18 + 2;
                int zOffset = 32;
                if (this.menu.getCurrentVariant() != null && i == list.indexOf(this.menu.getCurrentVariant())) {
                    zOffset = 0;
                } else if (x >= drawLeft && y >= drawTop && x < drawLeft + 16 && y < drawTop + 18) {
                    zOffset = 16;
                }

                graphics.blit(BACKGROUND_TEXTURE1, drawLeft, drawTop - 1, zOffset, this.imageHeight, 16, 18);
            }
        }
    }

    private void drawSlotBackgrounds(GuiGraphics graphics)
    {
        if (this.menu.getCurrentVariant() != null && this.menu.getCurrentVariant().getItem() instanceof BlockItem item && item.getBlock() instanceof IMateriallyTexturedBlock block)
        {
            final int sourceLeft = 16 * 3;
            final int sourceTop = 202;
            final int numComponents = block.getComponents().size();
            final List<ResourceLocation> input = new ArrayList<>();
            if (item instanceof IDoItem doItem)
            {
                input.addAll(doItem.getInputIds());
            }

            for (int i = 0; i < 2; i++)
            {
                int drawLeft = 95 + this.leftPos;
                int drawTop = this.topPos + 65 + i * 20;
                if (i < input.size())
                {
                    graphics.drawString(this.font, Component.translatable(input.get(i).getNamespace() + ".desc." + input.get(i).getPath(), Component.translatable(Constants.MOD_ID + ".desc.material", "")), drawLeft - 88, drawTop + 5, 4210752, false);
                }
                graphics.blit(BACKGROUND_TEXTURE1, drawLeft, drawTop, sourceLeft + (i >= numComponents ? 18 : 0), sourceTop, 18, 18);
            }
        }
    }

    private void drawRecipesItems(final @NotNull GuiGraphics graphics, int left, int top) {

        final List<ResourceLocation> typeList = new ArrayList<>(ModBlocks.getInstance().getOrComputeItemGroups().keySet());
        for(int i = this.typeIndexOffset; i < this.typeIndexOffset + 10 && i < typeList.size(); ++i) {
            int j = i - this.typeIndexOffset;
            int k = left + j % 10 * 16;
            int l = j / 10;
            int i1 = top + l * 18 + 2;

            graphics.renderItem(ModBlocks.getInstance().getOrComputeItemGroups().get(typeList.get(i)).get(0), k, i1);
        }

        if (this.menu.getCurrentGroup() != null)
        {
            List<ItemStack> list = ModBlocks.getInstance().getOrComputeItemGroups().get(this.menu.getCurrentGroup());
            for (int i = this.recipeIndexOffset; i < this.recipeIndexOffset + 10 && i < list.size(); ++i)
            {
                int j = i - this.recipeIndexOffset;
                int k = left + j % 10 * 16;
                int l = j / 10;
                int i1 = top + 23 + l * 18 + 2;

                if (this.menu.outputInventorySlot.hasItem())
                {
                    final ItemStack input = list.get(i).copy();
                    if (input.hasTag() && this.menu.outputInventorySlot.getItem().hasTag() && this.menu.outputInventorySlot.getItem().getTag().contains("textureData"))
                    {
                        input.getTag().put("textureData", this.menu.outputInventorySlot.getItem().getTag().get("textureData"));
                    }
                    graphics.renderItem(input, k, i1);
                }
                else
                {
                    graphics.renderItem(list.get(i), k, i1);
                }
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.clickedOnRecipeScroll = false;
        this.clickedOnTypeScroll = false;

        if (this.menu.getCurrentGroup() != null) {
            int leftOffset = this.leftPos + 58;
            int topOffset = this.topPos + 16 + 23;
            int scrollOffset = this.recipeIndexOffset + 10;

            for(int index = this.recipeIndexOffset; index < scrollOffset; ++index) {
                int rowIndex = index - this.recipeIndexOffset;
                double mouseXOffset = mouseX - (double)(leftOffset + rowIndex % 10 * 16);
                double mouseYOffset = mouseY - (double)(topOffset + rowIndex / 10 * 18);
                if (mouseXOffset >= 0.0D && mouseYOffset >= 0.0D && mouseXOffset < 16.0D && mouseYOffset < 18.0D && (this.menu).clickMenuButton(Objects.requireNonNull(Objects.requireNonNull(this.minecraft).player), index + ModBlocks.getInstance().itemGroups.size())) {
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                    Objects.requireNonNull(this.minecraft.gameMode).handleInventoryButtonClick(this.menu.containerId, index + ModBlocks.getInstance().itemGroups.size());
                    variantIndexCache = index + ModBlocks.getInstance().itemGroups.size();
                    return true;
                }
            }

            leftOffset = this.leftPos + 220;
            topOffset = this.topPos + 9 + 23;
            if (mouseX >= (double)leftOffset && mouseX < (double)(leftOffset + 12) && mouseY >= (double)topOffset && mouseY < (double)(topOffset + 18)) {
                this.clickedOnRecipeScroll = true;
            }
        }

        if (!clickedOnRecipeScroll)
        {
            int leftOffset = this.leftPos + 58;
            int topOffset = this.topPos + 16;
            int scrollOffset = this.typeIndexOffset + 10;

            for(int index = this.typeIndexOffset; index < scrollOffset; ++index) {
                int rowIndex = index - this.typeIndexOffset;
                double mouseXOffset = mouseX - (double)(leftOffset + rowIndex % 10 * 16);
                double mouseYOffset = mouseY - (double)(topOffset + rowIndex / 10 * 18);
                if (mouseXOffset >= 0.0D && mouseYOffset >= 0.0D && mouseXOffset < 16.0D && mouseYOffset < 18.0D && (this.menu).clickMenuButton(Objects.requireNonNull(Objects.requireNonNull(this.minecraft).player), index)) {
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                    Objects.requireNonNull(this.minecraft.gameMode).handleInventoryButtonClick(this.menu.containerId, index);
                    groupIndexCache = index;
                    return true;
                }
            }

            leftOffset = this.leftPos + 220;
            topOffset = this.topPos + 9;
            if (mouseX >= (double)leftOffset && mouseX < (double)(leftOffset + 12) && mouseY >= (double)topOffset && mouseY < (double)(topOffset + 18)) {
                this.clickedOnTypeScroll = true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.clickedOnRecipeScroll && this.canScrollRecipes()) {
            int i = this.topPos + 16 + 23;
            int j = i + 10;
            this.recipeSliderProgress = ((float)mouseY - (float)i - 7.5F) / ((float)(j - i) - 5.0F);
            this.recipeSliderProgress = Mth.clamp(this.recipeSliderProgress, 0.0F, 1.0F);
            this.recipeIndexOffset = (int)((double)(this.recipeSliderProgress * (float)this.getHiddenRecipeRows()) + 0.5D) * 10;
            return true;
        }
        else if (this.clickedOnTypeScroll && this.canScrollTypes()) {
            int i = this.topPos + 16;
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
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (this.canScrollRecipes()) {
            int i = this.getHiddenRecipeRows();
            this.recipeSliderProgress = (float)((double)this.recipeSliderProgress - delta / (double)i);
            this.recipeSliderProgress = Mth.clamp(this.recipeSliderProgress, 0.0F, 1.0F);
            this.recipeIndexOffset = (int)((double)(this.recipeSliderProgress * (float)i) + 0.5D) * 10;
        }

        if (this.canScrollTypes()) {
            int i = this.getHiddenTypeRows();
            this.typeSliderProgress = (float)((double)this.typeSliderProgress - delta / (double)i);
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
