package com.ldtteam.domumornamentum.datagen.post;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.PostBlock;
import com.ldtteam.domumornamentum.block.types.PostType;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static com.ldtteam.domumornamentum.block.AbstractPostBlock.FACING;


public class PostBlockStateProvider extends BlockStateProvider {

    public PostBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        final MultiPartBlockStateBuilder builder = getMultipartBuilder(ModBlocks.getInstance().getPost());

        for (Direction facingValue : FACING.getPossibleValues()) {
            for (PostType typeValue : PostBlock.TYPE.getPossibleValues()) {
                //for (Half halfValue : AbstractPostBlock.HALF.getPossibleValues()) {

                    final var partBuilder = builder.part();

                    partBuilder.modelFile(models()
                                    .withExistingParent("block/post/post_" + typeValue.getSerializedName(), modLoc("block/post/post_%s_spec".formatted(typeValue.getSerializedName())))
                                    .customLoader(MateriallyTexturedModelBuilder::new)
                                    .end())

                            .rotationY(getYFromFacing(facingValue)) //+ getFromHalf(halfValue))
                            .rotationX(getXFromFacing(facingValue)) // + getFromHalf(halfValue))
                            .addModel()


                            .condition(FACING, facingValue)
                            .condition(PostBlock.TYPE, typeValue)
                            //.condition(HALF, halfValue)
                            .end();

                //}
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


    private int getXFromFacing(final Direction facing) {
        return switch (facing) {

            case UP -> 0;
            case DOWN -> 180;
            case NORTH -> 90;
            case SOUTH -> 90;
            case WEST -> 90;
            case EAST -> 90;
        };
    }



    @NotNull
    @Override
    public String getName() {
        return "Post BlockStates Provider";
    }
}
