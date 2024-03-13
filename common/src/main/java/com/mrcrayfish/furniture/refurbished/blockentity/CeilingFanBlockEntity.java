package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.block.CeilingFanBlock;
import com.mrcrayfish.furniture.refurbished.block.LightswitchBlock;
import com.mrcrayfish.furniture.refurbished.client.audio.AudioManager;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class CeilingFanBlockEntity extends ElectricityModuleBlockEntity implements ILevelAudio
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

    protected final Vec3 audioPosition;
    private float bladeSpeed;
    private float bladeRotation;
    private float lastBladeRotation;
    protected @Nullable Component name;

    public CeilingFanBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.CEILING_FAN.get(), pos, state);
        this.audioPosition = pos.getCenter().add(0, 0.375, 0);
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

    /**
     * Updates the rotation
     */
    private void updateAnimation()
    {
        this.lastBladeRotation = this.bladeRotation;
        if(this.isPowered())
        {
            this.bladeSpeed = Math.min(this.bladeSpeed + ACCELERATION, MAX_SPEED);
        }
        this.bladeSpeed *= RESISTANCE;
        this.bladeRotation += this.bladeSpeed;
        if(this.bladeRotation > 360F)
        {
            this.bladeRotation -= 360F;
            this.lastBladeRotation -= 360F;
        }
    }

    /**
     * Gets the exact rotation of the blades at the given partial tick. This allows the blades
     * to be drawn with a smooth rotation.
     *
     * @param partialTick the current partial tick
     * @return the rotation of the blades
     */
    public float getRotation(float partialTick)
    {
        return Mth.lerp(partialTick, this.lastBladeRotation, this.bladeRotation);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, CeilingFanBlockEntity ceilingFan)
    {
        ceilingFan.updateAnimation();
        AudioManager.get().playLevelAudio(ceilingFan);
    }

    /**
     * Causes damage to entities that are colliding with the blades of the fan. The ceiling fan
     * will not cause any damage if it is not powered.
     *
     * @param level the level containing the ceiling fan
     */
    private void performDamage(Level level)
    {
        if(this.isPowered())
        {
            Direction direction = this.getDirection();
            AABB box = this.getDamageBox(direction).move(this.getBlockPos());
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, box);
            entities.forEach(entity -> {
                entity.hurt(Services.BLOCK.ceilingFanDamageSource(level), 0.5F);
                entity.setLastHurtByMob(entity); // Is this going to be a problem?
            });
        }
    }

    /**
     * Gets the collision box used for determining if an entity can be damaged by the fan
     *
     * @param direction the facing direction of the ceiling fan
     * @return an aabb of the damage box
     */
    public AABB getDamageBox(Direction direction)
    {
        return DAMAGE_BOXES[direction.get3DDataValue()];
    }

    /**
     * @return The direction the ceiling fan is facing
     */
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
    public SoundSource getSource()
    {
        return SoundSource.BLOCKS;
    }

    @Override
    public Vec3 getAudioPosition()
    {
        return this.audioPosition;
    }

    @Override
    public boolean canPlayAudio()
    {
        return this.bladeSpeed > 5.0F && !this.isRemoved();
    }

    @Override
    public float getAudioVolume()
    {
        return this.bladeSpeed / MAX_SPEED;
    }

    @Override
    public float getAudioPitch()
    {
        return 0.5F + this.bladeSpeed / MAX_SPEED;
    }

    @Override
    public int getAudioHash()
    {
        return this.worldPosition.hashCode();
    }

    @Override
    public boolean isAudioEqual(ILevelAudio other)
    {
        return this == other;
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
            this.bladeSpeed = MAX_SPEED;
        }
    }
}
