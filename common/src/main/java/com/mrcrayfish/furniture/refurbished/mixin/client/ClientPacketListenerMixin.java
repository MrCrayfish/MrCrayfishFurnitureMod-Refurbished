package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.mrcrayfish.furniture.refurbished.client.ClientRecipes;
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

/**
 * Author: MrCrayfish
 */
@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin implements ClientRecipes.Access
{
    @Unique
    private ClientRecipes refurbished_furniture$syncedRecipes;

    @Unique
    @Override
    public ClientRecipes refurbished_furniture$clientRecipes()
    {
        if(this.refurbished_furniture$syncedRecipes == null)
        {
            this.refurbished_furniture$syncedRecipes = new ClientRecipes();
        }
        return this.refurbished_furniture$syncedRecipes;
    }
}
