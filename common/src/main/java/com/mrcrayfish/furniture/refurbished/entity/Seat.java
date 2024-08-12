package com.mrcrayfish.furniture.refurbished.entity;

import com.mrcrayfish.framework.api.sync.Serializers;
import com.mrcrayfish.framework.api.sync.SyncedClassKey;
import com.mrcrayfish.framework.api.sync.SyncedDataKey;
import com.mrcrayfish.furniture.refurbished.core.ModEntities;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class Seat extends Entity
{
    public static final SyncedClassKey<Seat> SEAT = new SyncedClassKey<>(Seat.class, Utils.resource("seat"));
    public static final SyncedDataKey<Seat, Boolean> LOCK_YAW = SyncedDataKey.builder(SEAT, Serializers.BOOLEAN)
            .id(Utils.resource("lock_yaw"))
            .defaultValueSupplier(() -> false)
            .syncMode(SyncedDataKey.SyncMode.TRACKING_ONLY)
            .saveToFile()
            .build();

    public Seat(Level level)
    {
        super(ModEntities.SEAT.get(), level);
    }

    private Seat(Level level, BlockPos pos, double seatHeight, float seatYaw, boolean lockYaw)
    {
        this(level);
        this.setPos(Vec3.atBottomCenterOf(pos).add(0, seatHeight, 0));
        this.setRot(seatYaw, 0);
        LOCK_YAW.setValue(this, lockYaw);
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {}

    @Override
    public Packet<?> getAddEntityPacket()
    {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public void tick()
    {
        super.tick();
        Level level = this.getLevel();
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
        Direction front = LOCK_YAW.getValue(this) ? this.getDirection() : entity.getDirection();
        Direction[] sides = {front, front.getClockWise(), front.getCounterClockWise(), front.getOpposite()};
        for(Direction side : sides)
        {
            Vec3 pos = DismountHelper.findSafeDismountLocation(entity.getType(), this.getLevel(), this.blockPosition().relative(side), false);
            if(pos != null)
            {
                return pos.add(0, 0.25, 0);
            }
        }
        return super.getDismountLocationForPassenger(entity);
    }

    private void clampPassengerYaw(Entity entity)
    {
        if(LOCK_YAW.getValue(this))
        {
            entity.setYBodyRot(this.getYRot());
            float wrappedYaw = Mth.wrapDegrees(entity.getYRot() - this.getYRot());
            float clampedYaw = Mth.clamp(wrappedYaw, -120, 120);
            entity.yRotO += clampedYaw - wrappedYaw;
            entity.setYRot(entity.getYRot() + clampedYaw - wrappedYaw);
            entity.setYHeadRot(entity.getYRot());
        }
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
    public static boolean sit(Player player, BlockPos pos, double seatHeight, @Nullable Direction direction)
    {
        Level level = player.getLevel();
        if(!level.isClientSide() && availableAt(level, pos))
        {
            float seatYaw = direction != null ? direction.toYRot() : player.getYRot();
            Seat seat = new Seat(level, pos, seatHeight, seatYaw, direction != null);
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
