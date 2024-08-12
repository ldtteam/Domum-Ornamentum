package com.ldtteam.domumornamentum.datagen.loot;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.FancyDoorBlock;
import com.ldtteam.domumornamentum.block.decorative.FancyTrapdoorBlock;
import com.ldtteam.domumornamentum.block.decorative.PanelBlock;
import com.ldtteam.domumornamentum.block.decorative.PostBlock;
import com.ldtteam.domumornamentum.block.vanilla.DoorBlock;
import com.ldtteam.domumornamentum.block.vanilla.TrapdoorBlock;
import com.ldtteam.domumornamentum.component.ModDataComponents;
import com.ldtteam.domumornamentum.shingles.ShingleHeightType;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import java.util.Set;
import java.util.function.UnaryOperator;

/**
 * LootTables for {@link IMateriallyTexturedBlock}s
 */
public class MaterialLootTableProvider extends BlockLootSubProvider
{
    public MaterialLootTableProvider(final HolderLookup.Provider registries)
    {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate()
    {
        dropDoorMateriallyWithProp(ModBlocks.getInstance().getDoor(), DoorBlock.TYPE);
        dropDoorMateriallyWithProp(ModBlocks.getInstance().getFancyDoor(), FancyDoorBlock.TYPE);
        dropSlabMaterially(ModBlocks.getInstance().getSlab());

        dropSelfMaterially(ModBlocks.getInstance().getFence());
        dropSelfMaterially(ModBlocks.getInstance().getFenceGate());
        dropSelfMaterially(ModBlocks.getInstance().getPaperWall());
        dropSelfMaterially(ModBlocks.getInstance().getShingle(ShingleHeightType.DEFAULT));
        dropSelfMaterially(ModBlocks.getInstance().getShingle(ShingleHeightType.FLAT_LOWER));
        dropSelfMaterially(ModBlocks.getInstance().getShingle(ShingleHeightType.FLAT));
        dropSelfMaterially(ModBlocks.getInstance().getShingleSlab());
        dropSelfMaterially(ModBlocks.getInstance().getStair());
        dropSelfMaterially(ModBlocks.getInstance().getWall());

        dropSelfMateriallyWithProp(ModBlocks.getInstance().getFancyTrapdoor(), FancyTrapdoorBlock.TYPE);
        dropSelfMateriallyWithProp(ModBlocks.getInstance().getPanel(), PanelBlock.TYPE);
        dropSelfMateriallyWithProp(ModBlocks.getInstance().getPost(), PostBlock.TYPE);
        dropSelfMateriallyWithProp(ModBlocks.getInstance().getTrapdoor(), TrapdoorBlock.TYPE);

        ModBlocks.getInstance().getAllBrickBlocks().forEach(this::dropSelfMaterially);
        ModBlocks.getInstance().getAllBrickStairBlocks().forEach(this::dropSelfMaterially);
        ModBlocks.getInstance().getFramedLights().forEach(this::dropSelfMaterially);
        ModBlocks.getInstance().getPillars().forEach(this::dropSelfMaterially);
        ModBlocks.getInstance().getTimberFrames().forEach(this::dropSelfMaterially);
    }

    @Override
    protected Iterable<Block> getKnownBlocks()
    {
        return BuiltInRegistries.BLOCK.stream().filter(IMateriallyTexturedBlock.class::isInstance).toList();
    }

    /**
     * Helper method to create default textureData lootTable
     */
    protected void dropSelfMaterially(final Block block, final UnaryOperator<LootPoolSingletonContainer.Builder<?>> itemPoolBuilder)
    {
        add(block,
            LootTable.lootTable()
                .withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(itemPoolBuilder.apply(LootItem.lootTableItem(block)
                        .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                            .include(ModDataComponents.TEXTURE_DATA.get()))))));
    }

    /**
     * Drops block: textureData
     */
    protected void dropSelfMaterially(final Block block)
    {
        dropSelfMaterially(block, UnaryOperator.identity());
    }

    /**
     * Drops block: textureData + given blockState property
     */
    protected void dropSelfMateriallyWithProp(final Block block, final Property<?> property)
    {
        dropSelfMaterially(block, item -> item.apply(CopyBlockState.copyState(block).copy(property)));
    }

    /**
     * Drops door block: textureData + given blockState property
     */
    protected void dropDoorMateriallyWithProp(final net.minecraft.world.level.block.DoorBlock block, final Property<?> property)
    {
        dropSelfMaterially(block,
            item -> item.apply(CopyBlockState.copyState(block).copy(property))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoorBlock.HALF, DoubleBlockHalf.LOWER))));
    }

    /**
     * Drops slab block: textureData
     */
    protected void dropSlabMaterially(final SlabBlock block)
    {
        dropSelfMaterially(block,
            item -> item.apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)))));
    }
}
