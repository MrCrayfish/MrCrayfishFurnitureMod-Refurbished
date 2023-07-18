package com.mrcrayfish.furniture.refurbished.entity;

import com.mrcrayfish.furniture.refurbished.core.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Author: MrCrayfish
 */
public class Seat extends Entity
{
    public Seat(Level level)
    {
        super(ModEntities.SEAT.get(), level);
    }

    private Seat(Level level, BlockPos pos, double seatHeight, float seatYaw)
    {
        this(level);
        this.setPos(Vec3.atBottomCenterOf(pos).add(0, seatHeight, 0));
        this.setRot(seatYaw, 0);
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {}

    @Override
    public void tick()
    {
        super.tick();
        Level level = this.level();
        if(!level.isClientSide())
        {
            BlockPos pos = this.blockPosition();
            if(this.getPassengers().isEmpty() || level.isEmptyBlock(pos))
            {
                this.discard();
                level.updateNeighbourForOutputSignal(pos, level.getBlockState(pos).getBlock());
            }
        }
    }

    @Override
    protected void addPassenger(Entity entity)
    {
        super.addPassenger(entity);
        entity.setYRot(this.getYRot());
    }

    @Override
    public void onPassengerTurned(Entity entity)
    {
        this.clampPassengerYaw(entity);
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity entity)
    {
        Direction front = this.getDirection();
        Direction[] sides = {front, front.getClockWise(), front.getCounterClockWise(), front.getOpposite()};
        for(Direction side : sides)
        {
            Vec3 pos = DismountHelper.findSafeDismountLocation(entity.getType(), this.level(), this.blockPosition().relative(side), false);
            if(pos != null)
            {
                return pos.add(0, 0.25, 0);
            }
        }
        return super.getDismountLocationForPassenger(entity);
    }

    private void clampPassengerYaw(Entity entity)
    {
        entity.setYBodyRot(this.getYRot());
        float wrappedYaw = Mth.wrapDegrees(entity.getYRot() - this.getYRot());
        float clampedYaw = Mth.clamp(wrappedYaw, -120, 120);
        entity.yRotO += clampedYaw - wrappedYaw;
        entity.setYRot(entity.getYRot() + clampedYaw - wrappedYaw);
        entity.setYHeadRot(entity.getYRot());
    }

    /**
     * Attempts to create a seat at the provided block position and sit the player on it. The position
     * of the seat begins from the bottom center of the block position and the height is offset by
     * the provided seat height. Only one seat can exist at any given block position, as such this
     * method will fail if a seat already exists at the provided block position. A seat only exists
     * in the level if the seat is being ridden by an entity, otherwise it immediately despawns after
     * the entity dismounts.
     *
     * @param player     the player to start riding the seat
     * @param pos        the block position of the seat
     * @param seatHeight the height offset from the bottom the block position
     * @param direction  the facing direction of the seat.
     * @return True if the player successfully starts riding the seat
     */
    public static boolean sit(Player player, BlockPos pos, double seatHeight, Direction direction)
    {
        Level level = player.level();
        if(!level.isClientSide() && availableAt(level, pos))
        {
            Seat seat = new Seat(level, pos, seatHeight, direction.toYRot());
            level.addFreshEntity(seat);
            return player.startRiding(seat);
        }
        return false;
    }

    /**
     * Determines if it is possible to create a seat at the given block position. A seat can be
     * spawned if no seat exists at the provided block position.
     *
     * @param level the level of the position
     * @param pos   the block position to test
     * @return True if a seat can be spawned
     */
    public static boolean availableAt(Level level, BlockPos pos)
    {
        return level.getEntitiesOfClass(Seat.class, new AABB(pos)).isEmpty();
    }
}
