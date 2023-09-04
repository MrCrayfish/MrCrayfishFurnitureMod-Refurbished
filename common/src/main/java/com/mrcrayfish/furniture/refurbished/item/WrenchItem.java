package com.mrcrayfish.furniture.refurbished.item;

import com.mrcrayfish.furniture.refurbished.electric.IElectricNode;
import com.mrcrayfish.furniture.refurbished.electric.LinkManager;
import com.mrcrayfish.furniture.refurbished.electric.NodeHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
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
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player)
    {
        return false;
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        Optional<NodeHitResult> optional = this.performRaycast(level, player);
        if(optional.isPresent())
        {
            if(!level.isClientSide() && level instanceof ServerLevel serverLevel)
            {
                NodeHitResult result = optional.get();
                LinkManager.get(serverLevel.getServer()).ifPresent(manager -> {
                    manager.onNodeInteract(level, player, result.getNode(), result.getLocation(), result.getPos());
                });
            }
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
        }
        return InteractionResultHolder.fail(player.getItemInHand(hand));
    }

    private Optional<NodeHitResult> performRaycast(Level level, Player player)
    {
        Vec3 start = player.getEyePosition(1F);
        Vec3 look = player.getViewVector(1F);
        Vec3 end = start.add(look.x * 10, look.y * 10, look.z * 10);
        NodeHitResult result = BlockGetter.traverseBlocks(start, end, null, (o, pos) -> {
            if(level.getBlockEntity(pos) instanceof IElectricNode found) {
                Optional<Vec3> hit = found.getInteractBox().clip(start, end);
                if(hit.isPresent()) {
                    return new NodeHitResult(hit.get(), pos, found);
                }
            }
            return null;
        }, o -> new NodeHitResult(end, null, null));
        return result.getType() != HitResult.Type.MISS ? Optional.of(result) : Optional.empty();
    }
}
