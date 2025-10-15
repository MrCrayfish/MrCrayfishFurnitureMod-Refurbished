package com.mrcrayfish.furniture.refurbished.item;

import com.mrcrayfish.furniture.refurbished.electricity.IElectricityNode;
import com.mrcrayfish.furniture.refurbished.electricity.LinkManager;
import com.mrcrayfish.furniture.refurbished.electricity.NodeHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class WrenchItem extends Item
{
    public WrenchItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean canDestroyBlock(ItemStack stack, BlockState state, Level level, BlockPos pos, LivingEntity entity)
    {
        return false;
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand)
    {
        float range = player.isCreative() ? 5.0F : 4.5F;
        NodeHitResult result = pickElectricityNode(level, player, range, 1F);
        if(result.getType() != HitResult.Type.MISS)
        {
            if(level instanceof ServerLevel serverLevel)
            {
                LinkManager.get(serverLevel.getServer()).ifPresent(manager -> {
                    manager.onNodeInteract(level, player, result.getNode());
                });
            }
        }
        return InteractionResult.SUCCESS;
    }

    public static NodeHitResult pickElectricityNode(Level level, Player player, double range, float partialTick)
    {
        Vec3 start = player.getEyePosition(partialTick);
        Vec3 look = player.getViewVector(partialTick);
        Vec3 end = start.add(look.x * range, look.y * range, look.z * range);
        return BlockGetter.traverseBlocks(start, end, null, (o, pos) -> {
            if(level.getBlockEntity(pos) instanceof IElectricityNode found) {
                Optional<Vec3> hit = found.getPositionedNodeInteractBox().inflate(0.0625).clip(start, end);
                if(hit.isPresent()) {
                    return new NodeHitResult(hit.get(), pos, found);
                }
            }
            return null;
        }, o -> new NodeHitResult(end, null, null));
    }
}
