package com.mrcrayfish.furniture.refurbished.data.model;

import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.Identifier;

/**
 * Author: MrCrayfish
 */
public class ExtraModel extends ParentModel<ExtraModel>
{
    public ExtraModel(String name, Identifier model, TextureSlot[] slots)
    {
        super(name, model, slots);
    }

    @Override
    public ExtraModel self()
    {
        return this;
    }
}
