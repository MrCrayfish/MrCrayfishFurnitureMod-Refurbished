package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.block.CeilingFanBlock;
import com.mrcrayfish.furniture.refurbished.client.audio.AudioManager;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class CeilingFanBlockEntity extends ElectricityModuleBlockEntity implements IAudioBlock
{
    private static final float MAX_SPEED = 50F;
    private static final float ACCELERATION = 1.25F;
    private static final float RESISTANCE = 0.98F;
    private static final AABB[] DAMAGE_BOXES = Util.make(() -> {
        AABB[] boxes = new AABB[Direction.values().length];
        boxes[Direction.UP.get3DDataValue()] = new AABB(-0.625, 0.5, -0.625, 1.625, 0.625, 1.625);
        boxes[Direction.DOWN.get3DDataValue()] = new AABB(-0.625, 0.375, -0.625, 1.625, 0.5, 1.625);
        boxes[Direction.NORTH.get3DDataValue()] = new AABB(-0.625, -0.625, 0.375, 1.625, 1.625, 0.5);
        boxes[Direction.EAST.get3DDataValue()] = new AABB(0.5, -0.625, -0.625, 0.625, 1.625, 1.625);
        boxes[Direction.SOUTH.get3DDataValue()] = new AABB(-0.625, -0.625, 0.5, 1.625, 1.625, 0.625);
        boxes[Direction.WEST.get3DDataValue()] = new AABB(0.375, -0.625, -0.625, 0.5, 1.625, 1.625);
        return boxes;
    });

    private float speed;
    private float lastRotation;
    private float rotation;

    public CeilingFanBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.CEILING_FAN.get(), pos, state);
    }

    @Override
    public boolean isPowered()
    {
        BlockState state = this.getBlockState();
        return state.hasProperty(BlockStateProperties.POWERED) && state.getValue(BlockStateProperties.POWERED);
    }

    @Override
    public void setPowered(boolean powered)
    {
        BlockState state = this.getBlockState();
        if(state.hasProperty(BlockStateProperties.POWERED))
        {
            this.level.setBlock(this.worldPosition, state.setValue(BlockStateProperties.POWERED, powered), Block.UPDATE_ALL);
        }
    }

    private void updateAnimation()
    {
        this.lastRotation = this.rotation;
        if(this.isPowered())
        {
            this.speed = Math.min(this.speed + ACCELERATION, MAX_SPEED);
        }
        this.speed *= RESISTANCE;
        this.rotation += this.speed;
        if(this.rotation > 360F)
        {
            this.rotation -= 360F;
            this.lastRotation -= 360F;
        }
    }

    public float getRotation(float partialTick)
    {
        return Mth.lerp(partialTick, this.lastRotation, this.rotation);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, CeilingFanBlockEntity ceilingFan)
    {
        ceilingFan.updateAnimation();
        AudioManager.get().playAudioBlock(ceilingFan);
    }

    private void performDamage(Level level)
    {
        if(this.isPowered())
        {
            Direction direction = this.getDirection();
            AABB box = this.getDamageBox(direction).move(this.getBlockPos());
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, box);
            entities.forEach(entity -> entity.hurt(level.damageSources().generic(), 0.5F));
        }
    }

    public AABB getDamageBox(Direction direction)
    {
        return DAMAGE_BOXES[direction.get3DDataValue()];
    }

    public Direction getDirection()
    {
        BlockState state = this.getBlockState();
        if(state.hasProperty(CeilingFanBlock.FACING))
        {
            return state.getValue(CeilingFanBlock.FACING);
        }
        return Direction.NORTH;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, CeilingFanBlockEntity ceilingFan)
    {
        ElectricityModuleBlockEntity.serverTick(level, pos, state, ceilingFan);
        ceilingFan.performDamage(level);
    }

    @Override
    public SoundEvent getSound()
    {
        return ModSounds.BLOCK_CEILING_FAN_SPIN.get();
    }

    @Override
    public BlockPos getAudioPosition()
    {
        return this.worldPosition;
    }

    @Override
    public boolean canPlayAudio()
    {
        return this.speed > 5.0F;
    }

    @Override
    public float getAudioVolume()
    {
        return this.speed / MAX_SPEED;
    }

    @Override
    public float getAudioPitch()
    {
        return 0.5F + this.speed / MAX_SPEED;
    }

    @Override
    public double getAudioRadiusSqr()
    {
        return 16;
    }

    @Override
    public void setLevel(Level level)
    {
        super.setLevel(level);

        // Sets the initial speed on load
        if(level.isClientSide() && this.isPowered())
        {
            this.speed = MAX_SPEED;
        }
    }
}
