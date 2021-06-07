package com.ldtteam.domumornamentum.client.screens;

import com.ldtteam.domumornamentum.block.MateriallyTexturedBlockManager;
import com.ldtteam.domumornamentum.container.ArchitectsCutterContainer;
import com.ldtteam.domumornamentum.recipe.architectscutter.ArchitectsCutterRecipe;
import com.ldtteam.domumornamentum.util.Constants;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public class ArchitectsCutterScreen extends ContainerScreen<ArchitectsCutterContainer>
{
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Constants.MOD_ID, "textures/gui/container/architectscutter.png");
    private              float            sliderProgress;
    /** Is {@code true} if the player clicked on the scroll wheel in the GUI. */
    private              boolean          clickedOnSroll;
    /**
     * The index of the first recipe to display.
     * The number of recipes displayed at any time is 12 (4 recipes per row, and 3 rows). If the player scrolled down one
     * row, this value would be 4 (representing the index of the first slot on the second row).
     */
    private              int              recipeIndexOffset;
    private boolean hasItemsInInputSlot;

    public ArchitectsCutterScreen(ArchitectsCutterContainer containerIn, PlayerInventory playerInv, ITextComponent titleIn) {
        super(containerIn, playerInv, titleIn);
        containerIn.setInventoryUpdateListener(this::onInventoryUpdate);
        --this.titleY;
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        this.renderBackground(matrixStack);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        int guiLeft = this.guiLeft;
        int guiTop = this.guiTop;
        this.blit(matrixStack, guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
        int sliderOffset = (int)(41.0F * this.sliderProgress);
        this.blit(matrixStack, guiLeft + 119, guiTop + 15 + sliderOffset, 176 + (this.canScroll() ? 0 : 12), 0, 12, 15);
        int recipeAreaLeft = this.guiLeft + 52;
        int recipeAreaTop = this.guiTop + 14;
        int recipeIndexDrawOffset = this.recipeIndexOffset + 12;
        this.drawSlotBackgrounds(matrixStack);
        this.drawRecipeButtonBackgrounds(matrixStack, x, y, recipeAreaLeft, recipeAreaTop, recipeIndexDrawOffset);
        this.drawRecipesItems(recipeAreaLeft, recipeAreaTop, recipeIndexDrawOffset);
    }

    protected void renderHoveredTooltip(MatrixStack matrixStack, int x, int y) {
        super.renderHoveredTooltip(matrixStack, x, y);
        if (this.hasItemsInInputSlot) {
            int i = this.guiLeft + 52;
            int j = this.guiTop + 14;
            int k = this.recipeIndexOffset + 12;
            List<ArchitectsCutterRecipe> list = this.container.getRecipeList();

            for(int l = this.recipeIndexOffset; l < k && l < this.container.getRecipeListSize(); ++l) {
                int i1 = l - this.recipeIndexOffset;
                int j1 = i + i1 % 4 * 16;
                int k1 = j + i1 / 4 * 18 + 2;
                if (x >= j1 && x < j1 + 16 && y >= k1 && y < k1 + 18) {
                    this.renderTooltip(matrixStack, list.get(l).getCraftingResult(this.container.inputInventory), x, y);
                }
            }
        }

    }

    private void drawRecipeButtonBackgrounds(MatrixStack matrixStack, int x, int y, int recipeAreaLeft, int recipeAreaTop, int recipeIndexDrawOffset) {
        for(int i = this.recipeIndexOffset; i < recipeIndexDrawOffset && i < this.container.getRecipeListSize(); ++i) {
            int drawIndex = i - this.recipeIndexOffset;
            int drawLeft = recipeAreaLeft + drawIndex % 4 * 16;
            int rowIndex = drawIndex / 4;
            int drawTop = recipeAreaTop + rowIndex * 18 + 2;
            int ySize = this.ySize;
            if (i == this.container.getSelectedRecipe()) {
                ySize += 18;
            } else if (x >= drawLeft && y >= drawTop && x < drawLeft + 16 && y < drawTop + 18) {
                ySize += 36;
            }

            this.blit(matrixStack, drawLeft, drawTop - 1, 0, ySize, 16, 18);
        }

    }

    private void drawSlotBackgrounds(MatrixStack matrixStack) {
        final int sourceLeft = 16;
        final int sourceTop = 166;

        for (int i = 0; i < MateriallyTexturedBlockManager.getInstance().getMaxTexturableComponentCount(); i++)
        {
            int rowIndex = i / 2;
            int columnIndex = i % 2;

            int drawLeft = 9 + columnIndex * 20 + this.guiLeft;
            int drawTop = 15 + rowIndex * 18 + this.guiTop;

            this.blit(matrixStack, drawLeft, drawTop, sourceLeft, sourceTop, 18,18);
        }
    }

    private void drawRecipesItems(int left, int top, int recipeIndexOffsetMax) {
        List<ArchitectsCutterRecipe> list = this.container.getRecipeList();

        for(int i = this.recipeIndexOffset; i < recipeIndexOffsetMax && i < this.container.getRecipeListSize(); ++i) {
            int j = i - this.recipeIndexOffset;
            int k = left + j % 4 * 16;
            int l = j / 4;
            int i1 = top + l * 18 + 2;
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(list.get(i).getCraftingResult(this.container.inputInventory), k, i1);
        }

    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.clickedOnSroll = false;
        if (this.hasItemsInInputSlot) {
            int i = this.guiLeft + 52;
            int j = this.guiTop + 14;
            int k = this.recipeIndexOffset + 12;

            for(int l = this.recipeIndexOffset; l < k; ++l) {
                int i1 = l - this.recipeIndexOffset;
                double d0 = mouseX - (double)(i + i1 % 4 * 16);
                double d1 = mouseY - (double)(j + i1 / 4 * 18);
                if (d0 >= 0.0D && d1 >= 0.0D && d0 < 16.0D && d1 < 18.0D && this.container.enchantItem(this.minecraft.player, l)) {
                    Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                    this.minecraft.playerController.sendEnchantPacket((this.container).windowId, l);
                    return true;
                }
            }

            i = this.guiLeft + 119;
            j = this.guiTop + 9;
            if (mouseX >= (double)i && mouseX < (double)(i + 12) && mouseY >= (double)j && mouseY < (double)(j + 54)) {
                this.clickedOnSroll = true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.clickedOnSroll && this.canScroll()) {
            int i = this.guiTop + 14;
            int j = i + 54;
            this.sliderProgress = ((float)mouseY - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
            this.sliderProgress = MathHelper.clamp(this.sliderProgress, 0.0F, 1.0F);
            this.recipeIndexOffset = (int)((double)(this.sliderProgress * (float)this.getHiddenRows()) + 0.5D) * 4;
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (this.canScroll()) {
            int i = this.getHiddenRows();
            this.sliderProgress = (float)((double)this.sliderProgress - delta / (double)i);
            this.sliderProgress = MathHelper.clamp(this.sliderProgress, 0.0F, 1.0F);
            this.recipeIndexOffset = (int)((double)(this.sliderProgress * (float)i) + 0.5D) * 4;
        }

        return true;
    }

    private boolean canScroll() {
        return this.hasItemsInInputSlot && this.container.getRecipeListSize() > 12;
    }

    protected int getHiddenRows() {
        return (this.container.getRecipeListSize() + 4 - 1) / 4 - 3;
    }

    /**
     * Called every time this screen's container is changed (is marked as dirty).
     */
    private void onInventoryUpdate() {
        this.hasItemsInInputSlot = this.container.hasItemsInInputSlots();
        if (!this.hasItemsInInputSlot) {
            this.sliderProgress = 0.0F;
            this.recipeIndexOffset = 0;
        }

    }
}
