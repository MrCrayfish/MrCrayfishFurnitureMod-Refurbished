package com.mrcrayfish.furniture.refurbished.client;

import com.mrcrayfish.furniture.refurbished.platform.Services;
import net.minecraft.world.inventory.RecipeBookType;

/**
 * Author: MrCrayfish
 */
public class RecipeBookTypeHolder
{
    private final String constantName;
    private RecipeBookType type;

    public RecipeBookTypeHolder(String constantName)
    {
        this.constantName = constantName;
    }

    public String getConstantName()
    {
        return this.constantName;
    }

    /**
     * @return The custom enum constant. Don't call too early!
     */
    public RecipeBookType get()
    {
        if(this.type == null)
        {
            // Fabric is not supported due to inability to register custom enums
            if(!Services.PLATFORM.getPlatform().isFabric())
            {
                this.type = RecipeBookType.valueOf(this.constantName);
            }
            else
            {
                this.type = RecipeBookType.CRAFTING;
            }
        }
        return this.type;
    }
}
