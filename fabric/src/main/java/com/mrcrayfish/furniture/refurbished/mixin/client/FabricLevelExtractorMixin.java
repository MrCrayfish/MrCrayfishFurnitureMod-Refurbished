package com.mrcrayfish.furniture.refurbished.mixin.client;

import com.mrcrayfish.furniture.refurbished.client.electricity.ElectricityRenderer;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.extract.LevelExtractor;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// MC 26.2 moved the per-frame camera-dependent extraction phase (including block outline
// extraction) out of LevelRenderer entirely into this new LevelExtractor class.
@Mixin(LevelExtractor.class)
public class FabricLevelExtractorMixin
{
    @Inject(method = "extract", at = @At(value = "HEAD"))
    private void refurbished_furniture$Extract(DeltaTracker deltaTracker, Camera camera, float partialTick, CallbackInfo ci)
    {
        ElectricityRenderer.get().extract(camera);
    }

    // Prevents the block outline from rendering while the player is holding a wrench
    @Inject(method = "extractBlockOutline", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;"), cancellable = true)
    private void refurbished_furniture$BeforeBlockOutline(Camera camera, LevelRenderState levelRenderState, CallbackInfo ci)
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc.player != null)
        {
            ItemStack stack = mc.player.getItemInHand(InteractionHand.MAIN_HAND);
            if(stack.is(ModItems.WRENCH.get()))
            {
                ci.cancel();
            }
        }
    }
}
