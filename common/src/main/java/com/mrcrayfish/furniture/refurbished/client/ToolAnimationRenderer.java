package com.mrcrayfish.furniture.refurbished.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class ToolAnimationRenderer
{
    private static ToolAnimationRenderer instance;

    public static ToolAnimationRenderer get()
    {
        if(instance == null)
        {
            instance = new ToolAnimationRenderer();
        }
        return instance;
    }

    private final Map<BlockPos, Animation> animationMap = new HashMap<>();

    private ToolAnimationRenderer() {}

    /**
     * Updates the animations and removes any that have finished
     */
    public void tick()
    {
        this.animationMap.entrySet().removeIf(entry -> {
            entry.getValue().tick();
            return entry.getValue().isFinished();
        });
    }

    /**
     * Renders all active animations in the level
     *
     * @param level the current level
     * @param partialTick the current partial tick
     */
    public void submit(Level level, Vec3 camera, float partialTick)
    {
        PoseStack poseStack = new PoseStack();
        poseStack.translate(-camera.x, -camera.y, -camera.z);
        FeatureRenderDispatcher renderDispatcher = Minecraft.getInstance().gameRenderer.getFeatureRenderDispatcher();
        SubmitNodeStorage storage = renderDispatcher.getSubmitNodeStorage();
        this.animationMap.forEach((pos, animation) -> {
            int light = LevelRenderer.getLightCoords(level, animation.pos);
            animation.submit(poseStack, storage, light, partialTick);
        });
    }

    /**
     * Plays the spatula animation
     *
     * @param dispenserPos the block position of the dispenser that activated the tool
     * @param direction    the facing direction of the dispenser
     */
    public void playSpatulaAnimation(BlockPos dispenserPos, Direction direction)
    {
        this.animationMap.put(dispenserPos, new Animation(Tool.SPATULA, dispenserPos.relative(direction), direction));
    }

    /**
     * Plays the knife animation
     *
     * @param dispenserPos the block position of the dispenser that activated the tool
     * @param direction    the facing direction of the dispenser
     */
    public void playKnifeAnimation(BlockPos dispenserPos, Direction direction)
    {
        this.animationMap.put(dispenserPos, new Animation(Tool.KNIFE, dispenserPos.relative(direction), direction));
    }

    private static class Animation
    {
        private final ItemStackRenderState state;
        private final Tool tool;
        private final BlockPos pos;
        private final Direction direction;
        private int time;

        public Animation(Tool tool, BlockPos pos, Direction direction)
        {
            Minecraft mc = Minecraft.getInstance();
            ItemStackRenderState state = new ItemStackRenderState();
            mc.getItemModelResolver().updateForTopItem(state, tool.stack.get(), ItemDisplayContext.NONE, mc.level, null, 0);
            this.state = state;
            this.tool = tool;
            this.pos = pos;
            this.direction = direction;
        }

        /**
         * @return True if the animation has finished playing
         */
        private boolean isFinished()
        {
            return this.time >= this.tool.animationTime;
        }

        /**
         * Updates the animation time
         */
        private void tick()
        {
            if(this.time < this.tool.animationTime)
            {
                this.time++;
            }
        }

        /**
         * Renders the tool animation in the level
         *
         * @param poseStack the current pose stack
         * @param light the light level to apply to models
         * @param partialTick the current partial tick
         */
        public void submit(PoseStack poseStack, SubmitNodeCollector collector, int light, float partialTick)
        {
            poseStack.pushPose();
            poseStack.translate(this.pos.getX() + 0.5, this.pos.getY(), this.pos.getZ() + 0.5);
            poseStack.mulPose(Axis.YP.rotation(-Mth.HALF_PI * this.direction.get2DDataValue()));
            this.tool.transform.accept(poseStack, this.time + partialTick);
            this.state.submit(poseStack, collector, light, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        }
    }

    private enum Tool
    {
        SPATULA(() -> new ItemStack(ModItems.SPATULA.get()), 6, (poseStack, time) -> {
            poseStack.translate(0, 0.375, 0);
            poseStack.translate(-0.5, 0, -0.5);
            poseStack.mulPose(Axis.XP.rotation(Mth.HALF_PI / 2));
            if(time >= 2) poseStack.mulPose(Axis.XN.rotation((Mth.HALF_PI / 2) * ((time - 2) / 2.0F)));
            poseStack.translate(0.5, 0, 0.5);
            poseStack.translate(0, 0, -0.5 + Mth.sin(time / 6F) * 0.375);
            poseStack.mulPose(Axis.XP.rotation(Mth.HALF_PI));
            poseStack.mulPose(Axis.ZP.rotation(Mth.HALF_PI / 2));
            poseStack.scale(0.75F, 0.75F, 0.75F);
        }),
        KNIFE(() -> new ItemStack(ModItems.KNIFE.get()), 4, (poseStack, time) -> {
            poseStack.translate(0, 0.375, 0);
            poseStack.translate(-0.5, 0, -0.5);
            if(time >= 2) poseStack.mulPose(Axis.XP.rotation((Mth.HALF_PI / 2) * ((time - 2) / 2.0F)));
            poseStack.translate(0.5, 0, 0.5);
            poseStack.translate(0, 0, -0.5 + time / 4F * 0.375);
            poseStack.mulPose(Axis.XP.rotation(Mth.HALF_PI));
            poseStack.mulPose(Axis.YN.rotation(Mth.HALF_PI));
            poseStack.mulPose(Axis.ZP.rotation(Mth.HALF_PI / 2));
            poseStack.scale(0.75F, 0.75F, 0.75F);
        });

        private final Supplier<ItemStack> stack;
        private final int animationTime;
        private final BiConsumer<PoseStack, Float> transform;

        Tool(Supplier<ItemStack> stack, int animationTime, BiConsumer<PoseStack, Float> transform)
        {
            this.stack = stack;
            this.animationTime = animationTime;
            this.transform = transform;
        }
    }
}
