package com.ldtteam.domumornamentum.entity.block;

import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.client.model.properties.ModProperties;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.common.util.Constants;
import org.jetbrains.annotations.NotNull;

public class MateriallyTexturedBlockEntity extends TileEntity
{

    private MaterialTextureData textureData = MaterialTextureData.EMPTY;

    public MateriallyTexturedBlockEntity(final TileEntityType<?> tileEntityTypeIn)
    {
        super(tileEntityTypeIn);
    }

    public void updateTextureDataWith(final MaterialTextureData materialTextureData)
    {
        this.textureData = materialTextureData;
        this.requestModelDataUpdate();
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        return save(new CompoundNBT());
    }

    @NotNull
    @Override
    public CompoundNBT save(@NotNull final CompoundNBT compound)
    {
        final CompoundNBT superData = super.save(compound);

        superData.put("textureData", textureData.serializeNBT());

        return superData;
    }

    @Override
    public void load(@NotNull final BlockState state, @NotNull final CompoundNBT nbt)
    {
        super.load(state, nbt);

        this.textureData = new MaterialTextureData();
        if (nbt.contains("textureData", Constants.NBT.TAG_COMPOUND))
        {
            this.textureData.deserializeNBT(nbt.getCompound("textureData"));
        }

        this.requestModelDataUpdate();
    }

    @NotNull
    @Override
    public IModelData getModelData()
    {
        return new ModelDataMap.Builder()
          .withInitial(ModProperties.MATERIAL_TEXTURE_PROPERTY, this.textureData)
          .build();
    }


}
