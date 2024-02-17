package com.ldtteam.domumornamentum.datagen.pillar;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.PillarBlock;
import com.ldtteam.domumornamentum.block.types.PillarShapeType;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class PillarBlockStateProvider extends BlockStateProvider {

    public PillarBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (final PillarBlock pillar : ModBlocks.getInstance().getPillars())
        {
            createBlockStateFile(pillar);
        }
    }

    private void createBlockStateFile(PillarBlock pillar) {
        final MultiPartBlockStateBuilder builder = getMultipartBuilder(pillar);
        builder.part()
                .modelFile(models().withExistingParent(pillarModelLocation(pillar, PillarShapeType.PILLAR_COLUMN), modLoc(pillarSpecModelLocation(pillar, PillarShapeType.PILLAR_COLUMN)))
                        .customLoader(MateriallyTexturedModelBuilder::new)
                        .end())
                .addModel()
                .condition(PillarBlock.COLUMN, PillarShapeType.PILLAR_COLUMN)
                .end()
                .part()
                .modelFile(models().withExistingParent(pillarModelLocation(pillar, PillarShapeType.PILLAR_BASE), modLoc(pillarSpecModelLocation(pillar, PillarShapeType.PILLAR_BASE)))
                        .customLoader(MateriallyTexturedModelBuilder::new)
                        .end())
                .addModel()
                .condition(PillarBlock.COLUMN, PillarShapeType.PILLAR_BASE)
                .end()
                .part()
                .modelFile(models().withExistingParent(pillarModelLocation(pillar, PillarShapeType.PILLAR_CAPITAL), modLoc(pillarSpecModelLocation(pillar, PillarShapeType.PILLAR_CAPITAL)))
                        .customLoader(MateriallyTexturedModelBuilder::new)
                        .end())
                .addModel()
                .condition(PillarBlock.COLUMN, PillarShapeType.PILLAR_CAPITAL)
                .end()
                .part()
                .modelFile(models().withExistingParent(pillarModelLocation(pillar, PillarShapeType.FULL_PILLAR), modLoc(pillarSpecModelLocation(pillar, PillarShapeType.FULL_PILLAR)))
                        .customLoader(MateriallyTexturedModelBuilder::new)
                        .end())
                .addModel()
                .condition(PillarBlock.COLUMN, PillarShapeType.FULL_PILLAR)
                .end();

        final ItemModelBuilder itemBuilder = itemModels().withExistingParent(pillar.getRegistryName().getPath(), modLoc(pillarModelLocation(pillar, PillarShapeType.FULL_PILLAR)))
                .customLoader(MateriallyTexturedModelBuilder::new)
                .end();
        ModelBuilderUtils.applyDefaultItemTransforms(itemBuilder);
    }

    private String pillarModelLocation(PillarBlock pillar, PillarShapeType suffix) {
        return "block/pillar/" + pillar.getRegistryName().getPath() + "_" + suffix.getSerializedName();
    }

    private String pillarSpecModelLocation(PillarBlock pillar, PillarShapeType suffix) {
        return "block/pillar/" + pillar.getRegistryName().getPath() + "_" + suffix.getSpecificationName();
    }


    @NotNull
    @Override
    public String getName() {
        return "Pillar BlockStates Provider";
    }
}
