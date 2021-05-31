package com.ldtteam.domumornamentum.block.decorative;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.ldtteam.domumornamentum.block.AbstractBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.components.SimpleRetexturableComponent;
import com.ldtteam.domumornamentum.block.types.TimberFrameType;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.entity.block.ModBlockEntityTypes;
import com.ldtteam.domumornamentum.entity.block.MateriallyTexturedBlockEntity;
import com.ldtteam.domumornamentum.item.decoration.TimberFrameBlockItem;
import com.ldtteam.domumornamentum.tag.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.ITag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Decorative block
 */
public class TimberFrameBlock extends AbstractBlock<TimberFrameBlock> implements IMateriallyTexturedBlock
{

    public static final List<IMateriallyTexturedBlockComponent> COMPONENTS = ImmutableList.<IMateriallyTexturedBlockComponent>builder()
        .add(new SimpleRetexturableComponent(new ResourceLocation("block/oak_planks"), ModTags.TIMBERFRAMES_FRAME, Blocks.OAK_PLANKS))
        .add(new SimpleRetexturableComponent(new ResourceLocation("block/dark_oak_planks"), ModTags.TIMBERFRAMES_CENTER, Blocks.DARK_OAK_PLANKS))
        .build();

    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    /**
     * The hardness this block has.
     */
    private static final float                         BLOCK_HARDNESS = 3F;

    /**
     * The resistance this block has.
     */
    private static final float                         RESISTANCE     = 1F;

    /**
     * The type of this timber frame type.
     */
    private TimberFrameType timberFrameType;

    /**
     * Constructor for the TimberFrame
     */
    public TimberFrameBlock(final TimberFrameType timberFrameType)
    {
        super(Properties.create(Material.WOOD).hardnessAndResistance(BLOCK_HARDNESS, RESISTANCE).notSolid());
        setRegistryName(getName(timberFrameType));

        this.timberFrameType = timberFrameType;
    }

    @Override
    public void registerItemBlock(final IForgeRegistry<Item> registry, final Item.Properties properties)
    {
        registry.register((new TimberFrameBlockItem(this, properties)).setRegistryName(Objects.requireNonNull(this.getRegistryName())));
    }

    public static String getName(final TimberFrameType timberFrameType)
    {
        return timberFrameType.getName();
    }

    @Override
    protected void fillStateContainer(@NotNull final StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(FACING);
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    @NotNull
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    public List<IMateriallyTexturedBlockComponent> getComponents()
    {
        return COMPONENTS;
    }

    @Override
    public void fillItemGroup(final ItemGroup group, final NonNullList<ItemStack> items)
    {
        IMateriallyTexturedBlockComponent outerComponent = getComponents().get(0);
        IMateriallyTexturedBlockComponent innerComponent = getComponents().get(1);

        final ITag<Block> outerCandidates = outerComponent.getValidSkins();
        final ITag<Block> innerCandidates = innerComponent.getValidSkins();

        try {
            outerCandidates.getAllElements().forEach(outer -> {
                innerCandidates.getAllElements().forEach(inner ->{
                    final Map<ResourceLocation, Block> textureData = Maps.newHashMap();

                    textureData.put(outerComponent.getId(), outer);
                    textureData.put(innerComponent.getId(), inner);

                    final MaterialTextureData materialTextureData = new MaterialTextureData(textureData);

                    final CompoundNBT textureNbt = materialTextureData.serializeNBT();

                    final ItemStack result = new ItemStack(this);
                    result.getOrCreateTag().put("textureData", textureNbt);

                    items.add(result);
                });
            });
        } catch (IllegalStateException exception)
        {
            //Ignored. Thrown during start up.
        }


    }

    public TimberFrameType getTimberFrameType()
    {
        return this.timberFrameType;
    }

    @Override
    public void onBlockPlacedBy(
      final World worldIn, final BlockPos pos, final BlockState state, @Nullable final LivingEntity placer, final ItemStack stack)
    {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        final CompoundNBT textureData = stack.getOrCreateChildTag("textureData");
        final TileEntity tileEntity = worldIn.getTileEntity(pos);

        if (tileEntity instanceof MateriallyTexturedBlockEntity)
            ((MateriallyTexturedBlockEntity) tileEntity).updateTextureDataWith(MaterialTextureData.deserializeFromNBT(textureData));
    }

    @Override
    public boolean hasTileEntity(final BlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world)
    {
        return new MateriallyTexturedBlockEntity(ModBlockEntityTypes.MATERIALLY_TEXTURED_BLOCK_ENTITY_TILE_ENTITY_TYPE);
    }
}
