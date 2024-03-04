package com.mrcrayfish.furniture.refurbished;

import com.mrcrayfish.furniture.refurbished.blockentity.CuttingBoardBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.StorageJarBlockEntity;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class NeoForgeEvents
{
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
        if(event.getItemStack().is(ModItems.WRENCH.get()))
        {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event)
    {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getEntity();

        if(!player.isCreative())
            return;

        if(player.isCrouching())
            return;

        if(level.getBlockEntity(pos) instanceof StorageJarBlockEntity storageJar && !storageJar.isEmpty())
        {
            if(!level.isClientSide())
            {
                storageJar.popItem(player.getDirection().getOpposite());
            }
            event.setCanceled(true);
        }
        else if(level.getBlockEntity(pos) instanceof CuttingBoardBlockEntity cuttingBoard && !cuttingBoard.isEmpty())
        {
            if(!level.isClientSide())
            {
                cuttingBoard.removeItem();
            }
            event.setCanceled(true);
        }
    }
}
