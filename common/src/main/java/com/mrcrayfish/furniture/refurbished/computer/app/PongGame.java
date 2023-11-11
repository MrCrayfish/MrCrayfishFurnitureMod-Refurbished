package com.mrcrayfish.furniture.refurbished.computer.app;

import com.mrcrayfish.furniture.refurbished.blockentity.IComputer;
import com.mrcrayfish.furniture.refurbished.computer.Program;
import com.mrcrayfish.furniture.refurbished.inventory.ComputerMenu;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

/**
 * Author: MrCrayfish
 */
public class PongGame extends Program
{
    private final RandomSource source = RandomSource.create();

    public PongGame(ResourceLocation id, IComputer computer)
    {
        super(id, computer);
    }

    @Override
    public void tick()
    {
        if(this.computer.isServer())
        {
            ComputerMenu menu = this.computer.getMenu();
            if(menu != null)
            {
                int x = this.source.nextInt(10);
                int y = this.source.nextInt(10);
                long positions = (((long) x) << 32) | (y & 0xFFFFFFFFL);
                menu.setProgramData(positions);
            }
        }
    }
}
