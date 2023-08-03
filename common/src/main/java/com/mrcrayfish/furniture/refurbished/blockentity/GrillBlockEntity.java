package com.mrcrayfish.furniture.refurbished.blockentity;

import com.google.common.collect.ImmutableList;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.crafting.GrillCookingRecipe;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageFlipGrillItem;
import com.mrcrayfish.furniture.refurbished.platform.Services;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Author: MrCrayfish
 */
@SuppressWarnings("UnstableApiUsage")
public class GrillBlockEntity extends BlockEntity implements WorldlyContainer
{
    public static final int[] ALL_SLOTS = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    public static final int[] GRILL_SLOTS = new int[]{9, 10, 11, 12};

    private final NonNullList<ItemStack> fuel = NonNullList.withSize(9, ItemStack.EMPTY);
    private final NonNullList<ItemStack> cooking = NonNullList.withSize(4, ItemStack.EMPTY);
    private final ImmutableList<CookingSpace> spaces = Util.make(() -> {
        ImmutableList.Builder<CookingSpace> builder = ImmutableList.builderWithExpectedSize(this.cooking.size());
        IntStream.range(0, this.cooking.size()).forEach(i -> builder.add(new CookingSpace()));
        return builder.build();
    });
    private int remainingFuel;
    private float storedExperience;

