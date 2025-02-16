package com.ldtteam.domumornamentum.container;

import com.google.common.collect.Lists;
import com.ldtteam.domumornamentum.block.*;
import com.ldtteam.domumornamentum.recipe.ModRecipeTypes;
import com.ldtteam.domumornamentum.recipe.architectscutter.ArchitectsCutterRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

import static com.ldtteam.domumornamentum.util.GuiConstants.*;

public class ArchitectsCutterContainer extends AbstractContainerMenu
{
    /**
     * The world access.
     */
    private final ContainerLevelAccess worldPosCallable;

    /**
     * The world we are in.
     */
    private final Level world;

    /**
     * All possible recipes.
     */
    private List<ArchitectsCutterRecipe> recipes = Lists.newArrayList();

    /**
     * Input type stacks.
     */
    private final NonNullList<ItemStack> inputItemStacks = NonNullList.withSize(MateriallyTexturedBlockManager.getInstance().getMaxTexturableComponentCount(), ItemStack.EMPTY);

    /**
     * Last usage time.
     */
    private long lastOnTake;

    /**
     * Input slots.
     */
    final List<Slot> inputInventorySlots = Lists.newArrayList();

    /**
     * Output slot.
     */
    public final Slot outputInventorySlot;

    /**
     * Update listener.
     */
    private Runnable inventoryUpdateListener = () -> {};

    /**
     * Input inventory.
     */
    public final Container inputInventory = new SimpleContainer(MateriallyTexturedBlockManager.getInstance().getMaxTexturableComponentCount())
    {
        public void setChanged()
        {
            super.setChanged();
            ArchitectsCutterContainer.this.slotsChanged(this);
            ArchitectsCutterContainer.this.inventoryUpdateListener.run();
        }
    };

    /**
     * The total inventory.
     */
    private final ResultContainer inventory = new ResultContainer();

    /**
     * The currently selected group.
     */
    private ResourceLocation currentGroup;

    /**
     * The current selected variant.
     */
    private ItemStack currentVariant;

    public ArchitectsCutterContainer(int windowIdIn, Inventory playerInventoryIn) {
        this(windowIdIn, playerInventoryIn, ContainerLevelAccess.NULL);
    }

