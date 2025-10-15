package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.mrcrayfish.furniture.refurbished.client.electricity.CachedElectricityNodes;
import com.mrcrayfish.furniture.refurbished.electricity.IElectricityNode;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.Set;

/*
 * When a block entity is added to the level, it is checked if it is an electricity node. If true,
 * the electricity node is then stored into a set. This is used for rendering the electricity overlay
 * when the player is holding the wrench.
 */
@Mixin(ClientLevel.class)
public class ClientLevelMixin implements CachedElectricityNodes
{
    @Unique
    private final Set<IElectricityNode> refurbishedFurniture$electricityNodes = new ReferenceOpenHashSet<>();

    @Inject(method = "onBlockEntityAdded", at = @At(value = "TAIL"))
    private void refurbishedFurnitureBlockEntityAdded(BlockEntity entity, CallbackInfo ci)
    {
        if(entity instanceof IElectricityNode node)
        {
            this.refurbishedFurniture$electricityNodes.add(node);
        }
    }

    @Override
    public Set<IElectricityNode> refurbishedFurniture$ElectricityNodes()
    {
        return Collections.unmodifiableSet(this.refurbishedFurniture$electricityNodes);
    }

    @Override
    public void refurbishedFurniture$RemoveInvalidElectricityNodes()
    {
        this.refurbishedFurniture$electricityNodes.removeIf(node -> !node.isNodeValid());
    }
}
