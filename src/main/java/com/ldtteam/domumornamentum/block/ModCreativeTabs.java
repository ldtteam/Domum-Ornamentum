package com.ldtteam.domumornamentum.block;

import com.ldtteam.domumornamentum.shingles.ShingleHeightType;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import static com.ldtteam.domumornamentum.util.Constants.MOD_ID;

/**
 * Class used to handle the creativeTab of structurize.
 */
@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> TAB_REG = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final RegistryObject<CreativeModeTab> GENERAL = TAB_REG.register("general", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModBlocks.getInstance().getArchitectsCutter()))
            .title(Component.translatable("itemGroup." + MOD_ID + ".general"))
            .displayItems(new OutputAwareGenerator((config, output) -> {
        output.accept(ModBlocks.getInstance().getArchitectsCutter());
        ModBlocks.getInstance().getTimberFrames().forEach(output::accept);
        for (ShingleHeightType heightType : ShingleHeightType.values())
        {
            output.accept(ModBlocks.getInstance().getShingle(heightType));
        }

        output.accept(ModBlocks.getInstance().getShingleSlab());
        output.accept(ModBlocks.getInstance().getPaperWall());

        ModBlocks.getInstance().getPillars().forEach(output::accept);
        ModBlocks.getInstance().getFramedLights().forEach(output::accept);
        ModBlocks.getInstance().getAllBrickBlocks().forEach(output::accept);

        output.accept(ModBlocks.getInstance().getFence());
        output.accept(ModBlocks.getInstance().getFenceGate());
        output.accept(ModBlocks.getInstance().getSlab());
        output.accept(ModBlocks.getInstance().getWall());
        output.accept(ModBlocks.getInstance().getStair());
        output.accept(ModBlocks.getInstance().getTrapdoor());
        output.accept(ModBlocks.getInstance().getDoor());
        output.accept(ModBlocks.getInstance().getPanel());
        output.accept(ModBlocks.getInstance().getPost());
        output.accept(ModBlocks.getInstance().getFancyDoor());
        output.accept(ModBlocks.getInstance().getFancyTrapdoor());
    })).build());

    public static final RegistryObject<CreativeModeTab> EXTRA_BLOCKS = TAB_REG.register("extra_blocks", () -> CreativeModeTab.builder()
            .withTabsBefore(GENERAL.getId())
            .icon(() -> new ItemStack(ModBlocks.getInstance().getExtraTopBlocks().get(0)))
            .title(Component.translatable("itemGroup." + MOD_ID + ".extra-blocks"))
            .displayItems(new OutputAwareGenerator((config, output) -> {
        ModBlocks.getInstance().getExtraTopBlocks().forEach(output::accept);
        ModBlocks.getInstance().getBricks().forEach(output::accept);
        output.accept(ModBlocks.getInstance().getStandingBarrel());
        output.accept(ModBlocks.getInstance().getLayingBarrel());
    })).build());

    public static final RegistryObject<CreativeModeTab> FLOATING_CARPETS = TAB_REG.register("floating_carpets", () -> CreativeModeTab.builder()
            .withTabsBefore(EXTRA_BLOCKS.getId())
            .icon(() -> new ItemStack(ModBlocks.getInstance().getFloatingCarpets().get(0)))
            .title(Component.translatable("itemGroup." + MOD_ID + ".floating-carpets"))
            .displayItems(new OutputAwareGenerator((config, output) -> {
        ModBlocks.getInstance().getFloatingCarpets().forEach(output::accept);
    })).build());


    private record OutputAwareGenerator(CreativeModeTab.DisplayItemsGenerator delegate) implements CreativeModeTab.DisplayItemsGenerator {

        @Override
        public void accept(@NotNull CreativeModeTab.ItemDisplayParameters featureFlagSet, @NotNull final CreativeModeTab.Output output) {
            delegate.accept(featureFlagSet, new Output(output));
        }

        private record Output(CreativeModeTab.Output delegate) implements CreativeModeTab.Output {

            @Override
            public void accept(@NotNull ItemStack stack, CreativeModeTab.@NotNull TabVisibility visibility) {
                delegate.accept(stack, visibility);
            }

            @Override
            public void accept(@NotNull ItemLike itemLike) {

                if (itemLike instanceof ICachedItemGroupBlock cachedItemGroupBlock) {
                    final NonNullList<ItemStack> stacks = NonNullList.create();
                    cachedItemGroupBlock.fillItemCategory(stacks);
                    stacks.forEach(delegate::accept);
                }
                else
                {
                    delegate.accept(itemLike);
                }
            }
        }
    }
}
