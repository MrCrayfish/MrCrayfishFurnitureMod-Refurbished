package com.mrcrayfish.furniture.refurbished.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * Author: MrCrayfish
 */
public interface ICookingBlock
{
    /**
     * @return The holder of the cooking block
     */
    BlockEntity getBlockEntity();

    /**
     * @return True if this cooking block can start to cook
     */
    boolean canCook();

    /**
     * @return The total time in ticks required to complete a cooking cycle
     */
    int getTimeToCook();

    /**
     * A callback method when the cooking starts
     */
    default void onStartCooking() {}

    /**
     * A callback method when cooking stops. This does not necessarily mean it's completed.
     */
    default void onStopCooking() {}

    /**
     * A callback method when cooking is completed
     */
    void onCompleteCooking();

    /**
     * A default implementation of determining if this cooking block is currently
     * cooking. The heating source is the controller of the cooking logic, so to get
     * an accurate state, the result must come from the heating source.
     *
     * @return True if this cooking block is cooking
     */
    default boolean isCooking()
    {
        BlockEntity entity = this.getBlockEntity();
        BlockPos belowPos = entity.getBlockPos().below();
        Level level = entity.getLevel();
        if(level != null && level.getBlockEntity(belowPos) instanceof IHeatingSource source)
        {
            return source.isHeatingAboveBlock();
        }
        return false;
    }
}
