package com.ldtteam.domumornamentum.network.messages;

import com.ldtteam.domumornamentum.client.screens.ArchitectsCutterScreen;
import com.ldtteam.domumornamentum.container.ArchitectsCutterContainer;
import com.ldtteam.domumornamentum.network.IServerboundDistributor;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

/**
 * Sets the item in the {@link ArchitectsCutterScreen} for creative mode.
 */
public record CreativeSetArchitectCutterSlotMessage(int slot, ItemStack stack) implements IServerboundDistributor
{
    public static final Type<CreativeSetArchitectCutterSlotMessage> ID = new Type<>(Constants.resLocDO("creative_set_archicutter_slot"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CreativeSetArchitectCutterSlotMessage> CODEC = StreamCodec.composite(ByteBufCodecs.VAR_INT,
        CreativeSetArchitectCutterSlotMessage::slot,
        ItemStack.STREAM_CODEC,
        CreativeSetArchitectCutterSlotMessage::stack,
        CreativeSetArchitectCutterSlotMessage::new);

    @Override
    public Type<CreativeSetArchitectCutterSlotMessage> type()
    {
        return ID;
    }

    public void onExecute(@NotNull final IPayloadContext ctxIn)
    {
        final Player player = ctxIn.player();
        ctxIn.enqueueWork(() -> onExecuteMainThread(player));
    }

    private void onExecuteMainThread(final Player player)
    {
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
