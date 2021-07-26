package com.ldtteam.domumornamentum.client.screens;

import com.ldtteam.domumornamentum.block.MateriallyTexturedBlockManager;
import com.ldtteam.domumornamentum.container.ArchitectsCutterContainer;
import com.ldtteam.domumornamentum.recipe.architectscutter.ArchitectsCutterRecipe;
import com.ldtteam.domumornamentum.util.Constants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class ArchitectsCutterScreen extends AbstractContainerScreen<ArchitectsCutterContainer>
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

    public ArchitectsCutterScreen(ArchitectsCutterContainer containerIn, Inventory playerInv, Component titleIn) {
        super(containerIn, playerInv, titleIn);
        containerIn.setInventoryUpdateListener(this::onInventoryUpdate);
        --this.titleLabelY;
    }

    public void render(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    protected void renderBg(@NotNull PoseStack matrixStack, float partialTicks, int x, int y) {
        this.renderBackground(matrixStack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        int guiLeft = this.leftPos;
        int guiTop = this.topPos;
        this.blit(matrixStack, guiLeft, guiTop, 0, 0, this.imageWidth, this.imageHeight);
        int sliderOffset = (int)(41.0F * this.sliderProgress);
        this.blit(matrixStack, guiLeft + 119, guiTop + 15 + sliderOffset, 176 + (this.canScroll() ? 0 : 12), 0, 12, 15);
        int recipeAreaLeft = this.leftPos + 52;
        int recipeAreaTop = this.topPos + 14;
        int recipeIndexDrawOffset = this.recipeIndexOffset + 12;
        this.drawSlotBackgrounds(matrixStack);
        this.drawRecipeButtonBackgrounds(matrixStack, x, y, recipeAreaLeft, recipeAreaTop, recipeIndexDrawOffset);
        this.drawRecipesItems(recipeAreaLeft, recipeAreaTop, recipeIndexDrawOffset);
    }

    protected void renderTooltip(@NotNull PoseStack matrixStack, int x, int y) {
        super.renderTooltip(matrixStack, x, y);
        if (this.hasItemsInInputSlot) {
            int i = this.leftPos + 52;
            int j = this.topPos + 14;
            int k = this.recipeIndexOffset + 12;
            List<ArchitectsCutterRecipe> list = this.menu.getRecipeList();

            for(int l = this.recipeIndexOffset; l < k && l < this.menu.getRecipeListSize(); ++l) {
                int i1 = l - this.recipeIndexOffset;
                int j1 = i + i1 % 4 * 16;
                int k1 = j + i1 / 4 * 18 + 2;
                if (x >= j1 && x < j1 + 16 && y >= k1 && y < k1 + 18) {
                    this.renderTooltip(matrixStack, list.get(l).assemble(this.menu.inputInventory), x, y);
                }
            }
        }

    }

    private void drawRecipeButtonBackgrounds(PoseStack matrixStack, int x, int y, int recipeAreaLeft, int recipeAreaTop, int recipeIndexDrawOffset) {
        for(int i = this.recipeIndexOffset; i < recipeIndexDrawOffset && i < this.menu.getRecipeListSize(); ++i) {
            int drawIndex = i - this.recipeIndexOffset;
            int drawLeft = recipeAreaLeft + drawIndex % 4 * 16;
            int rowIndex = drawIndex / 4;
            int drawTop = recipeAreaTop + rowIndex * 18 + 2;
            int ySize = this.imageHeight;
            if (i == this.menu.getSelectedRecipe()) {
                ySize += 18;
            } else if (x >= drawLeft && y >= drawTop && x < drawLeft + 16 && y < drawTop + 18) {
                ySize += 36;
            }

            this.blit(matrixStack, drawLeft, drawTop - 1, 0, ySize, 16, 18);
        }

    }

    private void drawSlotBackgrounds(PoseStack matrixStack) {
        final int sourceLeft = 16;
        final int sourceTop = 166;

        for (int i = 0; i < MateriallyTexturedBlockManager.getInstance().getMaxTexturableComponentCount(); i++)
        {
            int rowIndex = i / 2;
            int columnIndex = i % 2;

            int drawLeft = 9 + columnIndex * 20 + this.leftPos;
            int drawTop = 15 + rowIndex * 18 + this.topPos;

            this.blit(matrixStack, drawLeft, drawTop, sourceLeft, sourceTop, 18,18);
        }
    }

    private void drawRecipesItems(int left, int top, int recipeIndexOffsetMax) {
        List<ArchitectsCutterRecipe> list = this.menu.getRecipeList();

        for(int i = this.recipeIndexOffset; i < recipeIndexOffsetMax && i < this.menu.getRecipeListSize(); ++i) {
            int j = i - this.recipeIndexOffset;
            int k = left + j % 4 * 16;
            int l = j / 4;
            int i1 = top + l * 18 + 2;
            Objects.requireNonNull(this.minecraft).getItemRenderer().renderAndDecorateItem(list.get(i).assemble(this.menu.inputInventory), k, i1);
        }

    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.clickedOnSroll = false;
        if (this.hasItemsInInputSlot) {
            int i = this.leftPos + 52;
            int j = this.topPos + 14;
            int k = this.recipeIndexOffset + 12;

            for(int l = this.recipeIndexOffset; l < k; ++l) {
                int i1 = l - this.recipeIndexOffset;
                double d0 = mouseX - (double)(i + i1 % 4 * 16);
                double d1 = mouseY - (double)(j + i1 / 4 * 18);
                if (d0 >= 0.0D && d1 >= 0.0D && d0 < 16.0D && d1 < 18.0D && this.menu.clickMenuButton(Objects.requireNonNull(Objects.requireNonNull(this.minecraft).player), l)) {
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                    Objects.requireNonNull(this.minecraft.gameMode).handleInventoryButtonClick((this.menu).containerId, l);
                    return true;
                }
            }

            i = this.leftPos + 119;
            j = this.topPos + 9;
            if (mouseX >= (double)i && mouseX < (double)(i + 12) && mouseY >= (double)j && mouseY < (double)(j + 54)) {
                this.clickedOnSroll = true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.clickedOnSroll && this.canScroll()) {
            int i = this.topPos + 14;
            int j = i + 54;
            this.sliderProgress = ((float)mouseY - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
            this.sliderProgress = Mth.clamp(this.sliderProgress, 0.0F, 1.0F);
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
            this.sliderProgress = Mth.clamp(this.sliderProgress, 0.0F, 1.0F);
            this.recipeIndexOffset = (int)((double)(this.sliderProgress * (float)i) + 0.5D) * 4;
        }

        return true;
    }

    private boolean canScroll() {
        return this.hasItemsInInputSlot && this.menu.getRecipeListSize() > 12;
    }

    protected int getHiddenRows() {
        return (this.menu.getRecipeListSize() + 4 - 1) / 4 - 3;
    }

    /**
     * Called every time this screen's container is changed (is marked as dirty).
     */
    private void onInventoryUpdate() {
        this.hasItemsInInputSlot = this.menu.hasItemsInInputSlots();
        if (!this.hasItemsInInputSlot) {
            this.sliderProgress = 0.0F;
            this.recipeIndexOffset = 0;
        }

    }
}
