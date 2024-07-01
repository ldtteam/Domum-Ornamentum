package com.ldtteam.domumornamentum;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlockManager;
import com.ldtteam.domumornamentum.block.IModBlocks;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import net.minecraft.core.component.DataComponentType;

import java.util.function.Supplier;

/**
 * The central DO api.
 */
public interface IDomumOrnamentumApi
{
    /**
     * The current API instance.
     * @return The instance.
     */
    static IDomumOrnamentumApi getInstance() {
        return Holder.getInstance();
    }

    /**
     * Gives access to the blocks of the mod.
     * @return The blocks of this mod.
     */
    IModBlocks getBlocks();

    /**
     * The materially texturable block manager.
     * @return The manager
     */
    IMateriallyTexturedBlockManager getMateriallyTexturedBlockManager();

    Supplier<DataComponentType<MaterialTextureData>> getMaterialTextureComponentType();

    class Holder {
        private static IDomumOrnamentumApi apiInstance;

        public static IDomumOrnamentumApi getInstance()
        {
            return apiInstance;
        }

        public static void setInstance(final IDomumOrnamentumApi instance)
        {
            if (apiInstance != null)
                throw new IllegalStateException("Can not setup API twice!");

            apiInstance = instance;
        }
    }
}
