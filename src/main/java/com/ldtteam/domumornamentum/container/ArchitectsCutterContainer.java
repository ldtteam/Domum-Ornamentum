package com.ldtteam.domumornamentum.container;

import com.google.common.collect.Lists;
import com.ldtteam.domumornamentum.block.*;
import com.ldtteam.domumornamentum.recipe.ModRecipeTypes;
import com.ldtteam.domumornamentum.recipe.architectscutter.ArchitectsCutterRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ArchitectsCutterContainer extends AbstractContainerMenu
{
    private final ContainerLevelAccess        worldPosCallable;
    private final DataSlot       selectedRecipe          = DataSlot.standalone();
    private final Level                        world;
    private       List<ArchitectsCutterRecipe> recipes = Lists.newArrayList();

    private final NonNullList<ItemStack> inputItemStacks = NonNullList.withSize(MateriallyTexturedBlockManager.getInstance().getMaxTexturableComponentCount(), ItemStack.EMPTY);
    private       long                   lastOnTake;
    final List<Slot>                        inputInventorySlots = Lists.newArrayList();
    final         Slot                     outputInventorySlot;
    private       Runnable                 inventoryUpdateListener = () -> {};
    public final  Container               inputInventory          = new SimpleContainer(MateriallyTexturedBlockManager.getInstance().getMaxTexturableComponentCount()) {
        public void setChanged() {
            super.setChanged();
            ArchitectsCutterContainer.this.slotsChanged(this);
            ArchitectsCutterContainer.this.inventoryUpdateListener.run();
        }
    };
    private final ResultContainer     inventory               = new ResultContainer();

    public ArchitectsCutterContainer(int windowIdIn, Inventory playerInventoryIn) {
        this(windowIdIn, playerInventoryIn, ContainerLevelAccess.NULL);
    }

    public ArchitectsCutterContainer(int windowIdIn, Inventory playerInventoryIn, final ContainerLevelAccess worldPosCallableIn) {
        super(ModContainerTypes.ARCHITECTS_CUTTER.get(), windowIdIn);
        this.worldPosCallable = worldPosCallableIn;
        this.world = playerInventoryIn.player.level;
        for (int i = 0; i < MateriallyTexturedBlockManager.getInstance().getMaxTexturableComponentCount(); i++)
        {
            int rowIndex = i / 2;
            int columnIndex = i % 2;

            int x = 10 + columnIndex * 20;
            int y = 16 + rowIndex * 18;
            this.inputInventorySlots.add(
              this.addSlot(new Slot(this.inputInventory, i, x, y) {
                  @Override
                  public boolean mayPlace(@NotNull final ItemStack stack)
                  {
                      return MateriallyTexturedBlockManager.getInstance().doesItemStackContainsMaterialForSlot(this.getSlotIndex(), stack);
                  }
              })
            );
        }
        this.outputInventorySlot = this.addSlot(new Slot(this.inventory, 1, 143, 33) {
            public boolean mayPlace(@NotNull ItemStack stack) {
                return false;
            }

            public void onTake(@NotNull Player thePlayer, @NotNull ItemStack stack) {
                stack.onCraftedBy(thePlayer.level, thePlayer, stack.getCount());
                ArchitectsCutterContainer.this.inventory.awardUsedRecipes(thePlayer);
                boolean anyEmpty = false;
                List<Slot> inventorySlots = ArchitectsCutterContainer.this.inputInventorySlots;

                final boolean craftingMateriallyTexturedBlock = stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof IMateriallyTexturedBlock;
                final List<IMateriallyTexturedBlockComponent>
                  components = craftingMateriallyTexturedBlock ? Lists.newArrayList(((IMateriallyTexturedBlock) ((BlockItem) stack.getItem()).getBlock()).getComponents()) : Collections.emptyList();
                final int componentSize = craftingMateriallyTexturedBlock ? components.size() : 1;
                for (int i = 0; i < inventorySlots.size(); i++)
                {
                    final Slot inputInventorySlot = inventorySlots.get(i);

                    final boolean isRequiredBlock = (i < componentSize);

                    if (!inputInventorySlot.getItem().isEmpty() && isRequiredBlock)
                    {
                        if (!thePlayer.isCreative() && inputInventorySlot.remove(1).isEmpty())
                        {
                            anyEmpty = true;
                        }
                    }
                }
                if (!anyEmpty) {
                    ArchitectsCutterContainer.this.updateRecipeResultSlot();
                }

                worldPosCallableIn.execute((world, pos) -> {
                    long gameTime = world.getGameTime();
                    if (ArchitectsCutterContainer.this.lastOnTake != gameTime) {
                        world.playSound(null, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
                        ArchitectsCutterContainer.this.lastOnTake = gameTime;
                    }

                });
            }
        });

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventoryIn, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventoryIn, k, 8 + k * 18, 142));
        }

        this.addDataSlot(this.selectedRecipe);
    }

    /**
     * Returns the index of the selected recipe.
     */
    @OnlyIn(Dist.CLIENT)
    public int getSelectedRecipe() {
        return this.selectedRecipe.get();
    }

    @OnlyIn(Dist.CLIENT)
    public List<ArchitectsCutterRecipe> getRecipeList() {
        return this.recipes;
    }

    @OnlyIn(Dist.CLIENT)
    public int getRecipeListSize() {
        return this.recipes.size();
    }

    @OnlyIn(Dist.CLIENT)
    public boolean hasItemsInInputSlots() {
        return this.inputInventorySlots.stream().anyMatch(Slot::hasItem) && !this.recipes.isEmpty();
    }

    /**
     * Determines whether supplied player can use this container
     */
    public boolean stillValid(@NotNull Player playerIn) {
        return stillValid(this.worldPosCallable, playerIn, IModBlocks.getInstance().getArchitectsCutter());
    }

    /**
     * Handles the given Button-click on the server, currently only used by enchanting. Name is for legacy.
     */
    public boolean clickMenuButton(@NotNull Player playerIn, int id) {
        if (this.isValidRecipeIndex(id)) {
            this.selectedRecipe.set(id);
            this.updateRecipeResultSlot();
        }

        return true;
    }

    private boolean isValidRecipeIndex(int p_241818_1_) {
        return p_241818_1_ >= 0 && p_241818_1_ < this.recipes.size();
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void slotsChanged(@NotNull Container inventoryIn) {
        boolean anyChanged = false;
        List<Slot> slots = this.inputInventorySlots;
        for (int i = 0; i < slots.size(); i++)
        {
            final Slot inputInventorySlot = slots.get(i);
            final ItemStack inputStack = this.inputItemStacks.get(i);

            if (inputInventorySlot.getItem().getItem() != inputStack.getItem())
                anyChanged = true;
        }

        if (anyChanged) {
            final List<ItemStack> stacks = new ArrayList<>();
            for (int i = 0; i < slots.size(); i++)
            {
                final ItemStack itemStack = this.inputInventorySlots.get(i).getItem();
                stacks.add(itemStack);
                this.inputItemStacks.set(i, itemStack.copy());
            }
            this.updateAvailableRecipes(inventoryIn, stacks);
        }

    }

    private void updateAvailableRecipes(Container inventoryIn, List<ItemStack> stacks) {
        this.recipes.clear();
        this.selectedRecipe.set(-1);
        this.outputInventorySlot.set(ItemStack.EMPTY);
        if (!stacks.stream().allMatch(ItemStack::isEmpty)) {
            this.recipes = this.world.getRecipeManager().getRecipesFor(ModRecipeTypes.ARCHITECTS_CUTTER.get(), inventoryIn, this.world);
            this.recipes.sort(Comparator.comparing(ArchitectsCutterRecipe::getBlockName).thenComparing(ArchitectsCutterRecipe::getId));
        }

    }

    private void updateRecipeResultSlot() {
        if (!this.recipes.isEmpty() && this.isValidRecipeIndex(this.selectedRecipe.get())) {
            Recipe<Container> recipe = this.recipes.get(this.selectedRecipe.get());
            this.inventory.setRecipeUsed(recipe);
            this.outputInventorySlot.set(recipe.assemble(this.inputInventory));
        } else {
            this.outputInventorySlot.set(ItemStack.EMPTY);
        }

        this.broadcastChanges();
    }

    @NotNull
    public MenuType<?> getType() {
        return ModContainerTypes.ARCHITECTS_CUTTER.get();
    }

    @OnlyIn(Dist.CLIENT)
    public void setInventoryUpdateListener(Runnable listenerIn) {
        this.inventoryUpdateListener = listenerIn;
    }

    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is
     * null for the initial slot that was double-clicked.
     */
    public boolean canTakeItemForPickAll(@NotNull ItemStack stack, Slot slotIn) {
        return slotIn.container != this.inventory && super.canTakeItemForPickAll(stack, slotIn);
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    @NotNull
    public ItemStack quickMoveStack(@NotNull Player player, int clickedSlot) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(clickedSlot);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (clickedSlot == 3) {
                if (!this.moveItemStackTo(itemstack1, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
                slot.onTake(player, itemstack);
                return itemstack;
            } else if (clickedSlot > 3) {
                if (!this.moveItemStackTo(itemstack1, 0, 2, false)) {
                    return ItemStack.EMPTY;
                }
               else if (clickedSlot < 31) {
                    if (!this.moveItemStackTo(itemstack1, 31, 40, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (clickedSlot < 40 && !this.moveItemStackTo(itemstack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 4, 40, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    /**
     * Called when the container is closed.
     */
    public void removed(@NotNull Player playerIn) {
        super.removed(playerIn);
        this.inventory.removeItemNoUpdate(1);
        this.worldPosCallable.execute((p_217079_2_, p_217079_3_) -> this.clearContainer(playerIn, this.inputInventory));
    }
}
