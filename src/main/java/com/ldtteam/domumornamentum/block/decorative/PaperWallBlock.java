package com.ldtteam.domumornamentum.block.decorative;

import com.ldtteam.domumornamentum.block.AbstractBlockPane;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockComponent;
import com.ldtteam.domumornamentum.block.types.PaperwallType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Collection;

import net.minecraft.block.AbstractBlock.Properties;

/**
 * The paperwall block class defining the paperwall.
 */
public class PaperWallBlock extends AbstractBlockPane<PaperWallBlock> implements IMateriallyTexturedBlock
{
    /**
     * The variants for the paperwall.
     */
    public static final EnumProperty<PaperwallType> VARIANT = EnumProperty.create("variant", PaperwallType.class);

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

    public PaperWallBlock(final String type)
    {
        super(Properties.of(Material.GLASS).strength(BLOCK_HARDNESS, RESISTANCE));
        setRegistryName(type + "_" + BLOCK_NAME);
    }

    /**
     * Registry block at gameregistry.
     *
     * @param registry the registry to use.
     */
    @Override
    public void registerItemBlock(final IForgeRegistry<Item> registry, final Item.Properties properties)
    {
        registry.register((new BlockItem(this, properties)).setRegistryName(this.getRegistryName()));
    }

    @Override
    public BlockState rotate(final BlockState state, final IWorld world, final BlockPos pos, final Rotation direction)
    {
        switch (direction)
        {
            case CLOCKWISE_180:
                return state.setValue(NORTH, state.getValue(SOUTH))
                         .setValue(EAST, state.getValue(WEST)).setValue(SOUTH, state.getValue(NORTH))
                         .setValue(WEST, state.getValue(EAST));
            case COUNTERCLOCKWISE_90:
                return state.setValue(NORTH, state.getValue(EAST))
                         .setValue(EAST, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(WEST))
                         .setValue(WEST, state.getValue(NORTH));
            case CLOCKWISE_90:
                return state.setValue(NORTH, state.getValue(WEST))
                         .setValue(EAST, state.getValue(NORTH)).setValue(SOUTH, state.getValue(EAST))
                         .setValue(WEST, state.getValue(SOUTH));
            default:
                return state;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, WEST, SOUTH, VARIANT, WATERLOGGED);
    }

    @Override
    public Collection<IMateriallyTexturedBlockComponent> getComponents()
    {
        return null;
    }
}
