package com.example.examplemod;

import net.minecraftforge.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class ExampleMod
{
    public ExampleMod()
    {
        Constants.LOG.info("Hello Forge world!");
    }
}