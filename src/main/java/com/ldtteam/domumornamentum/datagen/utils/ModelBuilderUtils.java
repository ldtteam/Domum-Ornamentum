package com.ldtteam.domumornamentum.datagen.utils;

import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;

public final class ModelBuilderUtils {

    private ModelBuilderUtils() {
        throw new IllegalStateException("Can not instantiate an instance of: ModelBuilderUtils. This is a utility class");
    }

    public static <T extends ModelBuilder<T>> T applyDefaultItemTransforms(final T modelBuilder) {
        return modelBuilder.transforms()
                .transform(ItemDisplayContext.GUI)
                .rotation(30, 225, 0)
                .translation(0,0.5f,0)
                .scale(0.625f)
                .end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
                .rotation(75, 45, 0)
                .translation(0, 2.5f, 0)
                .scale(0.375f)
                .end()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                .rotation(75, 45, 0)
                .translation(0, 2.5f, 0)
                .scale(0.375f)
                .end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
                .rotation(0, 225, 0)
                .scale(0.4f)
                .end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                .rotation(0, 225, 0)
                .scale(0.4f)
                .end()
                .transform(ItemDisplayContext.GROUND)
                .translation(0,3,0)
                .scale(0.25f)
                .end()
                .transform(ItemDisplayContext.FIXED)
                .scale(0.5f)
                .end()
                .transform(ItemDisplayContext.HEAD)
                .scale(1.03f)
                .end()
                .end();
    }

    public static <T extends ModelBuilder<T>> T applyDoorItemTransforms(final T modelBuilder) {
        return modelBuilder.transforms()
                .transform(ItemDisplayContext.GUI)
                .rotation(30, 225, 0)
                .translation(-2,-4,0)
                .scale(0.45f)
                .end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
                .rotation(75, 45, 0)
                .translation(0, 2.5f, 0)
                .scale(0.375f)
                .end()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                .rotation(75, 45, 0)
                .translation(0, 2.5f, 0)
                .scale(0.375f)
                .end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
                .rotation(0, 225, 0)
                .scale(0.4f)
                .end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                .rotation(0, 225, 0)
                .scale(0.4f)
                .end()
                .transform(ItemDisplayContext.GROUND)
                .translation(0,3,0)
                .scale(0.25f)
                .end()
                .transform(ItemDisplayContext.FIXED)
                .scale(0.5f)
                .end()
                .transform(ItemDisplayContext.HEAD)
                .scale(1.03f)
                .end()
                .end();
    }
}
