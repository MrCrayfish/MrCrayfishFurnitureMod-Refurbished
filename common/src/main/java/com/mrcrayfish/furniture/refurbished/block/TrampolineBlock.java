package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.framework.api.network.LevelLocation;
import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.core.ModParticleTypes;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import com.mrcrayfish.furniture.refurbished.network.Network;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class TrampolineBlock extends FurnitureBlock implements BlockTagSupplier
{
    private static final MapCodec<TrampolineBlock> CODEC = RecordCodecBuilder.mapCodec(builder -> {
        return builder.group(DyeColor.CODEC.fieldOf("color").forGetter(block -> {
            return block.color;
        }), propertiesCodec()).apply(builder, TrampolineBlock::new);
    });

    public static final EnumProperty<Shape> SHAPE = EnumProperty.create("shape", Shape.class);

    private final DyeColor color;

    public TrampolineBlock(DyeColor color, Properties properties)
    {
        super(properties);
        this.color = color;
    }

    public DyeColor getDyeColor()
    {
        return this.color;
    }

    @Override
    protected MapCodec<TrampolineBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape topShape = Block.box(0, 10, 0, 16, 13, 16);
        VoxelShape bottomLeftSupportShortShape = Block.box(1, 0, 1, 3, 2, 15);
        VoxelShape bottomLeftSupportLongShape = Block.box(1, 0, 0, 3, 2, 16);
        VoxelShape bottomLeftSupportNorthShape = Block.box(1, 0, 0, 3, 2, 15);
        VoxelShape bottomLeftSupportSouthShape = Block.box(1, 0, 1, 3, 2, 16);
        VoxelShape backLeftLegShape = Block.box(1, 2, 1, 3, 10, 3);
        VoxelShape frontLeftLegShape = Block.box(1, 2, 13, 3, 10, 15);
        VoxelShape bottomRightSupportShortShape = Block.box(13, 0, 1, 15, 2, 15);
        VoxelShape bottomRightSupportLongShape = Block.box(13, 0, 0, 15, 2, 16);
        VoxelShape bottomRightSupportNorthShape = Block.box(13, 0, 0, 15, 2, 15);
        VoxelShape bottomRightSupportSouthShape = Block.box(13, 0, 1, 15, 2, 16);
        VoxelShape frontRightLegShape = Block.box(13, 2, 13, 15, 10, 15);
        VoxelShape backRightLegShape = Block.box(13, 2, 1, 15, 10, 3);
        VoxelShape northWestCornerSupportShape = Block.box(1, 0, 0, 3, 2, 3);
        VoxelShape northEastCornerSupportShape = Block.box(13, 0, 0, 15, 2, 3);
        VoxelShape southEastCornerSupportShape = Block.box(13, 0, 13, 15, 2, 16);
        VoxelShape southWestCornerSupportShape = Block.box(1, 0, 13, 3, 2, 16);
        return ImmutableMap.copyOf(states.stream().collect(Collectors.toMap(state -> state, o -> topShape)));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context)
    {
        return this.shapes.get(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.getTrampolineState(this.defaultBlockState(), context.getLevel(), context.getClickedPos());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos newPos)
    {
        return this.getTrampolineState(state, level, pos);
    }

    /**
     * Determines the state of a trampoline if it were to be placed at the given block position.
     * The trampoline model connects to surrounding trampolines, which affects the shape. The shape
     * determines how "bouncy" the trampoline is. This method checks if the block neighboring the
     * given position are trampolines, and also if the trampoline needs to draw the corner legs.
     *
     * @param state any block state of the trampoline
     * @param level the level where the trampoline exists or will after placing
     * @param pos   the block position of the trampoline or where it's going to be placed
     * @return an updated trampoline blockstate
     */
    private BlockState getTrampolineState(BlockState state, LevelAccessor level, BlockPos pos)
    {
        boolean connectedNorth = this.isTrampoline(level, pos.north());
        boolean connectedEast = this.isTrampoline(level, pos.east());
        boolean connectedSouth = this.isTrampoline(level, pos.south());
        boolean connectedWest = this.isTrampoline(level, pos.west());
        boolean legNorthWest = connectedNorth && connectedWest && !this.isTrampoline(level, pos.north().west());
        boolean legNorthEast = connectedNorth && connectedEast && !this.isTrampoline(level, pos.north().east());
        boolean legSouthEast = connectedSouth && connectedEast && !this.isTrampoline(level, pos.south().east());
        boolean legSouthWest = connectedSouth && connectedWest && !this.isTrampoline(level, pos.south().west());
        int packedValue = Shape.createPackedValue(connectedNorth, connectedEast, connectedSouth, connectedWest, legNorthWest, legNorthEast, legSouthEast, legSouthWest);
        Shape shape = Shape.fromPackedValue(packedValue);
        return state.setValue(SHAPE, shape);
    }

    /**
     * Tests if the block at the given block position in the level is a trampoline
     *
     * @param level a level instance
     * @param pos   the block position to check
     * @return true if a trampoline at the block position
     */
    private boolean isTrampoline(LevelAccessor level, BlockPos pos)
    {
        return level.getBlockState(pos).getBlock() instanceof TrampolineBlock;
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance)
    {
        if(entity.isSuppressingBounce())
            return;

        Vec3 movement = entity.getDeltaMovement();
        if(movement.y > 0)
            return;

        float bounceForce = 2.0F;
        float maxBounceHeight = Config.SERVER.trampoline.maxBounceHeight.get().floatValue() * state.getValue(SHAPE).bounceScale * 0.75F;
        float bounceHeight = Math.min(entity.fallDistance * bounceForce, maxBounceHeight - 0.25F);
        entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.5, 0, 1.5));
        entity.push(0, Math.sqrt(0.22 * (bounceHeight + 0.25F)), 0);
        entity.resetFallDistance();
        this.spawnBounceParticle(level, entity, pos, false);
        if(!level.isClientSide())
        {
            level.playSound(null, pos, ModSounds.BLOCK_TRAMPOLINE_BOUNCE.get(), SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.2F + 0.9F);
        }
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter getter, Entity entity)
    {
        Vec3 velocity = entity.getDeltaMovement();
        if(velocity.y < 0)
        {
            // Special case for boats since they don't trigger the above method
            if(entity instanceof Boat boat && -velocity.y > 0.1)
            {
                this.bounceBoat(boat, velocity);
                return;
            }
            super.updateEntityAfterFallOn(getter, entity);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(SHAPE);
    }

    @Override
    public List<TagKey<Block>> getTags()
    {
        return List.of(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL);
    }

    /**
     * Determines the jump factor of the trampoline with more fine control, including the level,
     * blockstate and block position of the trampoline.
     *
     * @param level the current level
     * @param state the block state of the trampoline
     * @param pos   the block position of the trampoline
     * @return a factor that modifies the jump height of an entity
     */
    public float getJumpModifier(Level level, BlockState state, BlockPos pos)
    {
        Shape shape = state.getValue(SHAPE);
        float bounceHeight = Config.SERVER.trampoline.maxBounceHeight.get().floatValue() * shape.bounceScale;
        return Mth.sqrt(0.22F * bounceHeight) * 2.3F;
    }

    /**
     * Called after an entity living performs a jump on the trampoline.
     *
     * @param entity the entity that just jumped
     * @param level the level of the entity
     * @param state the blockstate of the trampoline
     * @param pos the block position of the trampoline
     */
    public void onLivingEntityJump(LivingEntity entity, Level level, BlockState state, BlockPos pos)
    {
        if(level.isClientSide())
        {
            level.playLocalSound(pos, ModSounds.BLOCK_TRAMPOLINE_SUPER_BOUNCE.get(), SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.1F + 0.75F, false);
            this.spawnBounceParticle(level, entity, pos, true);
        }
    }

    /**
     * Spawns particles at the position of the given entity for indicating a bounce. The particle
     * will be different if it was a super bounce.
     *
     * @param level          the level containing the trampoline
     * @param bouncingEntity the entity who is bouncing
     * @param pos
     * @param superBounce    true if should be a super bounce particle
     */
    private void spawnBounceParticle(Level level, Entity bouncingEntity, BlockPos pos, boolean superBounce)
    {
        if(!level.isClientSide())
        {
            // Special case because animals don't trigger client side
            if(!(bouncingEntity instanceof Player) && bouncingEntity instanceof LivingEntity)
            {
                ParticleOptions particle = superBounce ? ModParticleTypes.SUPER_BOUNCE.get() : ModParticleTypes.BOUNCE.get();
                Vec3 particlePos = Vec3.upFromBottomCenterOf(pos, 0.82);
                ((ServerLevel) level).sendParticles(particle, bouncingEntity.xo, particlePos.y, bouncingEntity.zo, 0, 0, 0, 0, 0);
            }
            return;
        }

        ParticleOptions particle = superBounce ? ModParticleTypes.SUPER_BOUNCE.get() : ModParticleTypes.BOUNCE.get();
        Vec3 particlePos = Vec3.upFromBottomCenterOf(pos, 0.82);
        level.addParticle(particle, bouncingEntity.xo, particlePos.y, bouncingEntity.zo, 0, 0, 0);
    }

    /**
     * Handles logic for bouncing boat
     *
     * @param boat the boat that landed on the trampoline
     * @param velocity the current velocity of the boat
     */
    private void bounceBoat(Boat boat, Vec3 velocity)
    {
        Level level = boat.level();
        boat.setDeltaMovement(velocity.x, -velocity.y, velocity.z);
        if(boat.isControlledByLocalInstance() && !boat.isEffectiveAi())
        {
            level.playLocalSound(boat.blockPosition(), ModSounds.BLOCK_TRAMPOLINE_BOUNCE.get(), SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.2F + 0.9F, false);
        }
        else
        {
            level.playSound(null, boat.blockPosition(), ModSounds.BLOCK_TRAMPOLINE_BOUNCE.get(), SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.2F + 0.9F);
        }
    }

    // To reduce the number of block states, an enum is being used to define only valid shapes.
    // This took down the original 256 states to 47. This doesn't work in all cases.
    public enum Shape implements StringRepresentable
    {
        DEFAULT("default", 0.4F),
        NORTH("north", 0.4F),
        EAST("east", 0.4F),
        SOUTH("south", 0.4F),
        WEST("west", 0.4F),
        NORTH_SOUTH("north_south", 0.4F),
        EAST_WEST("east_west", 0.4F),
        NORTH_EAST("north_east", 0.8F),
        EAST_SOUTH("east_south", 0.8F),
        SOUTH_WEST("south_west", 0.8F),
        WEST_NORTH("west_north", 0.8F),
        NORTH_EAST_WITH_LEG("north_east_with_leg_northeast", 0.4F),
        EAST_SOUTH_WITH_LEG("east_south_with_leg_eastsouth", 0.4F),
        SOUTH_WEST_WITH_LEG("south_west_with_leg_southwest", 0.4F),
        WEST_NORTH_WITH_LEG("west_north_with_leg_westnorth", 0.4F),
        NORTH_EAST_SOUTH("north_east_south", 0.8F),
        EAST_SOUTH_WEST("east_south_west", 0.8F),
        SOUTH_WEST_NORTH("south_west_north", 0.8F),
        WEST_NORTH_EAST("west_north_east", 0.8F),
        NORTH_EAST_SOUTH_WITH_LEG_NORTHEAST("north_east_south_with_leg_northeast", 0.8F),
        NORTH_EAST_SOUTH_WITH_LEG_EASTSOUTH("north_east_south_with_leg_eastsouth", 0.8F),
        NORTH_EAST_SOUTH_WITH_LEG_NORTHEAST_EASTSOUTH("north_east_south_with_leg_northeast_eastsouth", 0.4F),
        EAST_SOUTH_WEST_WITH_LEG_EASTSOUTH("east_south_west_with_leg_eastsouth", 0.8F),
        EAST_SOUTH_WEST_WITH_LEG_SOUTHWEST("east_south_west_with_leg_southwest", 0.8F),
        EAST_SOUTH_WEST_WITH_LEG_EASTSOUTH_SOUTHWEST("east_south_west_with_leg_eastsouth_southwest", 0.4F),
        SOUTH_WEST_NORTH_WITH_LEG_WESTNORTH("south_west_north_with_leg_westnorth", 0.8F),
        SOUTH_WEST_NORTH_WITH_LEG_SOUTHWEST("south_west_north_with_leg_southwest", 0.8F),
        SOUTH_WEST_NORTH_WITH_LEG_WESTNORTH_SOUTHWEST("south_west_north_with_leg_westnorth_southwest", 0.4F),
        WEST_NORTH_EAST_WITH_LEG_NORTHEAST("west_north_east_with_leg_northeast", 0.8F),
        WEST_NORTH_EAST_WITH_LEG_WESTNORTH("west_north_east_with_leg_westnorth", 0.8F),
        WEST_NORTH_EAST_WITH_LEG_NORTHEAST_WESTNORTH("west_north_east_with_leg_northeast_westnorth", 0.4F),
        ALL("north_east_south_west", 1.0F),
        ALL_WITH_LEG_ALL("north_east_south_west_with_leg_northeast_eastsouth_southwest_westnorth", 0.4F),
        ALL_WITH_LEG_NORTHEAST("north_east_south_west_with_leg_northeast", 0.8F),
        ALL_WITH_LEG_NORTHEAST_EASTSOUTH("north_east_south_west_with_leg_northeast_eastsouth", 0.8F),
        ALL_WITH_LEG_NORTHEAST_EASTSOUTH_SOUTHWEST("north_east_south_west_with_leg_northeast_eastsouth_southwest", 0.8F),
        ALL_WITH_LEG_EASTSOUTH("north_east_south_west_with_leg_eastsouth", 0.8F),
        ALL_WITH_LEG_EASTSOUTH_SOUTHWEST("north_east_south_west_with_leg_eastsouth_southwest", 0.8F),
        ALL_WITH_LEG_EASTSOUTH_SOUTHWEST_WESTNORTH("north_east_south_west_with_leg_eastsouth_southwest_westnorth", 0.8F),
        ALL_WITH_LEG_SOUTHWEST("north_east_south_west_with_leg_southwest", 0.8F),
        ALL_WITH_LEG_SOUTHWEST_WESTNORTH("north_east_south_west_with_leg_southwest_westnorth", 0.8F),
        ALL_WITH_LEG_SOUTHWEST_WESTNORTH_NORTHEAST("north_east_south_west_with_leg_southwest_westnorth_northeast", 0.8F),
        ALL_WITH_LEG_WESTNORTH("north_east_south_west_with_leg_westnorth", 0.8F),
        ALL_WITH_LEG_WESTNORTH_NORTHEAST("north_east_south_west_with_leg_westnorth_northeast", 0.8F),
        ALL_WITH_LEG_WESTNORTH_NORTHEAST_EASTSOUTH("north_east_south_west_with_leg_westnorth_northeast_eastsouth", 0.8F),
        ALL_WITH_LEG_NORTHEAST_SOUTHWEST("north_east_south_west_with_leg_northeast_southwest", 0.8F),
        ALL_WITH_LEG_EASTSOUTH_WESTNORTH("north_east_south_west_with_leg_eastsouth_westnorth", 0.8F);

        public static final Map<Integer, Shape> PACKED_VALUE_TO_SHAPE = Arrays.stream(values())
                .collect(Collectors.toMap(shape -> shape.packedValue, shape -> shape));

        private final String name;
        private final int packedValue;
        private final float bounceScale;

        Shape(String name, float bounceScale)
        {
            this.name = name;
            this.packedValue = createPackedValue(name);
            this.bounceScale = bounceScale;
        }

        @Override
        public String getSerializedName()
        {
            return this.name;
        }
        
        public static Shape fromPackedValue(int packedValue)
        {
            return PACKED_VALUE_TO_SHAPE.getOrDefault(packedValue, DEFAULT);
        }

        private static int createPackedValue(String name)
        {
            List<String> values = Arrays.asList(name.split("_"));
            boolean north = values.contains("north");   // connected north
            boolean east = values.contains("east");     // connected east
            boolean south = values.contains("south");   // connected south
            boolean west = values.contains("west");     // connected west
            boolean lnw = values.contains("westnorth"); // leg north-west
            boolean lne = values.contains("northeast"); // leg north-east
            boolean lse = values.contains("eastsouth"); // leg south-east
            boolean lsw = values.contains("southwest"); // leg south-west
            return createPackedValue(north, east, south, west, lnw, lne, lse, lsw);
        }
  
        public static int createPackedValue(boolean n, boolean e, boolean s, boolean w, boolean cnw, boolean cne, boolean cse, boolean csw)
        {
            boolean[] states = {n, e, s, w, cnw, cne, cse, csw};
            int value = 0;
            for(int i = 0; i < states.length; i++)
            {
                if(states[i])
                {
                    value |= 1 << i;
                }
            }
            return value;
        }
    }
}
