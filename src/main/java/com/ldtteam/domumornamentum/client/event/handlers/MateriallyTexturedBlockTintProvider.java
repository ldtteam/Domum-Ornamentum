package com.ldtteam.domumornamentum.client.event.handlers;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.client.model.RetexturingHandler;
import com.ldtteam.domumornamentum.client.model.retexturing.TargetTextureSource;
import com.ldtteam.domumornamentum.entity.block.MateriallyTexturedBlockEntity;
import com.ldtteam.domumornamentum.util.Constants;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.extensions.common.IClientBlockExtensions;
import org.jspecify.annotations.NonNull;

/**
 * 26.1 replacement for the old {@code BlockColor}/{@code ItemColor} handlers (which no longer exist).
 *
 * <p>Retextured quads carry a {@code MaterialInfo.tintIndex} that indexes into this block instance's dynamic tint
 * list. NeoForge fills that list by calling {@link IClientBlockExtensions#collectDynamicTintValues} for blocks
 * registered here (vanilla only looks up per-block {@code BlockTintSource}s, which we deliberately do not register).
 * Each entry resolves the original tint source of whatever block state the slot currently mimics, so biome tints
 * and other dynamic target colors follow the mapping automatically.
 */
@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public final class MateriallyTexturedBlockTintProvider {

    private static final IClientBlockExtensions EXTENSIONS = new IClientBlockExtensions() {
        @Override
        public void collectDynamicTintValues(final @NonNull BlockState state, final BlockAndTintGetter level, final @NonNull BlockPos pos, final @NonNull IntList tintValues) {
            if (!(level.getBlockEntity(pos) instanceof MateriallyTexturedBlockEntity texturedBlockEntity)) {
                //No block entity: retexturing is inactive and no dynamic tints are needed for this instance.
                return;
            }

            for (final TargetTextureSource source : RetexturingHandler.targetsFor(texturedBlockEntity.getTextureData())) {
                if (source.originalTintIndex() == -1) {
                    tintValues.add(-1);
                    continue;
                }

                final BlockTintSource tintSource = Minecraft.getInstance().getBlockColors()
                        .getTintSource(source.targetBlockState(), source.originalTintIndex());

                //Resolve the target's own tint in our world position, so e.g. grass keeps its biome color.
                tintValues.add(tintSource != null ? tintSource.colorInWorld(source.targetBlockState(), level, pos) : -1);
            }
        }
    };

    @SubscribeEvent
    public static void onRegisterClientExtensions(final RegisterClientExtensionsEvent event) {
        final Block[] blocks = BuiltInRegistries.BLOCK.stream()
                .filter(IMateriallyTexturedBlock.class::isInstance)
                .toArray(Block[]::new);

        if (blocks.length > 0) {
            event.registerBlock(EXTENSIONS, blocks);
        }
    }

    @SubscribeEvent
    public static void onAddClientReloadListeners(final AddClientReloadListenersEvent event) {
        //Sprites and tint sources change with reloads; cached resolved targets must not outlive them.
        event.addListener(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "retexturing_cache"), new SimplePreparableReloadListener<Unit>() {
            @Override
            protected @NonNull Unit prepare(final @NonNull ResourceManager manager, final @NonNull ProfilerFiller profiler) {
                RetexturingHandler.clearCache();
                return Unit.INSTANCE;
            }

            @Override
            protected void apply(final @NonNull Unit preparations, final @NonNull ResourceManager manager, final @NonNull ProfilerFiller profiler) {
            }
        });
    }
}