    public ArchitectsCutterContainer(int windowIdIn, Inventory playerInventoryIn, final ContainerLevelAccess worldPosCallableIn) {
        super(ModContainerTypes.ARCHITECTS_CUTTER.get(), windowIdIn);
        this.worldPosCallable = worldPosCallableIn;
        this.world = playerInventoryIn.player.level();
        for (int i = 0; i < MateriallyTexturedBlockManager.getInstance().getMaxTexturableComponentCount(); i++)
        {
            int x = CUTTER_INPUT_X;
            int y = CUTTER_INPUT_Y + i * CUTTER_INPUT_SPACING;
            this.inputInventorySlots.add(
              this.addSlot(new Slot(this.inputInventory, i, x, y) {
                  @Override
                  public boolean mayPlace(@NotNull final ItemStack stack)
                  {
                      if (currentVariant == null)
                      {
                          return MateriallyTexturedBlockManager.getInstance().doesItemStackContainsMaterialForSlot(this.getSlotIndex(), stack);
                      }
                      else
                      {
                          return MateriallyTexturedBlockManager.getInstance().doesItemStackContainsMaterialForSlot(this.getSlotIndex(), stack, currentVariant);
                      }
                  }
              })
            );
        }

        this.outputInventorySlot = this.addSlot(new Slot(this.inventory, 1, CUTTER_OUTPUT_X, CUTTER_OUTPUT_Y) {
            public boolean mayPlace(@NotNull ItemStack stack) {
                return false;
            }

            public void onTake(@NotNull Player thePlayer, @NotNull ItemStack stack) {
                stack.onCraftedBy(thePlayer.level(), thePlayer, stack.getCount());
                boolean anyEmpty = false;
                List<Slot> inventorySlots = ArchitectsCutterContainer.this.inputInventorySlots;
                ArchitectsCutterContainer.this.inventory.awardUsedRecipes(thePlayer, inventorySlots.stream().map(slot -> slot.getItem()).collect(Collectors.toList()));

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
                this.addSlot(new Slot(playerInventoryIn, j + i * 9 + 9, 40 + j * 18, 120 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventoryIn, k, 40 + k * 18, 178));
        }
    }

    /**
     * Determines whether supplied player can use this container
     */
    @Override
    public boolean stillValid(@NotNull Player playerIn) {
        return stillValid(this.worldPosCallable, playerIn, IModBlocks.getInstance().getArchitectsCutter());
    }

    /**
     * Handles the given Button-click on the server.
     */
    @Override
    public boolean clickMenuButton(@NotNull Player playerIn, int id) {
        if (id < ModBlocks.getInstance().getOrComputeItemGroups().size())
        {
            this.selectGroup(new ArrayList<>(ModBlocks.getInstance().getOrComputeItemGroups().keySet()).get(id));
            return true;
        }
        else if (this.getCurrentGroup() == null)
        {
            this.selectGroup(new ArrayList<>(ModBlocks.getInstance().getOrComputeItemGroups().keySet()).get(ModBlocks.getInstance().getOrComputeItemGroups().size() - 1));
        }

        int dif = id - ModBlocks.getInstance().getOrComputeItemGroups().size();
        if (dif < ModBlocks.getInstance().getOrComputeItemGroups().get(this.getCurrentGroup()).size())
        {
            this.selectVariant(ModBlocks.getInstance().getOrComputeItemGroups().get(getCurrentGroup()).get(dif));
            this.updateRecipeResultSlot();
            return true;
        }

        return true;
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    @Override
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
        this.outputInventorySlot.set(ItemStack.EMPTY);
        if (!stacks.stream().allMatch(ItemStack::isEmpty)) {
            this.recipes = this.world.getRecipeManager().getRecipesFor(ModRecipeTypes.ARCHITECTS_CUTTER.get(), inventoryIn, this.world);
            this.recipes.sort(Comparator.comparing(ArchitectsCutterRecipe::getBlockName).thenComparing(ArchitectsCutterRecipe::getId));
        }
        updateRecipeResultSlot();
    }

    private void updateRecipeResultSlot() {
        if (!this.recipes.isEmpty() && this.currentVariant != null && this.currentVariant.getItem() instanceof BlockItem blockItem) {
            for (final ArchitectsCutterRecipe recipe : recipes)
            {
                final ItemStack resultItem = recipe.getResultItem(this.world.registryAccess());
                if (resultItem.getItem() == currentVariant.getItem())
                {
                    if (resultItem.hasTag())
                    {
                        for (final String key: resultItem.getTag().getAllKeys())
                        {
                            if (currentVariant.hasTag() && currentVariant.getTag().contains(key) && resultItem.getTag().get(key).equals(currentVariant.getTag().get(key)))
                            {
                                this.inventory.setRecipeUsed(recipe);
                                this.outputInventorySlot.set(recipe.assemble(this.inputInventory, this.world.registryAccess()));
                                break;
                            }
                        }

                        continue;
                    }
                    this.inventory.setRecipeUsed(recipe);
                    this.outputInventorySlot.set(recipe.assemble(this.inputInventory, this.world.registryAccess()));
                    break;
                }
            }

        } else {
            this.outputInventorySlot.set(ItemStack.EMPTY);
        }

        this.broadcastChanges();
    }

    @NotNull
    public MenuType<?> getType() {
        return ModContainerTypes.ARCHITECTS_CUTTER.get();
    }

    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is
     * null for the initial slot that was double-clicked.
     */
    @Override
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
            if (clickedSlot == 2) {
                if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
                slot.onTake(player, itemstack);
                return itemstack;
            } else if (clickedSlot > 2) {
                if (!this.moveItemStackTo(itemstack1, 0, 2, false)) {
                    return ItemStack.EMPTY;
                }
               else if (clickedSlot < 30) {
                    if (!this.moveItemStackTo(itemstack1, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (clickedSlot < 39 && !this.moveItemStackTo(itemstack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 3, 39, false)) {
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
    @Override
    public void removed(@NotNull Player playerIn) {
        super.removed(playerIn);
        this.inventory.removeItemNoUpdate(1);
        this.worldPosCallable.execute((p_217079_2_, p_217079_3_) -> this.clearContainer(playerIn, this.inputInventory));
    }

    /**
     * Get the current selected group.
     * @return the group.
     */
    public ResourceLocation getCurrentGroup()
    {
        return currentGroup;
    }

    /**
     * Get the current selected variant.
     * @return the variant.
     */
    public ItemStack getCurrentVariant()
    {
        return currentVariant;
    }

    /**
     * Variant setter.
     * @param itemStack the variant stack.
     */
    public void selectVariant(final ItemStack itemStack)
    {
        this.currentVariant = itemStack;
    }

    /**
     * Group setter.
     * @param location the group to select.
     */
    public void selectGroup(final ResourceLocation location)
    {
        this.currentGroup = location;
    }
}
