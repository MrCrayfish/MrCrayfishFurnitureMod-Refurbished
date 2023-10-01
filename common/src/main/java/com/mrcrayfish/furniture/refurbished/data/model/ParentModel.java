package com.mrcrayfish.furniture.refurbished.data.model;

import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public abstract class ParentModel<T extends ParentModel<T>>
{
    protected final String name;
    protected final ResourceLocation model;
    protected final TextureSlot[] slots;
    protected TextureMapping textures = new TextureMapping();

    public ParentModel(String name, ResourceLocation model, TextureSlot[] slots)
    {
        this.name = name;
        this.model = model;
        this.slots = slots;
    }

    public abstract T self();

    public String getName()
    {
        return this.name;
    }

    public ResourceLocation getModel()
    {
        return this.model;
    }

    public TextureSlot[] getSlots()
    {
        return this.slots;
    }

    public T setTexture(TextureSlot slot, ResourceLocation texture)
    {
        this.textures.put(slot, texture);
        return this.self();
    }

    public T setTextures(TextureMapping mapping)
    {
        this.textures = mapping;
        return this.self();
    }

    public TextureMapping getTextures()
    {
        return this.textures;
    }

    public ModelTemplate asTemplate()
    {
        return new ModelTemplate(Optional.of(this.model), Optional.empty(), this.slots);
    }
}
