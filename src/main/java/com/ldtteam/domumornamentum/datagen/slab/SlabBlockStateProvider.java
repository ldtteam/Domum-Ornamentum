package com.ldtteam.domumornamentum.datagen.slab;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.vanilla.SlabBlock;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static com.ldtteam.domumornamentum.block.vanilla.SlabBlock.TYPE;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

public class SlabBlockStateProvider extends BlockStateProvider
{
    public SlabBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        createBlockstateFile(ModBlocks.getInstance().getSlab());
    }
    private void createBlockstateFile(final SlabBlock slabBlock)
    {
        final MultiPartBlockStateBuilder builder = getMultipartBuilder(slabBlock);
        for (Direction facingValue : FACING.getPossibleValues()) {
            for (SlabType value : SlabBlock.TYPE.getPossibleValues()) {
                builder.part()
                        .modelFile(models().withExistingParent("block/slab/slab_" + value.getSerializedName(), modLoc("block/slab/slab_" + value.getSerializedName() + "_spec"))
                                .customLoader(MateriallyTexturedModelBuilder::new)
                                .end())
                        .rotationX(getXFromFacing(facingValue))
                        .rotationY(getYFromFacing(facingValue))

                        .addModel()
                        .condition(FACING, facingValue)
                        .condition(TYPE, value)
                        .end();
            }
        }
        final ItemModelBuilder itemModelBuilder = itemModels().withExistingParent(slabBlock.getRegistryName().getPath(), modLoc("item/slab/slab_spec"))
                .customLoader(MateriallyTexturedModelBuilder::new)
                .end();
        ModelBuilderUtils.applyDefaultItemTransforms(itemModelBuilder);
    }
    @Contract(pure = true)
    private int getYFromFacing(final Direction facing) {
        return switch (facing) {
            default -> 0;
            case SOUTH -> 180;
            case WEST -> 270;
            case EAST -> 90;
        };
    }
    private int getXFromFacing(final Direction facing) {
        return switch (facing) {
            case UP -> 180;
            case DOWN -> 0;
            case NORTH -> 90;
            case SOUTH -> 90;
            case WEST -> 90;
            case EAST -> 90;
        };
    }
    @NotNull
    @Override
    public String getName()
    {
        return "Slab BlockStates Provider";
    }
}
