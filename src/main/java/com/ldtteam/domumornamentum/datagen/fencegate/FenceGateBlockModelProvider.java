package com.ldtteam.domumornamentum.datagen.fencegate;

import com.ldtteam.datagenerators.models.block.BlockModelJson;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

public class FenceGateBlockModelProvider implements DataProvider
{
    private final DataGenerator generator;

    public FenceGateBlockModelProvider(final DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull HashCache cache) throws IOException
    {
        final BlockModelJson gateJson = new BlockModelJson();

        gateJson.setLoader(Constants.MATERIALLY_TEXTURED_MODEL_LOADER);
        gateJson.setParent(new ResourceLocation(Constants.MOD_ID, "block/fence_gates/fence_gate_spec").toString());

        final String gateName = "fence_gate.json";
        final Path gateSavePath = this.generator.getOutputFolder().resolve(DataGeneratorConstants.FENCE_GATES_BLOCK_MODELS_DIR).resolve(gateName);

        DataProvider.save(DataGeneratorConstants.GSON, cache, DataGeneratorConstants.serialize(gateJson), gateSavePath);

        final BlockModelJson gateWallJson = new BlockModelJson();

        gateWallJson.setLoader(Constants.MATERIALLY_TEXTURED_MODEL_LOADER);
        gateWallJson.setParent(new ResourceLocation(Constants.MOD_ID, "block/fence_gates/fence_gate_wall_spec").toString());

        final String gateWallName = "fence_gate_wall.json";
        final Path gateWallSavePath = this.generator.getOutputFolder().resolve(DataGeneratorConstants.FENCE_GATES_BLOCK_MODELS_DIR).resolve(gateWallName);

        DataProvider.save(DataGeneratorConstants.GSON, cache, DataGeneratorConstants.serialize(gateWallJson), gateWallSavePath);

        final BlockModelJson openGateJson = new BlockModelJson();

        openGateJson.setLoader(Constants.MATERIALLY_TEXTURED_MODEL_LOADER);
        openGateJson.setParent(new ResourceLocation(Constants.MOD_ID, "block/fence_gates/fence_gate_open_spec").toString());

        final String openGateName = "fence_gate_open.json";
        final Path openGateSavePath = this.generator.getOutputFolder().resolve(DataGeneratorConstants.FENCE_GATES_BLOCK_MODELS_DIR).resolve(openGateName);

        DataProvider.save(DataGeneratorConstants.GSON, cache, DataGeneratorConstants.serialize(openGateJson), openGateSavePath);

        final BlockModelJson openWallGateJson = new BlockModelJson();

        openWallGateJson.setLoader(Constants.MATERIALLY_TEXTURED_MODEL_LOADER);
        openWallGateJson.setParent(new ResourceLocation(Constants.MOD_ID, "block/fence_gates/fence_gate_wall_open_spec").toString());

        final String openWallGateName = "fence_gate_wall_open.json";
        final Path openWallGateSavePath = this.generator.getOutputFolder().resolve(DataGeneratorConstants.FENCE_GATES_BLOCK_MODELS_DIR).resolve(openWallGateName);

        DataProvider.save(DataGeneratorConstants.GSON, cache, DataGeneratorConstants.serialize(openWallGateJson), openWallGateSavePath);

    }

    @Override
    @NotNull
    public String getName()
    {
        return "FenceGates Block Model Provider";
    }
}
