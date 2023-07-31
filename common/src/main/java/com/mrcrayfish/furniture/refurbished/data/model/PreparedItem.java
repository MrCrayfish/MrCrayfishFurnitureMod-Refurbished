package com.mrcrayfish.furniture.refurbished.data.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class PreparedItem
{
    private final Item item;
    private Model model;

    public PreparedItem(Item item)
    {
        this.item = item;
    }

    public Item getItem()
    {
        return this.item;
    }

    public void setModel(Model model)
    {
        this.model = model;
    }

    public Model getModel()
    {
        return this.model;
    }

    public static class Model extends ParentModel implements Supplier<JsonElement>
    {
        private Model(String name, ResourceLocation model, TextureSlot[] slots)
        {
            super(name, model, slots);
        }

        public static Model create(String name, ResourceLocation model, TextureSlot[] slots)
        {
            return new Model(name, model, slots);
        }

        @Override
        public PreparedItem.Model setTexture(TextureSlot slot, ResourceLocation texture)
        {
            return (PreparedItem.Model) super.setTexture(slot, texture);
        }

        @Override
        public PreparedItem.Model setTextures(TextureMapping mapping)
        {
            return (PreparedItem.Model) super.setTextures(mapping);
        }

        @Override
        public JsonElement get()
        {
            JsonObject object = new JsonObject();
            object.addProperty("parent", this.model.toString());
            JsonObject textures = new JsonObject();
            for(TextureSlot slot : this.slots)
            {
                textures.addProperty(slot.getId(), this.textures.get(slot).toString());
            }
            object.add("textures", textures);
            return object;
        }
    }
}
