package com.mrcrayfish.furniture.refurbished;

import com.mrcrayfish.framework.api.event.TickEvents;
import com.mrcrayfish.furniture.refurbished.blockentity.CuttingBoardBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.GrillBlockEntity;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.item.PackageItem;
import com.mrcrayfish.furniture.refurbished.mail.DeliveryService;
import com.mrcrayfish.furniture.refurbished.network.Network;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;

/**
 * Author: MrCrayfish
 */
public class Bootstrap
{
    public static void init()
    {
        Network.init();

        // Allows a spatula in a dispenser to flip items on the grill
        DispenserBlock.registerBehavior(ModItems.SPATULA::get, (source, stack) -> {
            Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
            BlockPos pos = source.getPos().relative(direction).below();
            if(source.getLevel().getBlockEntity(pos) instanceof GrillBlockEntity grill) {
                grill.flipItems();
            }
            return stack;
        });

        // Allows a knife in a dispenser to slice items on the cutting board
        DispenserBlock.registerBehavior(ModItems.KNIFE::get, (source, stack) -> {
            Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
            BlockPos pos = source.getPos().relative(direction);
            if(source.getLevel().getBlockEntity(pos) instanceof CuttingBoardBlockEntity cuttingBoard) {
                if(cuttingBoard.sliceItem(false)) {
                    if(stack.hurt(1, source.getLevel().random, null)) {
                        stack.setCount(0);
                    }
                }
            }
            return stack;
        });

        // Spawns the items contained in a package into the level
        DispenserBlock.registerBehavior(ModItems.PACKAGE::get, (source, stack) -> {
            Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
            Vec3 pos = source.getPos().relative(direction).getCenter();
            PackageItem.getPackagedItems(stack).forEach(s -> {
                Containers.dropItemStack(source.getLevel(), pos.x, pos.y, pos.z, s);
            });
            return ItemStack.EMPTY;
        });

        TickEvents.START_SERVER.register(server -> DeliveryService.get(server).ifPresent(DeliveryService::serverTick));
    }
}
