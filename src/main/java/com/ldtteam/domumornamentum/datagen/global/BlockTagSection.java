package com.ldtteam.domumornamentum.datagen.global;

import net.minecraft.core.HolderLookup;
import org.jetbrains.annotations.NotNull;

/**
 * A section of block tags contributed to the single shared {@link ModBlockTagsProvider} instance.
 * <p>
 * All sections share one provider, so same-namespace tag references (e.g. #domum_ornamentum:bricks)
 * always validate against the same builder map, as required by NeoForge's per-instance tag validation.
 */
public interface BlockTagSection {

    void addTags(ModBlockTagsProvider host, HolderLookup.@NotNull Provider registries);

}
