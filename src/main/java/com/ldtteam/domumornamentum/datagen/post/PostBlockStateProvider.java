package com.ldtteam.domumornamentum.datagen.post;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.PostBlock;
import com.ldtteam.domumornamentum.block.types.PostType;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static com.ldtteam.domumornamentum.block.AbstractPostBlock.UPRIGHT;
import static com.ldtteam.domumornamentum.block.AbstractPostBlock.FACING;

public class PostBlockStateProvider extends BlockStateProvider {

    public PostBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        final MultiPartBlockStateBuilder builder = getMultipartBuilder(ModBlocks.getInstance().getPost());

        for (Direction facingValue : FACING.getPossibleValues()) {
            for (Boolean upright : UPRIGHT.getPossibleValues())  {
                for (PostType typeValue : PostBlock.TYPE.getPossibleValues()) {


                    final var partBuilder = builder.part();
                    partBuilder.modelFile(models()
                                    .withExistingParent("block/post/post_" + typeValue.getSerializedName(), modLoc("block/post/post_%s_spec".formatted(typeValue.getSerializedName())))
                                    .customLoader(MateriallyTexturedModelBuilder::new)
                                    .end())
                            /*
                               posts can be placed upright normally, or against a face if the face is vertical.
                               If placing horizontally (upright is false), add 90 to X rotation
                             */
                            .rotationY(getYFromFacing(facingValue))
                            .rotationX(getXFromFacing(facingValue) + getUpright(upright, facingValue) )
                            .addModel()

                            .condition(UPRIGHT, upright)
                            .condition(FACING, facingValue)
                            .condition(PostBlock.TYPE, typeValue)

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
            overrideBuilder.predicate(Constants.POST_MODEL_OVERRIDE, i);
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


    private int getXFromFacing(final Direction facing) {
        return switch (facing) {

            case UP -> 180;
            case DOWN -> 0;
            case NORTH -> 0;
            case SOUTH -> 0;
            case WEST -> 0;
            case EAST -> 0;
        };
    }
    private int getUpright(final Boolean upright, Direction direction) {
        if(!upright){
            if(direction != Direction.DOWN){
                if (direction != Direction.UP){
                    return 90;
                }
            }
        }
        return 0;
    }



    @NotNull
    @Override
    public String getName() {
        return "Post BlockStates Provider";
    }
}
