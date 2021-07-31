package com.ldtteam.domumornamentum.datagen.slab;

import com.ldtteam.datagenerators.models.ModelDisplayPositionJson;
import com.ldtteam.datagenerators.models.ModelDisplayPositionsEnum;
import com.ldtteam.datagenerators.models.XYZDoubleListJson;
import com.ldtteam.datagenerators.models.XYZIntListJson;
import com.ldtteam.datagenerators.models.item.ItemModelJson;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.util.Constants;
import com.ldtteam.domumornamentum.util.DataGeneratorConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SlabItemModelProvider implements DataProvider
{
    private final DataGenerator generator;

    public SlabItemModelProvider(final DataGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull HashCache cache) throws IOException
    {
        final ItemModelJson modelJson = new ItemModelJson();
        modelJson.setDisplay(getDisplay());

        final String modelLocation = Constants.MOD_ID + ":item/slabs/slab_spec";

        modelJson.setParent(modelLocation);
        modelJson.setLoader(Constants.MATERIALLY_TEXTURED_MODEL_LOADER.toString());

        if (ModBlocks.getSlab().getRegistryName() == null)
            return;

        final String name = ModBlocks.getSlab().getRegistryName().getPath();
        DataProvider.save(DataGeneratorConstants.GSON, cache, DataGeneratorConstants.serialize(modelJson), generator.getOutputFolder().resolve(DataGeneratorConstants.ITEM_MODEL_DIR).resolve(name + ".json"));
    }

    private Map<ModelDisplayPositionsEnum, ModelDisplayPositionJson> getDisplay()
    {
        final Map<ModelDisplayPositionsEnum, ModelDisplayPositionJson> display = new HashMap<>();

        // GUI

        final XYZIntListJson guiRotation = new XYZIntListJson(30, 225, 0);
        final XYZDoubleListJson guiTranslation = new XYZDoubleListJson(0, 0.5, 0);
        final XYZDoubleListJson guiScale = new XYZDoubleListJson(0.625, 0.625, 0.625);

        final ModelDisplayPositionJson guiPosition = new ModelDisplayPositionJson(guiRotation, guiTranslation, guiScale);
        display.put(ModelDisplayPositionsEnum.GUI, guiPosition);

        // THIRD PERSON

        final XYZIntListJson thirdPersonRotation = new XYZIntListJson(75, 45, 0);
        final XYZDoubleListJson thirdPersonTranslation = new XYZDoubleListJson(0, 2.5, 0);
        final XYZDoubleListJson thirdPersonScale = new XYZDoubleListJson(0.375, 0.375, 0.375);

        final ModelDisplayPositionJson thirdPersonPosition = new ModelDisplayPositionJson(thirdPersonRotation, thirdPersonTranslation, thirdPersonScale);
        display.put(ModelDisplayPositionsEnum.THIRD_PERSON_LEFT_HAND, thirdPersonPosition);
        display.put(ModelDisplayPositionsEnum.THIRD_PERSON_RIGHT_HAND, thirdPersonPosition);

        // FIRST PERSON && GROUND

        final XYZIntListJson firstPersonRotation = new XYZIntListJson(0, 225, 0);
        final XYZDoubleListJson firstPersonScale = new XYZDoubleListJson(0.4, 0.4, 0.4);

        final ModelDisplayPositionJson firstPersonPosition = new ModelDisplayPositionJson(firstPersonRotation, null, firstPersonScale);
        display.put(ModelDisplayPositionsEnum.FIRST_PERSON_LEFT_HAND, firstPersonPosition);
        display.put(ModelDisplayPositionsEnum.FIRST_PERSON_RIGHT_HAND, firstPersonPosition);

        // Ground

        final XYZDoubleListJson groundTranslation = new XYZDoubleListJson(0, 3, 0);
        final XYZDoubleListJson groundScale = new XYZDoubleListJson(0.25, 0.25, 0.25);

        final ModelDisplayPositionJson groundPosition = new ModelDisplayPositionJson(null, groundTranslation, groundScale);
        display.put(ModelDisplayPositionsEnum.GROUND, groundPosition);

        // FIXED

        final XYZDoubleListJson fixedScale = new XYZDoubleListJson(0.5, 0.5, 0.5);

        final ModelDisplayPositionJson fixedPosition = new ModelDisplayPositionJson(null, null, fixedScale);
        display.put(ModelDisplayPositionsEnum.FIXED, fixedPosition);

        // HEAD

        final XYZDoubleListJson headScale = new XYZDoubleListJson(1.03, 1.03, 1.03);

        final ModelDisplayPositionJson headPosition = new ModelDisplayPositionJson(null, null, headScale);
        display.put(ModelDisplayPositionsEnum.HEAD, headPosition);

        return display;
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Slab Item Model Provider";
    }
}
