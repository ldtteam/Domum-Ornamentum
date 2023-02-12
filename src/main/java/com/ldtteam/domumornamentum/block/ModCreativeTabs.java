package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

/**
 * Class used to handle the creativeTab of structurize.
 */
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModCreativeTabs {
    public static CreativeModeTab GENERAL;

    public static CreativeModeTab EXTRA_BLOCKS;

    public static CreativeModeTab FLOATING_CARPETS;

    @SubscribeEvent
    public static void onCreativeModeTab(CreativeModeTabEvent.Register event) {
        GENERAL = event.registerCreativeModeTab(
                new ResourceLocation(Constants.MOD_ID, "general"),
                builder -> {
                    builder.icon(() -> new ItemStack(ModBlocks.getInstance().getArchitectsCutter()))
                            .title(Component.translatable("itemGroup." + Constants.MOD_ID + ".general"))
                            .displayItems(new OutputAwareGenerator((featureFlagSet, output, hasOpPermissions) -> {
                                output.accept(ModBlocks.getInstance().getArchitectsCutter());
                                ModBlocks.getInstance().getTimberFrames().forEach(output::accept);
                                output.accept(ModBlocks.getInstance().getShingle());
                                output.accept(ModBlocks.getInstance().getShingleSlab());
                                output.accept(ModBlocks.getInstance().getPaperWall());
                                ModBlocks.getInstance().getPillars().forEach(output::accept);
                                ModBlocks.getInstance().getFramedLights().forEach(output::accept);
                                output.accept(ModBlocks.getInstance().getFence());
                                output.accept(ModBlocks.getInstance().getFenceGate());
                                output.accept(ModBlocks.getInstance().getSlab());
                                output.accept(ModBlocks.getInstance().getWall());
                                output.accept(ModBlocks.getInstance().getStair());
                                output.accept(ModBlocks.getInstance().getTrapdoor());
                                output.accept(ModBlocks.getInstance().getDoor());
                                output.accept(ModBlocks.getInstance().getPanel());
                                output.accept(ModBlocks.getInstance().getFancyDoor());
                                output.accept(ModBlocks.getInstance().getFancyTrapdoor());
                            }));
                }
        );

        EXTRA_BLOCKS = event.registerCreativeModeTab(
                new ResourceLocation(Constants.MOD_ID, "extra_blocks"),
                builder -> {
                    builder.icon(() -> new ItemStack(ModBlocks.getInstance().getExtraTopBlocks().get(0)))
                            .title(Component.translatable("itemGroup." + Constants.MOD_ID + ".extra-blocks"))
                            .displayItems(new OutputAwareGenerator((featureFlagSet, output, hasOpPermissions) -> {
                                ModBlocks.getInstance().getExtraTopBlocks().forEach(output::accept);
                                ModBlocks.getInstance().getBricks().forEach(output::accept);
                            }));
                }
        );

        FLOATING_CARPETS = event.registerCreativeModeTab(
                new ResourceLocation(Constants.MOD_ID, "floating_carpets"),
                builder -> {
                    builder.icon(() -> new ItemStack(ModBlocks.getInstance().getFloatingCarpets().get(0)))
                            .title(Component.translatable("itemGroup." + Constants.MOD_ID + ".floating-carpets"))
                            .displayItems(new OutputAwareGenerator((featureFlagSet, output, hasOpPermissions) -> {
                                ModBlocks.getInstance().getFloatingCarpets().forEach(output::accept);
                            }));
                }
        );
    }

    private record OutputAwareGenerator(CreativeModeTab.DisplayItemsGenerator delegate) implements CreativeModeTab.DisplayItemsGenerator {

        @Override
        public void accept(@NotNull FeatureFlagSet featureFlagSet, CreativeModeTab.@NotNull Output output, boolean hasPermissions) {
            delegate.accept(featureFlagSet, new Output(output), hasPermissions);
        }

        private record Output(CreativeModeTab.Output delegate) implements CreativeModeTab.Output {

            @Override
            public void accept(@NotNull ItemStack stack, CreativeModeTab.@NotNull TabVisibility visibility) {
                delegate.accept(stack, visibility);
            }

            @Override
            public void accept(@NotNull ItemLike itemLike) {
                delegate.accept(itemLike);
                if (itemLike instanceof ICachedItemGroupBlock cachedItemGroupBlock) {
                    final NonNullList<ItemStack> stacks = NonNullList.create();
                    cachedItemGroupBlock.fillItemCategory(stacks);
                    stacks.forEach(delegate::accept);
                }
            }
        }
    }
}
