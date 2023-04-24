package com.ldtteam.domumornamentum.datagen.post;
import net.minecraft.world.level.block.RotatedPillarBlock;
import com.ldtteam.domumornamentum.block.decorative.PostBlock;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.types.PostType;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


import static com.ldtteam.domumornamentum.block.AbstractPanelBlockTrapdoor.HALF;
//import static com.ldtteam.domumornamentum.block.AbstractPanelBlockTrapdoor.OPEN;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;


public class PostBlockStateProvider extends BlockStateProvider {

    public PostBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        final MultiPartBlockStateBuilder builder = getMultipartBuilder(ModBlocks.getInstance().getPost());

        for (Direction facingValue : FACING.getPossibleValues()) {
            for (PostType typeValue : PostBlock.TYPE.getPossibleValues()) {
                for (Half halfValue : HALF.getPossibleValues()) {

                        final var partBuilder = builder.part();


                        partBuilder.modelFile(models()
                                        .withExistingParent("block/post/post_" + typeValue.getSerializedName(), modLoc("block/post/post_%s_spec".formatted(typeValue.getSerializedName())))
                                        .customLoader(MateriallyTexturedModelBuilder::new)
                                        .end())
                                //direction.getAxis
                                .rotationY(getYFromFacing(facingValue) + getFromHalf(halfValue))
                                .rotationX(getFromHalf(halfValue))
                                .addModel()


                                .condition(PostBlock.AXIS, getAxisFromFacing(facingValue))
                                .condition(PostBlock.TYPE, typeValue)
                                .condition(HALF, halfValue)
                                //.condition(OPEN, openValue)
                                .end();

                }
            }
        }

        final ItemModelBuilder itemSpecModelBuilder = itemModels()
                .withExistingParent("post_spec", mcLoc("block/thin_block"));
        PostType[] values = PostType.values();
        for (int i = 0; i < values.length; i++) {
            PostType value = values[i];
            final ItemModelBuilder.OverrideBuilder overrideBuilder = itemSpecModelBuilder.override();
            overrideBuilder.predicate(new ResourceLocation(Constants.POST_MODEL_OVERRIDE), i);
            overrideBuilder.model(itemModels().getExistingFile(modLoc("block/post/post_%s_spec".formatted(value.getSerializedName()))));
            overrideBuilder.end();
        }

        final ItemModelBuilder itemModelBuilder = itemModels()
                .getBuilder(ModBlocks.getInstance().getPost().getRegistryName().getPath()).parent(itemSpecModelBuilder)
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
    private Direction getAxisFromFacing(final Direction facing) {
        return switch (facing) {
            default -> Axis.Y;
            case NORTH -> Axis.Z;
            case SOUTH -> Axis.Z;
            case WEST -> Axis.X;
            case EAST -> Axis.X;
            case UP -> Axis.Y;
            case DOWN -> Axis.Y;
        };
    }
    private int getFromHalf(final Half half) {
        return half == Half.TOP? 180 : 0;
    }


    @NotNull
    @Override
    public String getName() {
        return "Post BlockStates Provider";
    }
}
