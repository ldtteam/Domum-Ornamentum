package com.ldtteam.domumornamentum.block.decorative;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ldtteam.domumornamentum.block.AbstractBlockPane;
import com.ldtteam.domumornamentum.block.ICachedItemGroupBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.components.SimpleRetexturableComponent;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.entity.block.MateriallyTexturedBlockEntity;
import com.ldtteam.domumornamentum.entity.block.ModBlockEntityTypes;
import com.ldtteam.domumornamentum.item.decoration.PaperwallBlockItem;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.BlockUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The paperwall block class defining the paperwall.
 */
public class PaperWallBlock extends AbstractBlockPane<PaperWallBlock> implements IMateriallyTexturedBlock, ICachedItemGroupBlock, EntityBlock
{
    public static final List<IMateriallyTexturedBlockComponent> COMPONENTS = ImmutableList.<IMateriallyTexturedBlockComponent>builder()
                                                                               .add(new SimpleRetexturableComponent(new ResourceLocation("block/oak_planks"), ModTags.PAPERWALL_FRAME, Blocks.OAK_PLANKS))
                                                                               .add(new SimpleRetexturableComponent(new ResourceLocation("block/dark_oak_planks"), ModTags.PAPERWALL_CENTER, Blocks.DARK_OAK_PLANKS))
                                                                               .build();

    private final List<ItemStack> fillItemGroupCache = Lists.newArrayList();

    /**
     * This block's name.
     */
    public static final String                      BLOCK_NAME     = "blockpaperwall";

    /**
     * The hardness this block has.
     */
    private static final float                      BLOCK_HARDNESS = 3F;

    /**
     * The resistance this block has.
     */
    private static final float                      RESISTANCE     = 1F;

    public PaperWallBlock()
    {
        super(Properties.of(Material.GLASS).strength(BLOCK_HARDNESS, RESISTANCE));
        setRegistryName(BLOCK_NAME);
    }

    /**
     * Registry block at game registry.
     *
     * @param registry the registry to use.
     */
    @Override
    public void registerItemBlock(final IForgeRegistry<Item> registry, final Item.Properties properties)
    {
        registry.register((new PaperwallBlockItem(this, properties)).setRegistryName(Objects.requireNonNull(this.getRegistryName())));
    }

    @Override
    public BlockState rotate(final BlockState state, final LevelAccessor world, final BlockPos pos, final Rotation direction)
    {
        return switch (direction)
                 {
                     case CLOCKWISE_180 -> state.setValue(NORTH, state.getValue(SOUTH))
                                             .setValue(EAST, state.getValue(WEST)).setValue(SOUTH, state.getValue(NORTH))
                                             .setValue(WEST, state.getValue(EAST));
                     case COUNTERCLOCKWISE_90 -> state.setValue(NORTH, state.getValue(EAST))
                                                   .setValue(EAST, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(WEST))
                                                   .setValue(WEST, state.getValue(NORTH));
                     case CLOCKWISE_90 -> state.setValue(NORTH, state.getValue(WEST))
                                            .setValue(EAST, state.getValue(NORTH)).setValue(SOUTH, state.getValue(EAST))
                                            .setValue(WEST, state.getValue(SOUTH));
                     default -> state;
                 };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }

    @Override
    public List<IMateriallyTexturedBlockComponent> getComponents()
    {
        return COMPONENTS;
    }


    @Override
    public void fillItemCategory(final @NotNull CreativeModeTab group, final @NotNull NonNullList<ItemStack> items)
    {
        if (!fillItemGroupCache.isEmpty()) {
            items.addAll(fillItemGroupCache);
            return;
        }

        IMateriallyTexturedBlockComponent frameComponent = getComponents().get(0);
        IMateriallyTexturedBlockComponent centerComponent = getComponents().get(1);

        final Tag<Block> frameCandidates = frameComponent.getValidSkins();
        final Tag<Block> centerCandidates = centerComponent.getValidSkins();

        try {
            frameCandidates.getValues().forEach(cover -> {
                centerCandidates.getValues().forEach(support ->{
                    final Map<ResourceLocation, Block> textureData = Maps.newHashMap();

                    textureData.put(frameComponent.getId(), cover);
                    textureData.put(centerComponent.getId(), support);

                    final MaterialTextureData materialTextureData = new MaterialTextureData(textureData);

                    final CompoundTag textureNbt = materialTextureData.serializeNBT();

                    final ItemStack result = new ItemStack(this);
                    result.getOrCreateTag().put("textureData", textureNbt);

                    fillItemGroupCache.add(result);
                });
            });
        } catch (IllegalStateException exception)
        {
            //Ignored. Thrown during start up.
        }

        items.addAll(fillItemGroupCache);
    }

    @Override
    public void setPlacedBy(
      final @NotNull Level worldIn, final @NotNull BlockPos pos, final @NotNull BlockState state, @Nullable final LivingEntity placer, final @NotNull ItemStack stack)
    {
        super.setPlacedBy(worldIn, pos, state, placer, stack);

        final CompoundTag textureData = stack.getOrCreateTagElement("textureData");
        final BlockEntity tileEntity = worldIn.getBlockEntity(pos);

        if (tileEntity instanceof MateriallyTexturedBlockEntity)
            ((MateriallyTexturedBlockEntity) tileEntity).updateTextureDataWith(MaterialTextureData.deserializeFromNBT(textureData));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(final @NotNull BlockPos blockPos, final @NotNull BlockState blockState)
    {
        return new MateriallyTexturedBlockEntity(ModBlockEntityTypes.MATERIALLY_TEXTURED, blockPos, blockState);
    }

    @Override
    public void resetCache()
    {
        fillItemGroupCache.clear();
    }

    @Override
    public @NotNull List<ItemStack> getDrops(final @NotNull BlockState state, final @NotNull LootContext.Builder builder)
    {
        return BlockUtils.getMaterializedItemStack(builder);
    }

    @Override
    public ItemStack getPickBlock(
      final BlockState state, final HitResult target, final BlockGetter world, final BlockPos pos, final Player player)
    {
        return BlockUtils.getMaterializedItemStack(player, world, pos);
    }
}
