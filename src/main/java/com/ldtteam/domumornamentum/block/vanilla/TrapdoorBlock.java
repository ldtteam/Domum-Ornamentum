package com.ldtteam.domumornamentum.block.vanilla;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ldtteam.domumornamentum.block.AbstractBlockTrapdoor;
import com.ldtteam.domumornamentum.block.ICachedItemGroupBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.components.SimpleRetexturableComponent;
import com.ldtteam.domumornamentum.block.types.TrapdoorType;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.entity.block.MateriallyTexturedBlockEntity;
import com.ldtteam.domumornamentum.entity.block.ModBlockEntityTypes;
import com.ldtteam.domumornamentum.item.vanilla.TrapdoorBlockItem;
import com.ldtteam.domumornamentum.tag.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static net.minecraft.world.level.block.Blocks.OAK_PLANKS;

@SuppressWarnings("deprecation")
public class TrapdoorBlock extends AbstractBlockTrapdoor<TrapdoorBlock> implements IMateriallyTexturedBlock, ICachedItemGroupBlock, EntityBlock
{
    public static final EnumProperty<TrapdoorType> TYPE = EnumProperty.create("type", TrapdoorType.class);
    public static final List<IMateriallyTexturedBlockComponent> COMPONENTS = ImmutableList.<IMateriallyTexturedBlockComponent>builder()
                                                                               .add(new SimpleRetexturableComponent(new ResourceLocation("minecraft:block/oak_planks"), ModTags.SLAB_MATERIALS, OAK_PLANKS))
                                                                               .build();

    private final List<ItemStack> fillItemGroupCache = Lists.newArrayList();

    public TrapdoorBlock()
    {
        super(Properties.of(Material.WOOD, OAK_PLANKS.defaultMaterialColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD));
        this.registerDefaultState(this.defaultBlockState().setValue(TYPE, TrapdoorType.FULL));
        setRegistryName(com.ldtteam.domumornamentum.util.Constants.MOD_ID, "vanilla_trapdoors_compat");
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(TYPE);
    }

    @Override
    public void registerItemBlock(final IForgeRegistry<Item> registry, final Item.Properties properties)
    {
        registry.register((new TrapdoorBlockItem(this, properties)).setRegistryName(Objects.requireNonNull(this.getRegistryName())));
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

        IMateriallyTexturedBlockComponent materialComponent = getComponents().get(0);

        final Tag<Block> materialCandidate = materialComponent.getValidSkins();

        try {
            for (final TrapdoorType trapdoorType : TrapdoorType.values())
            {
                materialCandidate.getValues().forEach(cover -> {
                    final Map<ResourceLocation, Block> textureData = Maps.newHashMap();

                    textureData.put(materialComponent.getId(), cover);

                    final MaterialTextureData materialTextureData = new MaterialTextureData(textureData);

                    final CompoundTag textureNbt = materialTextureData.serializeNBT();

                    final ItemStack result = new ItemStack(this);
                    result.getOrCreateTag().put("textureData", textureNbt);
                    result.getOrCreateTag().putString("type", trapdoorType.toString().toUpperCase());

                    fillItemGroupCache.add(result);
                });
            }


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

        final String type = stack.getOrCreateTag().getString("type");
        worldIn.setBlock(
          pos,
          state.setValue(TYPE, TrapdoorType.valueOf(type.toUpperCase())),
          Constants.BlockFlags.DEFAULT_AND_RERENDER
        );

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
    public @NotNull List<ItemStack> getDrops(final @NotNull BlockState state, final @NotNull LootContext.Builder builder)
    {
        final BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (!(blockEntity instanceof final MateriallyTexturedBlockEntity texturedBlockEntity))
            return Lists.newArrayList();

        final MaterialTextureData materialTextureData = texturedBlockEntity.getTextureData();

        final CompoundTag textureNbt = materialTextureData.serializeNBT();

        final ItemStack result = new ItemStack(this);
        result.getOrCreateTag().put("textureData", textureNbt);
        result.getOrCreateTag().putString("type", state.getValue(TYPE).toString().toUpperCase());

        return Lists.newArrayList(result);
    }

    @Override
    public void resetCache()
    {
        fillItemGroupCache.clear();
    }
}
