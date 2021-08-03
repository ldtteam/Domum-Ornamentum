package com.ldtteam.domumornamentum.block.decorative;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ldtteam.domumornamentum.block.AbstractBlockStairs;
import com.ldtteam.domumornamentum.block.ICachedItemGroupBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.components.SimpleRetexturableComponent;
import com.ldtteam.domumornamentum.block.types.ShingleShapeType;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.entity.block.MateriallyTexturedBlockEntity;
import com.ldtteam.domumornamentum.entity.block.ModBlockEntityTypes;
import com.ldtteam.domumornamentum.item.decoration.ShingleBlockItem;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.BlockUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Class defining the general shingle.
 */
public class ShingleBlock extends AbstractBlockStairs<ShingleBlock> implements IMateriallyTexturedBlock, ICachedItemGroupBlock, EntityBlock
{
    public static final List<IMateriallyTexturedBlockComponent> COMPONENTS = ImmutableList.<IMateriallyTexturedBlockComponent>builder()
                                                                               .add(new SimpleRetexturableComponent(new ResourceLocation("block/clay"), ModTags.SHINGLES_ROOF, Blocks.CLAY))
                                                                               .add(new SimpleRetexturableComponent(new ResourceLocation("block/oak_planks"), ModTags.SHINGLES_SUPPORT, Blocks.OAK_PLANKS))
                                                                               .build();

    /**
     * The hardness this block has.
     */
    private static final float BLOCK_HARDNESS = 3F;

    /**
     * The resistance this block has.
     */
    private static final float RESISTANCE = 1F;

    private final List<ItemStack> fillItemGroupCache = Lists.newArrayList();

    public ShingleBlock()
    {
        super(Blocks.OAK_PLANKS::defaultBlockState, Properties.of(Material.WOOD).strength(BLOCK_HARDNESS, RESISTANCE).noOcclusion());
        setRegistryName(Constants.MOD_ID, "shingle");
    }

    @Override
    public void registerItemBlock(final IForgeRegistry<Item> registry, final Item.Properties properties)
    {
        registry.register((new ShingleBlockItem(this, properties)).setRegistryName(Objects.requireNonNull(this.getRegistryName())));
    }

    /**
     * Get the model type from a StairsShape object
     *
     * @param shape the StairsShape object
     * @return the model type for provided StairsShape
     */
    public static ShingleShapeType getTypeFromShape(final StairsShape shape)
    {
        switch (shape)
        {
            case INNER_LEFT:
            case INNER_RIGHT:
                return ShingleShapeType.CONCAVE;
            case OUTER_LEFT:
            case OUTER_RIGHT:
                return ShingleShapeType.CONVEX;
            default:
                return ShingleShapeType.STRAIGHT;
        }
    }

    @Override
    public List<IMateriallyTexturedBlockComponent> getComponents()
    {
        return COMPONENTS;
    }

    @Override
    public void fillItemCategory(final CreativeModeTab group, final NonNullList<ItemStack> items)
    {
        if (!fillItemGroupCache.isEmpty()) {
            items.addAll(fillItemGroupCache);
            return;
        }

        IMateriallyTexturedBlockComponent coverComponent = getComponents().get(0);
        IMateriallyTexturedBlockComponent supportComponent = getComponents().get(1);

        final Tag<Block> coverCandidates = coverComponent.getValidSkins();
        final Tag<Block> supportCandidates = supportComponent.getValidSkins();

        try {
            coverCandidates.getValues().forEach(cover -> {
                supportCandidates.getValues().forEach(support ->{
                    final Map<ResourceLocation, Block> textureData = Maps.newHashMap();

                    textureData.put(coverComponent.getId(), cover);
                    textureData.put(supportComponent.getId(), support);

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
      final Level worldIn, final BlockPos pos, final BlockState state, @Nullable final LivingEntity placer, final ItemStack stack)
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
