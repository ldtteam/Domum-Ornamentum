package com.ldtteam.domumornamentum.block.decorative;

import com.google.common.collect.Lists;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.entity.block.MateriallyTexturedBlockEntity;
import com.ldtteam.domumornamentum.util.Suppression;
import com.ldtteam.domumornamentum.block.interfaces.IDOBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.registries.IForgeRegistry;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SlabBlock<B extends SlabBlock<B>> extends net.minecraft.world.level.block.SlabBlock implements IDOBlock<B>
{
    /**
     * Constructor of abstract class.
     * @param properties the input properties.
     */
    public SlabBlock(final Properties properties, final String registryName)
    {
        super(properties);
        this.setRegistryName(registryName);
    }

    @Override
    @SuppressWarnings(Suppression.UNCHECKED)
    public B registerBlock(final IForgeRegistry<Block> registry)
    {
        registry.register(this);
        return (B) this;
    }

    @Override
    public void registerItemBlock(final IForgeRegistry<Item> registry, final Item.Properties properties)
    {
        registry.register((new BlockItem(this, properties)).setRegistryName(this.getRegistryName()));
    }


    @Override
    public @NotNull List<ItemStack> getDrops(final @NotNull BlockState state, final @NotNull LootContext.Builder builder)
    {
        final BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (!(blockEntity instanceof final MateriallyTexturedBlockEntity texturedBlockEntity))
            return Lists.newArrayList();

        final MaterialTextureData materialTextureData = texturedBlockEntity.getTextureData();

        final CompoundTag textureNbt = materialTextureData.serializeNBT();

        final ItemStack result = new ItemStack(this);
        result.getOrCreateTag().put("textureData", textureNbt);

        return Lists.newArrayList(result);
    }
}
