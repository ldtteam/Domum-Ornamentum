package com.ldtteam.domumornamentum.network.messages;

import com.ldtteam.domumornamentum.client.screens.ArchitectsCutterScreen;
import com.ldtteam.domumornamentum.container.ArchitectsCutterContainer;
import com.ldtteam.domumornamentum.network.IServerboundDistributor;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import org.jetbrains.annotations.NotNull;

/**
 * Sets the item in the {@link ArchitectsCutterScreen} for creative mode.
 */
public record CreativeSetArchitectCutterSlotMessage(int slot, ItemStack stack) implements IServerboundDistributor
{
    public static final ResourceLocation ID = new ResourceLocation(Constants.MOD_ID, "creative_set_archicutter_slot");

    /**
     * Construct from network.
     * @param buf the buffer.
     */
    public CreativeSetArchitectCutterSlotMessage(@NotNull final FriendlyByteBuf buf)
    {
        this(buf.readVarInt(), buf.readItem());
    }

    @Override
    public void write(@NotNull final FriendlyByteBuf buf)
    {
        buf.writeVarInt(this.slot);
        buf.writeItem(this.stack);
    }

    @Override
    public ResourceLocation id()
    {
        return ID;
    }

    public void onExecute(@NotNull final PlayPayloadContext ctxIn)
    {
        final Player player = ctxIn.player().orElse(null);

        if (player != null && player.isCreative() && player.containerMenu instanceof ArchitectsCutterContainer menu)
        {
            if (this.slot >= 0 && this.slot < menu.slots.size())
            {
                final Slot menuSlot = menu.slots.get(this.slot);
                if (menuSlot.isActive() && menuSlot.allowModification(player) && menuSlot.mayPlace(this.stack))
                {
                    menuSlot.setByPlayer(this.stack);
                }
            }
        }
    }
}
