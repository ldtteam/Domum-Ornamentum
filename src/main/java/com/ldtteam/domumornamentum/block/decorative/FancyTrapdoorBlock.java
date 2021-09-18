package com.ldtteam.domumornamentum.block.decorative;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.ldtteam.domumornamentum.block.AbstractBlockTrapdoor;
import com.ldtteam.domumornamentum.block.ICachedItemGroupBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.components.SimpleRetexturableComponent;
import com.ldtteam.domumornamentum.block.types.FancyTrapdoorType;
import com.ldtteam.domumornamentum.block.types.TrapdoorType;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.entity.block.MateriallyTexturedBlockEntity;
import com.ldtteam.domumornamentum.entity.block.ModBlockEntityTypes;
import com.ldtteam.domumornamentum.item.decoration.FancyTrapdoorBlockItem;
import com.ldtteam.domumornamentum.item.vanilla.TrapdoorBlockItem;
import com.ldtteam.domumornamentum.recipe.ModRecipeSerializers;
import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.BlockUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static net.minecraft.world.level.block.Blocks.ACACIA_PLANKS;
import static net.minecraft.world.level.block.Blocks.OAK_PLANKS;

@SuppressWarnings("deprecation")
public class FancyTrapdoorBlock extends AbstractBlockTrapdoor<FancyTrapdoorBlock> implements IMateriallyTexturedBlock, ICachedItemGroupBlock, EntityBlock
{
    public static final EnumProperty<FancyTrapdoorType> TYPE = EnumProperty.create("type", FancyTrapdoorType.class);
    public static final List<IMateriallyTexturedBlockComponent> COMPONENTS = ImmutableList.<IMateriallyTexturedBlockComponent>builder()
                                                                               .add(new SimpleRetexturableComponent(new ResourceLocation("minecraft:block/oak_planks"), ModTags.FANCY_TRAPDOORS_MATERIALS, OAK_PLANKS))
                                                                               .add(new SimpleRetexturableComponent(new ResourceLocation("minecraft:block/acacia_planks"), ModTags.FANCY_TRAPDOORS_MATERIALS, ACACIA_PLANKS))
                                                                               .build();

    private final List<ItemStack> fillItemGroupCache = Lists.newArrayList();

    public FancyTrapdoorBlock()
    {
        super(Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn((state, blockGetter, pos, type) -> false));
        this.registerDefaultState(this.defaultBlockState().setValue(TYPE, FancyTrapdoorType.FULL));
        setRegistryName(com.ldtteam.domumornamentum.util.Constants.MOD_ID, "fancy_trapdoors");
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.@NotNull Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(TYPE);
    }

    @Override
    public void registerItemBlock(final IForgeRegistry<Item> registry, final Item.Properties properties)
    {
        registry.register((new FancyTrapdoorBlockItem(this, properties)).setRegistryName(Objects.requireNonNull(this.getRegistryName())));
    }

    @Override
    public @NotNull List<IMateriallyTexturedBlockComponent> getComponents()
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

        IMateriallyTexturedBlockComponent innerComponent = getComponents().get(0);
        IMateriallyTexturedBlockComponent outerComponent = getComponents().get(1);

        final Tag<Block> innerCandidate = innerComponent.getValidSkins();
        final Tag<Block> outerCandidate = innerComponent.getValidSkins();

        try {
            for (final FancyTrapdoorType trapdoorType : FancyTrapdoorType.values())
            {
                innerCandidate.getValues().forEach(cover -> {
                    final Map<ResourceLocation, Block> textureData = Maps.newHashMap();

                    textureData.put(innerComponent.getId(), cover);

                    final MaterialTextureData materialTextureData = new MaterialTextureData(textureData);

                    final CompoundTag textureNbt = materialTextureData.serializeNBT();

                    final ItemStack result = new ItemStack(this);
                    result.getOrCreateTag().put("textureData", textureNbt);
                    result.getOrCreateTag().putString("type", trapdoorType.toString().toUpperCase());

                    fillItemGroupCache.add(result);
                    outerCandidate.getValues().forEach(outer -> {
                        final Map<ResourceLocation, Block> combinedTextureData = Maps.newHashMap(textureData);

                        combinedTextureData.put(outerComponent.getId(), outer);

                        final MaterialTextureData combinedMaterialTextureData = new MaterialTextureData(combinedTextureData);

                        final CompoundTag combinedTextureNbt = combinedMaterialTextureData.serializeNBT();

                        final ItemStack combinedResult = new ItemStack(this);
                        combinedResult.getOrCreateTag().put("textureData", combinedTextureNbt);
                        combinedResult.getOrCreateTag().putString("type", trapdoorType.toString().toUpperCase());

                        fillItemGroupCache.add(combinedResult);
                    });
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
          state.setValue(TYPE, FancyTrapdoorType.valueOf(type.toUpperCase())),
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
        return BlockUtils.getMaterializedItemStack(builder, (s, e) -> {
            s.getOrCreateTag().putString("type", e.getBlockState().getValue(TYPE).toString().toUpperCase());
            return s;
        });
    }

    @Override
    public ItemStack getPickBlock(
      final BlockState state, final HitResult target, final BlockGetter world, final BlockPos pos, final Player player)
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

        for (final FancyTrapdoorType value : FancyTrapdoorType.values())
        {
            recipes.add(
              new FinishedRecipe() {
                  @Override
                  public void serializeRecipeData(final @NotNull JsonObject jsonObject)
                  {
                      final CompoundTag tag = new CompoundTag();
                      tag.putString("type", value.toString().toUpperCase());

                      jsonObject.addProperty("block", Objects.requireNonNull(getBlock().getRegistryName()).toString());
                      jsonObject.addProperty("nbt", tag.toString());
                  }

                  @Override
                  public @NotNull ResourceLocation getId()
                  {
                      return new ResourceLocation(Objects.requireNonNull(getBlock().getRegistryName()).toString() + "_" + value.getSerializedName());
                  }

                  @Override
                  public @NotNull RecipeSerializer<?> getType()
                  {
                      return ModRecipeSerializers.ARCHITECTS_CUTTER;
                  }

                  @Nullable
                  @Override
                  public JsonObject serializeAdvancement()
                  {
                      return null;
                  }

                  @Nullable
                  @Override
                  public ResourceLocation getAdvancementId()
                  {
                      return null;
                  }
              }
            );
        }

        return recipes;
    }
}
