package com.ldtteam.domumornamentum.block.decorative;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.ldtteam.domumornamentum.block.AbstractBlockDoor;
import com.ldtteam.domumornamentum.block.ICachedItemGroupBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.components.SimpleRetexturableComponent;
import com.ldtteam.domumornamentum.block.types.FancyDoorType;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.entity.block.MateriallyTexturedBlockEntity;
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
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static net.minecraft.world.level.block.Blocks.ACACIA_PLANKS;
import static net.minecraft.world.level.block.Blocks.OAK_PLANKS;

@SuppressWarnings("deprecation")
public class FancyDoorBlock extends AbstractBlockDoor<FancyDoorBlock> implements IMateriallyTexturedBlock, ICachedItemGroupBlock, EntityBlock
{
    public static final EnumProperty<FancyDoorType>             TYPE       = EnumProperty.create("type", FancyDoorType.class);
    public static final List<IMateriallyTexturedBlockComponent> COMPONENTS = ImmutableList.<IMateriallyTexturedBlockComponent>builder()
                                                                               .add(new SimpleRetexturableComponent(new ResourceLocation("minecraft:block/oak_planks"), ModTags.FANCY_DOORS_MATERIALS, OAK_PLANKS))
                                                                               .add(new SimpleRetexturableComponent(new ResourceLocation("minecraft:block/acacia_planks"), ModTags.FANCY_DOORS_MATERIALS, ACACIA_PLANKS, true))
                                                                               .build();

    private final List<ItemStack> fillItemGroupCache = Lists.newArrayList();

    public FancyDoorBlock()
    {
        super(Properties.of().mapColor(MapColor.WOOD).strength(3.0F).noOcclusion().isValidSpawn((state, blockGetter, pos, type) -> false));
        this.registerDefaultState(this.defaultBlockState().setValue(TYPE, FancyDoorType.FULL));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.@NotNull Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(TYPE);
    }

    @Override
    public @NotNull Block getBlock()
    {
        return this;
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
            for (final FancyDoorType fancyDoorType : FancyDoorType.values()) {
                final ItemStack result = new ItemStack(this);
                result.getOrCreateTag().putString("type", fancyDoorType.toString().toUpperCase());

                fillItemGroupCache.add(result);
            }
        }
        catch (IllegalStateException exception) {
            //Ignored. Thrown during start up.
        }

        items.addAll(fillItemGroupCache);
    }

    @Override
    public void setPlacedBy(
      final @NotNull Level worldIn, final @NotNull BlockPos pos, final @NotNull BlockState state, @Nullable final LivingEntity placer, final @NotNull ItemStack stack)
    {
        super.setPlacedBy(worldIn, pos, state, Objects.requireNonNull(placer), stack);

        final String type = stack.getOrCreateTag().getString("type");
        worldIn.setBlock(
          pos,
          worldIn.getBlockState(pos).setValue(TYPE, FancyDoorType.valueOf(type.toUpperCase())),
          Block.UPDATE_ALL
        );
        worldIn.setBlock(
          pos.above(),
          worldIn.getBlockState(pos.above()).setValue(TYPE, FancyDoorType.valueOf(type.toUpperCase())),
          Block.UPDATE_ALL
        );

        final CompoundTag textureData = stack.getOrCreateTagElement("textureData");
        final BlockEntity lowerBlockEntity = worldIn.getBlockEntity(pos);

        if (lowerBlockEntity instanceof MateriallyTexturedBlockEntity)
            ((MateriallyTexturedBlockEntity) lowerBlockEntity).updateTextureDataWith(MaterialTextureData.deserializeFromNBT(textureData));

        final BlockEntity upperBlockEntity = worldIn.getBlockEntity(pos.above());

        if (upperBlockEntity instanceof MateriallyTexturedBlockEntity)
            ((MateriallyTexturedBlockEntity) upperBlockEntity).updateTextureDataWith(MaterialTextureData.deserializeFromNBT(textureData));
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
        // According to BlockLootSubProvider "door" loot tables should only drop items for the lower half of the door.
        if (!state.getValue(HALF).equals(DoubleBlockHalf.LOWER))
        {
            return Collections.emptyList();
        }
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
    public @NotNull Collection<FinishedRecipe> getValidCutterRecipes()
    {
        final List<FinishedRecipe> recipes = new ArrayList<>();

        for (final FancyDoorType value : FancyDoorType.values())
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
                  }

                  @Override
                  public @NotNull ResourceLocation getId()
                  {
                      return new ResourceLocation(Objects.requireNonNull(getRegistryName(getBlock())) + "_" + value.getSerializedName());
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
