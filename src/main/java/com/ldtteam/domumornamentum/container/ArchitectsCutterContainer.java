package com.ldtteam.domumornamentum.container;

import com.google.common.collect.Lists;
import com.ldtteam.domumornamentum.block.MateriallyTexturedBlockManager;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.recipe.ModRecipeTypes;
import com.ldtteam.domumornamentum.recipe.architectscutter.ArchitectsCutterRecipe;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ArchitectsCutterContainer extends Container
{
    private final IWorldPosCallable        worldPosCallable;
    private final IntReferenceHolder       selectedRecipe          = IntReferenceHolder.single();
    private final World                        world;
    private       List<ArchitectsCutterRecipe> recipes = Lists.newArrayList();

    private final NonNullList<ItemStack> inputItemStacks = NonNullList.withSize(MateriallyTexturedBlockManager.getInstance().getMaxTexturableComponentCount(), ItemStack.EMPTY);
    private       long                   lastOnTake;
    final List<Slot>                        inputInventorySlots = Lists.newArrayList();
    final         Slot                     outputInventorySlot;
    private       Runnable                 inventoryUpdateListener = () -> {};
    public final  IInventory               inputInventory          = new Inventory(MateriallyTexturedBlockManager.getInstance().getMaxTexturableComponentCount()) {
        public void markDirty() {
            super.markDirty();
            ArchitectsCutterContainer.this.onCraftMatrixChanged(this);
            ArchitectsCutterContainer.this.inventoryUpdateListener.run();
        }
    };
    private final CraftResultInventory     inventory               = new CraftResultInventory();

    public ArchitectsCutterContainer(int windowIdIn, PlayerInventory playerInventoryIn) {
        this(windowIdIn, playerInventoryIn, IWorldPosCallable.DUMMY);
    }

    public ArchitectsCutterContainer(int windowIdIn, PlayerInventory playerInventoryIn, final IWorldPosCallable worldPosCallableIn) {
        super(ModContainerTypes.ARCHITECTS_CUTTER, windowIdIn);
        this.worldPosCallable = worldPosCallableIn;
        this.world = playerInventoryIn.player.world;
        for (int i = 0; i < MateriallyTexturedBlockManager.getInstance().getMaxTexturableComponentCount(); i++)
        {
            int rowIndex = i / 2;
            int columnIndex = i % 2;

            int x = 10 + columnIndex * 20;
            int y = 16 + rowIndex * 18;
            this.inputInventorySlots.add(
              this.addSlot(new Slot(this.inputInventory, i, x, y) {
                  @Override
                  public boolean isItemValid(@NotNull final ItemStack stack)
                  {
                      return MateriallyTexturedBlockManager.getInstance().doesItemStackContainsMaterialForSlot(this.getSlotIndex(), stack);
                  }
              })
            );
        }
        this.outputInventorySlot = this.addSlot(new Slot(this.inventory, 1, 143, 33) {
            public boolean isItemValid(@NotNull ItemStack stack) {
                return false;
            }

            @NotNull
            public ItemStack onTake(@NotNull PlayerEntity thePlayer, @NotNull ItemStack stack) {
                stack.onCrafting(thePlayer.world, thePlayer, stack.getCount());
                ArchitectsCutterContainer.this.inventory.onCrafting(thePlayer);
                boolean anyEmpty = false;
                for (final Slot inputInventorySlot : ArchitectsCutterContainer.this.inputInventorySlots)
                {
                    if (inputInventorySlot.decrStackSize(1).isEmpty())
                        anyEmpty = true;
                }
                if (!anyEmpty) {
                    ArchitectsCutterContainer.this.updateRecipeResultSlot();
                }

                worldPosCallableIn.consume((world, pos) -> {
                    long gameTime = world.getGameTime();
                    if (ArchitectsCutterContainer.this.lastOnTake != gameTime) {
                        world.playSound(null, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        ArchitectsCutterContainer.this.lastOnTake = gameTime;
                    }

                });
                return super.onTake(thePlayer, stack);
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

        this.trackInt(this.selectedRecipe);
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
        return this.inputInventorySlots.stream().allMatch(Slot::getHasStack) && !this.recipes.isEmpty();
    }

    /**
     * Determines whether supplied player can use this container
     */
    public boolean canInteractWith(@NotNull PlayerEntity playerIn) {
        return isWithinUsableDistance(this.worldPosCallable, playerIn, ModBlocks.getArchitectsCutterBlock());
    }

    /**
     * Handles the given Button-click on the server, currently only used by enchanting. Name is for legacy.
     */
    public boolean enchantItem(@NotNull PlayerEntity playerIn, int id) {
        if (this.func_241818_d_(id)) {
            this.selectedRecipe.set(id);
            this.updateRecipeResultSlot();
        }

        return true;
    }

    private boolean func_241818_d_(int p_241818_1_) {
        return p_241818_1_ >= 0 && p_241818_1_ < this.recipes.size();
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void onCraftMatrixChanged(@NotNull IInventory inventoryIn) {
        boolean anyChanged = false;
        List<Slot> slots = this.inputInventorySlots;
        for (int i = 0; i < slots.size(); i++)
        {
            final Slot inputInventorySlot = slots.get(i);
            final ItemStack inputStack = this.inputItemStacks.get(i);

            if (inputInventorySlot.getStack().getItem() != inputStack.getItem())
                anyChanged = true;
        }

        if (anyChanged) {
            final List<ItemStack> stacks = new ArrayList<>();
            for (int i = 0; i < slots.size(); i++)
            {
                final ItemStack itemStack = this.inputInventorySlots.get(i).getStack();
                stacks.add(itemStack);
                this.inputItemStacks.set(i, itemStack.copy());
            }
            this.updateAvailableRecipes(inventoryIn, stacks);
        }

    }

    private void updateAvailableRecipes(IInventory inventoryIn, List<ItemStack> stacks) {
        this.recipes.clear();
        this.selectedRecipe.set(-1);
        this.outputInventorySlot.putStack(ItemStack.EMPTY);
        if (!stacks.stream().allMatch(ItemStack::isEmpty)) {
            this.recipes = this.world.getRecipeManager().getRecipes(ModRecipeTypes.ARCHITECTS_CUTTER, inventoryIn, this.world);
        }

    }

    private void updateRecipeResultSlot() {
        if (!this.recipes.isEmpty() && this.func_241818_d_(this.selectedRecipe.get())) {
            IRecipe<IInventory> recipe = this.recipes.get(this.selectedRecipe.get());
            this.inventory.setRecipeUsed(recipe);
            this.outputInventorySlot.putStack(recipe.getCraftingResult(this.inputInventory));
        } else {
            this.outputInventorySlot.putStack(ItemStack.EMPTY);
        }

        this.detectAndSendChanges();
    }

    @NotNull
    public ContainerType<?> getType() {
        return ModContainerTypes.ARCHITECTS_CUTTER;
    }

    @OnlyIn(Dist.CLIENT)
    public void setInventoryUpdateListener(Runnable listenerIn) {
        this.inventoryUpdateListener = listenerIn;
    }

    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is
     * null for the initial slot that was double-clicked.
     */
    public boolean canMergeSlot(@NotNull ItemStack stack, Slot slotIn) {
        return slotIn.inventory != this.inventory && super.canMergeSlot(stack, slotIn);
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    @NotNull
    public ItemStack transferStackInSlot(@NotNull PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            Item item = itemstack1.getItem();
            itemstack = itemstack1.copy();
            if (index == 1) {
                item.onCreated(itemstack1, playerIn.world, playerIn);
                if (!this.mergeItemStack(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index == 0) {
                if (!this.mergeItemStack(itemstack1, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.world.getRecipeManager().getRecipe(IRecipeType.STONECUTTING, new Inventory(itemstack1), this.world).isPresent()) {
                if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 29) {
                if (!this.mergeItemStack(itemstack1, 29, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 38 && !this.mergeItemStack(itemstack1, 2, 29, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            }

            slot.onSlotChanged();
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
            this.detectAndSendChanges();
        }

        return itemstack;
    }

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(@NotNull PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        this.inventory.removeStackFromSlot(1);
        this.worldPosCallable.consume((p_217079_2_, p_217079_3_) -> this.clearContainer(playerIn, playerIn.world, this.inputInventory));
    }
}
