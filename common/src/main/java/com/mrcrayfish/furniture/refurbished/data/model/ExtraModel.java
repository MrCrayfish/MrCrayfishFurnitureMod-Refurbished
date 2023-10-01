package com.mrcrayfish.furniture.refurbished.data.model;

import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public class ExtraModel extends ParentModel<ExtraModel>
{
    public ExtraModel(String name, ResourceLocation model, TextureSlot[] slots)
    {
        super(name, model, slots);
    }

    @Override
    public ExtraModel self()
    {
        return this;
    }
}