    protected GrillBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    public GrillBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.GRILL.get(), pos, state);
    }

    /**
     * @return The cooking space at the given position
     */
    public CookingSpace getCookingSpace(int position)
    {
        return this.spaces.get(position);
    }

    /**
     * @return The inventory for the input. These are the items placed on top of the grill
     */
    public NonNullList<ItemStack> getCookingItems()
    {
        return this.cooking;
    }

    /**
     * @return The inventory for the fuel. These are the items placed inside the grill
     */
    public NonNullList<ItemStack> getFuelItems()
    {
        return this.fuel;
    }

    /**
     * Attempts to add the given ItemStack into cooking space at the given position. If the ItemStack
     * is not associated with any grill cooking recipe, this method will do nothing and this will
     * return false. The rotation determines the orientation that the item is displayed on top of
     * the grill, this can be retrieved from the 2d data value of directions.
     *
     * @param stack    the itemstack to add
     * @param position the position of the cooking space
     * @param rotation the orientation of the item when displayed
     * @return True if the item was successfully added
     */
    public boolean addCookingItem(ItemStack stack, int position, int rotation)
    {
        if(this.cooking.get(position).isEmpty())
        {
            Optional<GrillCookingRecipe> optional = this.findMatchingRecipe(stack);
            if(optional.isPresent())
            {
                GrillCookingRecipe recipe = optional.get();
                ItemStack copy = stack.copy();
                copy.setCount(1);
                this.cooking.set(position, copy);
                this.spaces.get(position).update(recipe.getCookingTime(), recipe.getExperience(), rotation);
                this.syncCookingSpace(position);
                this.playPlaceSound();
                return true;
            }
        }
        return false;
    }

    public boolean addFuel(ItemStack stack)
    {
        for(int i = 0; i < this.fuel.size(); i++)
        {
            if(this.fuel.get(i).isEmpty())
            {
                ItemStack fuel = stack.copy();
                fuel.setCount(1);
                this.fuel.set(i, fuel);
                this.syncFuel();
                return true;
            }
        }
        return false;
    }

    public void flipItem(int position)
    {
        if(!this.cooking.get(position).isEmpty())
        {
            CookingSpace space = this.spaces.get(position);
            if(space.isHalfCooked())
            {
                space.flip();
                this.sendFlipAnimationToPlayers(position);
                this.syncCookingSpace(position);
                this.playFlipSound();
                this.setChanged();
            }
            else if(space.isFullyCooked())
            {
                this.removeCookingItem(position);
            }
        }
    }

    private void sendFlipAnimationToPlayers(int position)
    {
        Level level = Objects.requireNonNull(this.level);
        if(level.getChunkSource() instanceof ServerChunkCache cache)
        {
            BlockPos pos = this.getBlockPos();
            List<ServerPlayer> players = cache.chunkMap.getPlayers(new ChunkPos(pos), false);
            players.forEach(player -> Network.getPlay().sendToPlayer(() -> player, new MessageFlipGrillItem(pos, position)));
        }
    }

    /**
     * Hook method for dispenser behaviour.
     */
    public void flipItems()
    {
        for(int i = 0; i < 4; i++)
        {
            if(!this.cooking.get(i).isEmpty())
            {
                if(this.spaces.get(i).isHalfCooked())
                {
                    this.flipItem(i);
                    return;
                }
            }
        }
    }

    public void removeCookingItem(int position)
    {
        if(!this.cooking.get(position).isEmpty())
        {
            // Spawns the item into the level
            double posX = this.worldPosition.getX() + 0.3 + 0.4 * (position % 2);
            double posY = this.worldPosition.getY() + 1.0;
            double posZ = this.worldPosition.getZ() + 0.3 + 0.4 * (position / 2);
            ItemEntity entity = new ItemEntity(this.level, posX, posY + 0.1, posZ, this.cooking.get(position).copy());
            this.level.addFreshEntity(entity);

            // Puts an empty stack in the position since it was removed
            this.cooking.set(position, ItemStack.EMPTY);

            // Spawn experience orbs if it was fully cooked. The removed item is not always a result.
            CookingSpace space = this.spaces.get(position);
            if(space.isFullyCooked())
            {
                this.spawnExperience(posX, posY, posZ, space.getExperience());
            }

            // Reset the cooking space to avoid potential bugs
            space.update(0, 0F, 0);

            // Sends the items to tracking clients
            CompoundTag compound = new CompoundTag();
            this.writeCookingItems(compound);
            BlockEntityHelper.sendCustomUpdate(this, compound);
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, GrillBlockEntity grill)
    {
        boolean canCook = grill.canCook();
        if(grill.remainingFuel == 0 && canCook)
        {
            for(int i = grill.fuel.size() - 1; i >= 0; i--)
            {
                ItemStack fuel = grill.fuel.get(i);
                if(!fuel.isEmpty())
                {
                    grill.remainingFuel = Services.ITEM.getBurnTime(fuel, RecipeType.SMELTING);
                    grill.fuel.set(i, ItemStack.EMPTY);

                    /* Send updates to client */
                    CompoundTag compound = new CompoundTag();
                    grill.writeFuel(compound);
                    grill.writeRemainingFuel(compound);
                    BlockEntityHelper.sendCustomUpdate(grill, compound);
                    break;
                }
            }
        }

        if(canCook && grill.remainingFuel > 0)
        {
            grill.cookItems();
            grill.remainingFuel--;
            if(grill.remainingFuel == 0)
            {
                /* Send updates to client */
                CompoundTag compound = new CompoundTag();
                grill.writeRemainingFuel(compound);
                BlockEntityHelper.sendCustomUpdate(grill, compound);
            }
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, GrillBlockEntity grill)
    {
        grill.spawnParticles();
        grill.spaces.forEach(space -> space.getAnimation().tick());
    }

    private boolean canCook()
    {
        for(int i = 0; i < this.cooking.size(); i++)
        {
            CookingSpace space = this.spaces.get(i);
            if(!this.cooking.get(i).isEmpty() && space.canCook())
            {
                return true;
            }
        }
        return false;
    }

    private void cookItems()
    {
        boolean changed = false;
        for(int i = 0; i < this.cooking.size(); i++)
        {
            if(!this.cooking.get(i).isEmpty())
            {
                CookingSpace space = this.spaces.get(i);
                if(space.canCook())
                {
                    space.cook();
                    if(space.isCooked())
                    {
                        /* Set to result on cooked and flipped */
                        if(space.isFlipped())
                        {
                            Level level = Objects.requireNonNull(this.level);
                            Optional<GrillCookingRecipe> optional = level.getRecipeManager().getRecipeFor(ModRecipeTypes.GRILL_COOKING.get(), new SimpleContainer(this.cooking.get(i)), level);
                            if(optional.isPresent())
                            {
                                this.cooking.set(i, optional.get().getResultItem(level.registryAccess()).copy());
                            }
                        }
                        this.syncCookingSpace(i);
                        changed = true;
                    }
                }
            }
        }
        if(changed)
        {
            // Update items on clients
            CompoundTag compound = new CompoundTag();
            this.writeCookingItems(compound);
            BlockEntityHelper.sendCustomUpdate(this, compound);

            // Mark as changed to ensure it's saved
            this.setChanged();
        }
    }

    private void spawnParticles()
    {
        Level level = this.getLevel();
        if(level != null)
        {
            if(this.isCooking())
            {
                double posX = this.worldPosition.getX() + 0.2 + 0.6 * level.random.nextDouble();
                double posY = this.worldPosition.getY() + 0.85;
                double posZ = this.worldPosition.getZ() + 0.2 + 0.6 * level.random.nextDouble();
                level.addParticle(ParticleTypes.FLAME, posX, posY, posZ, 0.0, 0.0, 0.0);
            }

            BlockPos pos = this.getBlockPos();
            for(int i = 0; i < this.cooking.size(); i++)
            {
                if(!this.cooking.get(i).isEmpty() && level.random.nextFloat() < 0.1F)
                {
                    double posX = pos.getX() + 0.3 + 0.4 * (i % 2);
                    double posY = pos.getY() + 1.0;
                    double posZ = pos.getZ() + 0.3 + 0.4 * (i / 2);
                    if(this.spaces.get(i).isHalfCooked())
                    {
                        for(int j = 0; j < 4; j++)
                        {
                            level.addParticle(ParticleTypes.SMOKE, posX, posY, posZ, 0.0D, 5.0E-4D, 0.0D);
                        }
                    }
                }
            }
        }
    }

    private boolean isCooking()
    {
        for(int i = 0; i < this.cooking.size(); i++)
        {
            CookingSpace space = this.spaces.get(i);
            if(!this.cooking.get(i).isEmpty() && (space.canCook() || space.isHalfCooked()))
            {
                return this.remainingFuel > 0;
            }
        }
        return false;
    }

    public Optional<GrillCookingRecipe> findMatchingRecipe(ItemStack input)
    {
        return this.cooking.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.level.getRecipeManager().getRecipeFor(ModRecipeTypes.GRILL_COOKING.get(), new SimpleContainer(input), this.level);
    }

    @Override
    public int getContainerSize()
    {
        return this.fuel.size() + this.cooking.size();
    }

    @Override
    public boolean isEmpty()
    {
        for(ItemStack stack : this.fuel)
        {
            if(!stack.isEmpty())
            {
                return false;
            }
        }
        for(ItemStack stack : this.cooking)
        {
            if(!stack.isEmpty())
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int index)
    {
        if(index - this.fuel.size() >= 0)
        {
            return this.cooking.get(index - this.fuel.size());
        }
        return this.fuel.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int count)
    {
        if(index - this.fuel.size() >= 0)
        {
            index -= this.fuel.size();
            ItemStack result = ContainerHelper.removeItem(this.cooking, index, count);

            if(this.cooking.get(index).isEmpty())
            {
                CookingSpace space = this.spaces.get(index);
                if(space.isFullyCooked())
                {
                    double posX = this.worldPosition.getX() + 0.3 + 0.4 * (index % 2);
                    double posY = this.worldPosition.getY() + 1.0;
                    double posZ = this.worldPosition.getZ() + 0.3 + 0.4 * (index / 2);
                    this.spawnExperience(posX, posY, posZ, space.getExperience());
                }

                // Reset the space after removing
                space.update(0, 0F, 0);
            }

            /* Send updates to client */
            CompoundTag compound = new CompoundTag();
            this.writeCookingItems(compound);
            BlockEntityHelper.sendCustomUpdate(this, compound);

            return result;
        }
        ItemStack result = ContainerHelper.removeItem(this.fuel, index, count);
        this.syncFuel();
        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index)
    {
        if(index - this.fuel.size() >= 0)
        {
            return ContainerHelper.takeItem(this.cooking, index - this.fuel.size());
        }
        return ContainerHelper.takeItem(this.fuel, index);
    }

    @Override
    public void setItem(int index, ItemStack stack)
    {
        NonNullList<ItemStack> inventory = this.fuel;
        if(index - this.fuel.size() >= 0)
        {
            index -= this.fuel.size();
            inventory = this.cooking;
            // TODO store this in the cooking space
            Optional<GrillCookingRecipe> optional = this.level.getRecipeManager().getRecipeFor(ModRecipeTypes.GRILL_COOKING.get(), new SimpleContainer(stack), this.level);
            if(optional.isPresent())
            {
                GrillCookingRecipe recipe = optional.get();
                this.spaces.get(index).update(recipe.getCookingTime(), recipe.getExperience(), (byte) 0);
                this.syncCookingSpace(index);
            }
        }
        inventory.set(index, stack);
        if(stack.getCount() > this.getMaxStackSize())
        {
            stack.setCount(this.getMaxStackSize());
        }

        /* Send updates to client */
        CompoundTag compound = new CompoundTag();
        this.writeCookingItems(compound);
        this.writeFuel(compound);
        BlockEntityHelper.sendCustomUpdate(this, compound);
    }

    @Override
    public int getMaxStackSize()
    {
        return 1;
    }

    @Override
    public void clearContent()
    {
        this.fuel.clear();
        this.cooking.clear();
    }

    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        if(compound.contains("Grill", Tag.TAG_LIST))
        {
            this.cooking.clear();
            BlockEntityHelper.loadItems("Grill", compound, this.cooking);
        }
        if(compound.contains("Fuel", Tag.TAG_LIST))
        {
            this.fuel.clear();
            BlockEntityHelper.loadItems("Fuel", compound, this.fuel);
        }
        if(compound.contains("RemainingFuel", Tag.TAG_INT))
        {
            this.remainingFuel = compound.getInt("RemainingFuel");
        }
        if(compound.contains("StoredExperience", Tag.TAG_FLOAT))
        {
            this.storedExperience = compound.getFloat("StoredExperience");
        }
        this.readCookingSpaces(compound);
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        this.writeCookingItems(tag);
        this.writeFuel(tag);
        this.writeRemainingFuel(tag);
        this.writeCookingSpaces(tag);
        tag.putFloat("StoredExperience", this.storedExperience);
    }

    /**
     * Writes the cooking inventory to NBT. This method is used for saving and sending sync data to clients.
     *
     * @param compound the compound tag to save the data to
     * @return the compound tag the data saved to
     */
    private CompoundTag writeCookingItems(CompoundTag compound)
    {
        BlockEntityHelper.saveItems("Grill", compound, this.cooking);
        return compound;
    }

    /**
     * Writes the fuel inventory to NBT. This method is used for saving and sending sync data to clients.
     *
     * @param compound the compound tag to save the data to
     * @return the compound tag the data saved to
     */
    private CompoundTag writeFuel(CompoundTag compound)
    {
        BlockEntityHelper.saveItems("Fuel", compound, this.fuel);
        return compound;
    }

    /**
     * Writes the remaining fuel to NBT. This method is used for saving and sending sync data to
     * clients.
     *
     * @param compound the compound tag to save the data to
     * @return the compound tag the data saved to
     */
    private CompoundTag writeRemainingFuel(CompoundTag compound)
    {
        compound.putInt("RemainingFuel", this.remainingFuel);
        return compound;
    }

    /**
     * Helper method to save all Cooking Spaces to NBT.
     * See {@link #writeCookingSpaces(CompoundTag, int)} for more info
     *
     * @param compound the compound tag to save the data to
     * @return the compound tag the data saved to
     */
    private CompoundTag writeCookingSpaces(CompoundTag compound)
    {
        this.writeCookingSpaces(compound, -1);
        return compound;
    }

    /**
     * Writes the Cooking Spaces to NBT. The second parameter allows control over the position
     * to save and is only used for syncing data to the client. To save all items, the position
     * should be -1.
     *
     * @param compound the compound tag to save the data to
     * @param position if syncing, the position to sync otherwise -1
     * @return the compound tag the data saved to
     */
    private CompoundTag writeCookingSpaces(CompoundTag compound, int position)
    {
        ListTag list = new ListTag();
        if(position >= 0 && position < this.spaces.size())
        {
            CompoundTag tag = new CompoundTag();
            this.spaces.get(position).writeToTag(tag);
            tag.putInt("Position", position);
            list.add(tag);
        }
        else if(position == -1)
        {
            for(int i = 0; i < this.spaces.size(); i++)
            {
                CompoundTag tag = new CompoundTag();
                this.spaces.get(i).writeToTag(tag);
                tag.putInt("Position", i);
                list.add(tag);
            }
        }
        compound.put("CookingSpaces", list);
        return compound;
    }

    /**
     * Reads the Cooking Spaces from NBT. This method has been designed to accept partial data.
     * This means it can read the data from one cooking space and not reset/affect the other spaces.
     * This use case is used for reading sync data, while still being a general method to read all
     * the cooking spaces.
     *
     * @param compound the compound tag to read from
     */
    private void readCookingSpaces(CompoundTag compound)
    {
        if(compound.contains("CookingSpaces", Tag.TAG_LIST))
        {
            ListTag list = compound.getList("CookingSpaces", Tag.TAG_COMPOUND);
            list.forEach(nbt ->
            {
                CompoundTag tag = (CompoundTag) nbt;
                if(tag.contains("Position", Tag.TAG_INT))
                {
                    int position = tag.getInt("Position");
                    if(position >= 0 && position < this.spaces.size())
                    {
                        this.spaces.get(position).readFromTag(tag);
                    }
                }
            });
        }
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        return this.saveWithoutMetadata();
    }

    @Override
    public boolean stillValid(Player player)
    {
        return this.level.getBlockEntity(this.worldPosition) == this && player.distanceToSqr(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5) <= 64;
    }

    @Override
    public int[] getSlotsForFace(Direction side)
    {
        return side == Direction.DOWN ? GRILL_SLOTS : ALL_SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction)
    {
        if(!this.getItem(index).isEmpty())
        {
            return false;
        }
        if(index - this.fuel.size() >= 0)
        {
            return this.level.getRecipeManager().getRecipeFor(ModRecipeTypes.GRILL_COOKING.get(), new SimpleContainer(stack), this.level).isPresent();
        }
        return stack.getItem() == Items.COAL || stack.getItem() == Items.CHARCOAL;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction)
    {
        if(direction == Direction.DOWN)
        {
            if(index - this.fuel.size() >= 0)
            {
                index -= this.fuel.size();
                if(this.spaces.get(index).isFullyCooked())
                {
                    Optional<GrillCookingRecipe> optional = this.level.getRecipeManager().getRecipeFor(ModRecipeTypes.GRILL_COOKING.get(), new SimpleContainer(stack), this.level);
                    return !optional.isPresent();
                }
            }
        }
        return false;
    }

    private void playPlaceSound()
    {
        Level level = Objects.requireNonNull(this.level);
        Vec3 pos = Vec3.atBottomCenterOf(this.worldPosition).add(0, 1, 0);
        float pitch = 0.9F + 0.2F * level.random.nextFloat();
        level.playSound(null, pos.x, pos.y, pos.z, SoundEvents.BONE_BLOCK_PLACE, SoundSource.BLOCKS, 0.75F, pitch);
    }

    private void playFlipSound()
    {
        Level level = Objects.requireNonNull(this.level);
        Vec3 pos = Vec3.atBottomCenterOf(this.worldPosition).add(0, 1, 0);
        level.playSound(null, pos.x, pos.y, pos.z, SoundEvents.AMETHYST_BLOCK_HIT, SoundSource.BLOCKS, 0.75F, 1.0F);
    }

    /**
     * Syncs the cooking space at the given position to all tracking clients of this grill
     * @param position the position of the cooking space
     */
    private void syncCookingSpace(int position)
    {
        CompoundTag compound = new CompoundTag();
        this.writeCookingItems(compound);
        this.writeCookingSpaces(compound, position);
        BlockEntityHelper.sendCustomUpdate(this, compound);
    }

    /**
     * Syncs the fuel items to all the tracking clients of this grill
     */
    private void syncFuel()
    {
        CompoundTag compound = new CompoundTag();
        this.writeFuel(compound);
        BlockEntityHelper.sendCustomUpdate(this, compound);
    }

    /**
     * Spawns experience into the level from the stored experience. Experience will only spawn
     * if there is enough to spawn orb. This means that players will be awarded experience after
     * cooking multiple items, not just one.
     *
     * @param x   the x position to spawn the orbs
     * @param y   the y position to spawn the orbs
     * @param z   the z position to spawn the orbs
     * @param exp the experience to try and spawn, otherwise it is just stored
     */
    private void spawnExperience(double x, double y, double z, float exp)
    {
        this.storedExperience += Math.max(exp, 0);
        if(Mth.floor(this.storedExperience) >= 1.0F && this.level instanceof ServerLevel level)
        {
            int amount = Mth.floor(this.storedExperience);
            ExperienceOrb.award(level, new Vec3(x, y, z), amount);
            this.storedExperience = Math.max(this.storedExperience - amount, 0);
        }
    }

    /**
     * Plays the grill flipping animation for the given cooking space position. This method will do
     * nothing if called on the logical server side.
     *
     * @param position the cooking space index
     */
    public void playFlipAnimation(int position)
    {
        if(Objects.requireNonNull(this.level).isClientSide() && position >= 0 && position < this.spaces.size())
        {
            this.spaces.get(position).getAnimation().play();
        }
    }

    public static class CookingSpace
    {
        private int cookingTime = 0;
        private int totalCookingTime = 0;
        private boolean flipped = false;
        private float experience = 0F;
        private int rotation = 0;

        // Client only
        private FlipAnimation animation;

        public boolean isFlipped()
        {
            return this.flipped;
        }

        public float getExperience()
        {
            return this.experience;
        }

        public int getRotation()
        {
            return this.rotation;
        }

        /**
         * Increments the cooking time if this space has not reached it's maximum cooking time
         */
        public void cook()
        {
            if(this.cookingTime < this.totalCookingTime)
            {
                this.cookingTime++;
            }
        }

        /**
         * @return True if the cooking time has reached the total cooking time. This doesn't take
         * into consideration if the item in this space has been flipped. See {@link #isFullyCooked()}
         */
        public boolean isCooked()
        {
            return this.cookingTime == this.totalCookingTime;
        }

        /**
         * @return True if the cooking time has reached the total cooking time and the item
         * has not been flipped yet.
         */
        public boolean isHalfCooked()
        {
            return !this.flipped && this.cookingTime == this.totalCookingTime;
        }

        /**
         * @return True if the cooking time has reached the total cooking time and the item has also
         * been flipped.
         */
        public boolean isFullyCooked()
        {
            return this.flipped && this.cookingTime == this.totalCookingTime;
        }

        /**
         * @return True if the cooking time has not reached the total cooking time.
         */
        public boolean canCook()
        {
            return this.cookingTime < this.totalCookingTime;
        }

        public void update(int cookTime, float experience, int rotation)
        {
            this.cookingTime = 0;
            this.totalCookingTime = cookTime / 2; //Half the time because it has to cook both sides
            this.flipped = false;
            this.experience = experience;
            this.rotation = rotation;
        }

        public void flip()
        {
            if(!this.flipped)
            {
                this.flipped = true;
                this.cookingTime = 0;
            }
        }

        public void writeToTag(CompoundTag tag)
        {
            tag.putInt("CookingTime", this.cookingTime);
            tag.putInt("TotalCookingTime", this.totalCookingTime);
            tag.putBoolean("Flipped", this.flipped);
            tag.putFloat("Experience", this.experience);
            tag.putInt("Rotation", this.rotation);
        }

        public void readFromTag(CompoundTag tag)
        {
            if(tag.contains("CookingTime", Tag.TAG_INT))
            {
                this.cookingTime = tag.getInt("CookingTime");
            }
            if(tag.contains("TotalCookingTime", Tag.TAG_INT))
            {
                this.totalCookingTime = tag.getInt("TotalCookingTime");
            }
            if(tag.contains("Flipped", Tag.TAG_BYTE))
            {
                this.flipped = tag.getBoolean("Flipped");
            }
            if(tag.contains("Experience", Tag.TAG_FLOAT))
            {
                this.experience = tag.getFloat("Experience");
            }
            if(tag.contains("Rotation", Tag.TAG_INT))
            {
                this.rotation = tag.getInt("Rotation");
            }
        }

        public FlipAnimation getAnimation()
        {
            if(this.animation == null)
            {
                this.animation = new FlipAnimation();
            }
            return this.animation;
        }
    }

    public static class FlipAnimation
    {
        public static final int FLIP_ANIMATION_LENGTH = 15;

        private boolean flipping;
        private int animation;

        /**
         * Plays the animation
         */
        public void play()
        {
            this.flipping = true;
            this.animation = 0;
        }

        /**
         * @return True if the animation is currently playing. The animation is playing if it's
         * flipped and the animation time is less than the animation length.
         */
        public boolean isPlaying()
        {
            return this.flipping && this.animation < FLIP_ANIMATION_LENGTH;
        }

        /**
         * @return The current animation time
         */
        public float getTime(float partialTick)
        {
            return Mth.clamp(Mth.lerp(partialTick, this.animation, this.animation + 1), 0, FLIP_ANIMATION_LENGTH) / FLIP_ANIMATION_LENGTH;
        }

        /**
         * Handles updating the animation time and stopping when finished
         */
        public void tick()
        {
            if(this.flipping && this.animation < FLIP_ANIMATION_LENGTH)
            {
                this.animation++;
                if(this.animation == FLIP_ANIMATION_LENGTH)
                {
                    this.flipping = false;
                }
            }
        }
    }
}