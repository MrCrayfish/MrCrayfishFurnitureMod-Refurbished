package com.mrcrayfish.furniture.refurbished.mixin;

import com.mrcrayfish.furniture.refurbished.electricity.LinkManager;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

/**
 * Author: MrCrayfish
 */
@Mixin(ServerLevel.class)
public class ServerLevelMixin implements LinkManager.Access
{
    @Unique
    private LinkManager refurbishedFurniture$linkManager;

    @Override
    public LinkManager refurbishedFurniture$GetLinkManager()
    {
        if(this.refurbishedFurniture$linkManager == null)
        {
            this.refurbishedFurniture$linkManager = new LinkManager();
        }
        return this.refurbishedFurniture$linkManager;
    }
}
