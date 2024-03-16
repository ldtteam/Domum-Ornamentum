package com.ldtteam.domumornamentum.block.decorative;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.ldtteam.domumornamentum.block.*;
import com.ldtteam.domumornamentum.block.components.SimpleRetexturableComponent;
import com.ldtteam.domumornamentum.block.types.TrapdoorType;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.entity.block.MateriallyTexturedBlockEntity;
import com.ldtteam.domumornamentum.entity.block.ModBlockEntityTypes;
import com.ldtteam.domumornamentum.recipe.FinishedDORecipe;
import com.ldtteam.domumornamentum.recipe.ModRecipeSerializers;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.BlockUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static net.minecraft.world.level.block.Blocks.OAK_PLANKS;

@SuppressWarnings("deprecation")
public class PanelBlock extends AbstractPanelBlockTrapdoor<PanelBlock> implements IMateriallyTexturedBlock, ICachedItemGroupBlock, EntityBlock
{
    public static final EnumProperty<TrapdoorType>              TYPE       = EnumProperty.create("type", TrapdoorType.class);
    public static final List<IMateriallyTexturedBlockComponent> COMPONENTS = ImmutableList.<IMateriallyTexturedBlockComponent>builder()
                                                                               .add(new SimpleRetexturableComponent(new ResourceLocation("minecraft:block/oak_planks"), ModTags.TRAPDOORS_MATERIALS, OAK_PLANKS))
                                                                               .build();

    private final List<ItemStack> fillItemGroupCache = Lists.newArrayList();

    public PanelBlock()
    {
        super(Properties.of().mapColor(MapColor.WOOD).strength(3.0F).noOcclusion().isValidSpawn((state, blockGetter, pos, type) -> false));
        this.registerDefaultState(this.defaultBlockState().setValue(TYPE, TrapdoorType.FULL));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.@NotNull Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(TYPE);
    }

    @Override
    public @NotNull List<IMateriallyTexturedBlockComponent> getComponents()
    {
        return COMPONENTS;
    }

    @Override
    public void fillItemCategory(final @NotNull NonNullList<ItemStack> items)
    {
        if (!fillItemGroupCache.isEmpty()) {
            items.addAll(fillItemGroupCache);
            return;
        }

        try {
            for (final TrapdoorType trapdoorType : TrapdoorType.values())
            {
                final ItemStack result = new ItemStack(this);
                result.getOrCreateTag().putString("type", trapdoorType.name().toUpperCase());

                fillItemGroupCache.add(result);
            }
        } catch (IllegalStateException exception)
        {
            //Ignored. Thrown during start up.
        }

        items.addAll(fillItemGroupCache);
    }

    @Override
    public void setPlacedBy(final @NotNull Level worldIn, final @NotNull BlockPos pos, final @NotNull BlockState state, @Nullable final LivingEntity placer, final @NotNull ItemStack stack)
    {
        super.setPlacedBy(worldIn, pos, state, placer, stack);

        String type = stack.getOrCreateTag().getString("type");
        if (type == "") {
            type = TrapdoorType.FULL.name();
        }

        worldIn.setBlock(
          pos,
          state.setValue(TYPE, TrapdoorType.valueOf(type.toUpperCase())),
          Block.UPDATE_ALL
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
        return new MateriallyTexturedBlockEntity(blockPos, blockState);
    }

    @Override
    public @NotNull List<ItemStack> getDrops(final @NotNull BlockState state, final @NotNull LootParams.Builder builder)
    {
        return BlockUtils.getMaterializedItemStack(builder, (s, e) -> {
            s.getOrCreateTag().putString("type", e.getBlockState().getValue(TYPE).toString().toUpperCase());
            return s;
        });
    }

    @Override
    public ItemStack getCloneItemStack(final BlockState state, final HitResult target, final BlockGetter world, final BlockPos pos, final Player player)
    {
        return BlockUtils.getMaterializedItemStack(player, world, pos, (s, e) -> {
            s.getOrCreateTag().putString("type", e.getBlockState().getValue(TYPE).toString().toUpperCase());
            return s;
        });
    }

    @Override
    public void resetCache()
    {
        fillItemGroupCache.clear();
    }

    @Override
    public @NotNull Block getBlock()
    {
        return this;
    }

    @Override
    public @NotNull Collection<FinishedRecipe> getValidCutterRecipes()
    {
        final List<FinishedRecipe> recipes = new ArrayList<>();

        for (final TrapdoorType value : TrapdoorType.values())
        {
            recipes.add(
              new FinishedDORecipe() {
                  @Override
                  public void serializeRecipeData(final @NotNull JsonObject jsonObject)
                  {
                      final CompoundTag tag = new CompoundTag();
                      tag.putString("type", value.toString().toUpperCase());

                      jsonObject.addProperty("block", Objects.requireNonNull(getRegistryName(getBlock())).toString());
                      jsonObject.addProperty("nbt", tag.toString());
                      jsonObject.addProperty("count", COMPONENTS.size() * 4);
                  }

                  @Override
                  public @NotNull ResourceLocation getId()
                  {
                      return new ResourceLocation(Objects.requireNonNull(getRegistryName(getBlock())).toString() + "_" + value.getSerializedName());
                  }
              }
            );
        }

        return recipes;
    }

    @Override
    public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        return getDOExplosionResistance(this, state, level, pos, explosion);
    }

    @Override
    public float getDestroyProgress(@NotNull BlockState state, @NotNull Player player, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return getDODestroyProgress(this, state, player, level, pos);
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return getDOSoundType(this, state, level, pos, entity);
    }

    @Override
    public IMateriallyTexturedBlockComponent getMainComponent() {
        return COMPONENTS.get(0);
    }
}
