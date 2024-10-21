package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mojang.math.Vector3f;
import com.mrcrayfish.furniture.refurbished.block.RangeHoodBlock;
import com.mrcrayfish.furniture.refurbished.client.audio.AudioManager;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModParticleTypes;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.crafting.ProcessingRecipe;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageFlipAnimation;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
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

// TODO allow to be placed on grills, including farmers delight blocks

/**
 * Author: MrCrayfish
 */
public class FryingPanBlockEntity extends BasicLootBlockEntity implements ICookingBlock, ILevelAudio
{
    public static final Vector3f OIL_COLOUR = new Vector3f(Vec3.fromRGB24(0xE1A803));
    public static final double MAX_AUDIO_DISTANCE = Mth.square(8);

    protected final RecipeManager.CachedCheck<Container, ? extends ProcessingRecipe> recipeCache;
    protected final RecipeManager.CachedCheck<Container, ? extends AbstractCookingRecipe> campfireCookingCache;
    protected final Vec3 audioPosition;
    protected boolean needsFlipping;
    protected boolean flipped;
    protected int rotation;
    protected boolean sync;

    // Client only
    private FlipAnimation animation;

    public FryingPanBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.FRYING_PAN.get(), pos, state, ModRecipeTypes.FRYING_PAN_COOKING.get());
    }

    public FryingPanBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, RecipeType<? extends ProcessingRecipe> recipeType)
    {
        super(type, pos, state, 1);
        this.recipeCache = RecipeManager.createCheck(recipeType);
        this.campfireCookingCache = RecipeManager.createCheck(RecipeType.CAMPFIRE_COOKING);
        this.audioPosition = Vec3.atCenterOf(pos).add(0, -0.375, 0);
    }

    public boolean isFlippingNeeded()
    {
        return this.needsFlipping;
    }

    public boolean isFlipped()
    {
        return this.flipped;
    }

    public int getRotation()
    {
        return this.rotation;
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "frying_pan");
    }

    @Override
    public boolean isMatchingContainerMenu(AbstractContainerMenu menu)
    {
        return false;
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        return null;
    }

    @Override
    public int getMaxStackSize()
    {
        return 1;
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, FryingPanBlockEntity fryingPan)
    {
        AudioManager.get().playLevelAudio(fryingPan);
        fryingPan.spawnParticles(level, pos);

        FlipAnimation animation = fryingPan.getAnimation();
        boolean wasPlaying = animation.isPlaying();
        animation.tick();
        if(wasPlaying && !animation.isPlaying())
        {
            fryingPan.playPlaceIngredientSound(true, 1.0F);
        }
    }

    /**
     * Spawns sizzling particles when the item in the pan is cooking
     *
     * @param level the level the spawn the particles
     * @param pos   the block position of the frying pan
     */
    private void spawnParticles(Level level, BlockPos pos)
    {
        if(this.isCooking() && !this.getAnimation().isPlaying())
        {
            double posX = pos.getX() + 0.35 + 0.3 * level.random.nextDouble();
            double posY = pos.getY() + 0.15;
            double posZ = pos.getZ() + 0.35 + 0.3 * level.random.nextDouble();
            level.addParticle(new DustParticleOptions(OIL_COLOUR, 0.25F), posX, posY, posZ, 0, -0.05, 0);
            this.spawnSteam(level, posX, posY, posZ);
        }
    }

    /**
     * Spawns a steam particle from the frying pan where the y velocity of the particle
     * changes depending on if a range hood is being used.
     *
     * @param level the level containing the frying pan
     * @param x     the start x position of the particle
     * @param y     the start y position of the particle
     * @param z     the start z position of the particle
     */
    public void spawnSteam(Level level, double x, double y, double z)
    {
        double ySpeed = 0.01;
        if(this.isRangeHoodPowered(level, this.worldPosition.above()))
        {
            ySpeed = 0.05;
        }
        else if(this.isRangeHoodPowered(level, this.worldPosition.above().above()))
        {
            ySpeed = 0.1;
        }
        level.addParticle(ModParticleTypes.STEAM.get(), x, y, z, 0, ySpeed, 0);
    }

    /**
     * Determines if range hood is located at the given block position in the level and that
     * the range hood is powered.
     *
     * @param level the level containing the frying pan
     * @param pos   the block position to check
     * @return True if range hook is located and powered
     */
    private boolean isRangeHoodPowered(Level level, BlockPos pos)
    {
        BlockState state = level.getBlockState(pos);
        if(state.getBlock() instanceof RangeHoodBlock)
        {
            return state.getValue(RangeHoodBlock.POWERED);
        }
        return false;
    }

    @Override
    public BlockEntity getBlockEntity()
    {
        return this;
    }

    @Override
    public boolean canCook()
    {
        ItemStack stack = this.getItem(0);
        if(!stack.isEmpty() && !this.needsFlipping)
        {
            return this.getRecipe(stack).isPresent();
        }
        return false;
    }

    @Override
    public int getTimeToCook()
    {
        ItemStack stack = this.getItem(0);
        if(!stack.isEmpty())
        {
            Optional<? extends ProcessingRecipe> optional = this.getRecipe(stack);
            if(optional.isPresent())
            {
                return optional.get().getTime() / 2; // Half the time since it's flipped
            }
        }
        return 0;
    }

    @Override
    public void onCompleteCooking()
    {
        if(!this.flipped)
        {
            this.needsFlipping = true;
            this.setChanged();
            return;
        }

        ItemStack stack = this.getItem(0);
        if(!stack.isEmpty())
        {
            Item remainingItem = stack.getItem().getCraftingRemainingItem();
            Optional<? extends ProcessingRecipe> optional = this.getRecipe(stack);
            ItemStack result = optional.map(ProcessingRecipe::getResultItem).orElse(ItemStack.EMPTY);
            stack.shrink(1);
            if(!result.isEmpty())
            {
                ItemStack copy = result.copy();
                this.setItem(0, copy);
                if(remainingItem != null)
                {
                    BlockPos pos = this.worldPosition;
                    this.level.addFreshEntity(new ItemEntity(this.level, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, new ItemStack(remainingItem)));
                }
            }
        }
    }

    @Override
    public boolean canPlaceItem(int slotIndex, ItemStack stack)
    {
        return slotIndex == 0 && super.canPlaceItem(slotIndex, stack) && this.getRecipe(stack).isPresent();
    }

    @Override
    public boolean canTakeItemThroughFace(int slotIndex, ItemStack stack, Direction direction)
    {
        return slotIndex == 0 && this.getRecipe(stack).isEmpty();
    }

    @Override
    public void setItem(int slotIndex, ItemStack stack)
    {
        // Fixes an issue when items are inserted by a hopper that flipped is not reset
        if(!this.isCooking() && this.getRecipe(stack).isPresent())
        {
            this.flipped = false;
            this.needsFlipping = false;
        }
        super.setItem(slotIndex, stack);
    }

    /**
     * Gets the frying pan recipe for the given input ItemStack. This method has a fallback on
     * smoker recipes if no frying pan recipe is found. Smoker recipes are mostly food items. This
     * allows for greater support of modded food items since they most likely support the smoker.
     *
     * @param stack the input item stack
     * @return an AbstractCookingRecipe for the given input or an empty optional is no recipe is found
     */
    private Optional<? extends ProcessingRecipe> getRecipe(ItemStack stack)
    {
        Optional<? extends ProcessingRecipe> optional = this.getRecipe(this.recipeCache, stack);
        optional = optional.isEmpty() ? this.getCookingRecipe(this.campfireCookingCache, stack) : optional;
        return optional;
    }

    private Optional<ProcessingRecipe> getCookingRecipe(RecipeManager.CachedCheck<Container, ? extends AbstractCookingRecipe> cache, ItemStack stack)
    {
        return cache.getRecipeFor(new SimpleContainer(stack), Objects.requireNonNull(this.level)).map(ProcessingRecipe.Item::from);
    }

    /**
     * A utility to get a recipe for the given cache and ItemStack.
     *
     * @param cache the cache to check
     * @param stack the itemstack of the recipe
     * @return An optional recipe
     */
    private Optional<? extends ProcessingRecipe> getRecipe(RecipeManager.CachedCheck<Container, ? extends ProcessingRecipe> cache, ItemStack stack)
    {
        return cache.getRecipeFor(new SimpleContainer(stack), Objects.requireNonNull(this.level));
    }

    /**
     * Flips the item
     */
    public void flipItem()
    {
        if(this.needsFlipping && !this.flipped)
        {
            this.flipped = true;
            this.needsFlipping = false;
            this.sendFlipAnimationToPlayers();
            this.setChanged();
            return;
        }
        if(!this.getItem(0).isEmpty())
        {
            this.removeContents();
        }
    }

    /**
     * Places an ItemStack into the frying pan. The rotation of the item when rendering is
     * determined by the rotation param. This should be the 2d value of a Direction.
     *
     * @param stack    the item to place
     * @param rotation the rotation of the item
     * @return True if the item was placed into the frying pan
     */
    public boolean placeContents(Level level, ItemStack stack, int rotation)
    {
        if(!stack.isEmpty() && this.getItem(0).isEmpty())
        {
            ItemStack copy = stack.copy();
            copy.setCount(1);
            this.setItem(0, copy);
            this.rotation = rotation;
            this.flipped = false;
            this.needsFlipping = false;
            this.setChanged();
            this.playPlaceIngredientSound(false, 0.85F);
            return true;
        }
        this.removeContents();
        return false;
    }

    /**
     * Removes the contents from the frying pan and spawns it as an entity in level
     */
    public void removeContents()
    {
        if(!this.getItem(0).isEmpty())
        {
            ItemStack stack = this.getItem(0);
            BlockPos pos = this.worldPosition;
            ItemEntity entity = new ItemEntity(this.level, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, stack.copy());
            this.setItem(0, ItemStack.EMPTY);
            this.level.addFreshEntity(entity);
            this.flipped = false;
            this.needsFlipping = false;
            this.setChanged();
        }
    }

    /**
     * Plays the flipping animation for the item at the given index. This method will do
     * nothing if called on the logical server side.
     *
     * @param index the index of the item
     */
    public void playFlipAnimation(int index)
    {
        if(Objects.requireNonNull(this.level).isClientSide() && index == 0)
        {
            this.getAnimation().play();
        }
    }

    /**
     * Sends a packet to clients tracking the position of this frying pan to perform a flipping
     * animation for the item currently placed in the frying pan.
     */
    private void sendFlipAnimationToPlayers()
    {
        Level level = Objects.requireNonNull(this.level);
        if(level.getChunkSource() instanceof ServerChunkCache cache)
        {
            BlockPos pos = this.getBlockPos();
            List<ServerPlayer> players = cache.chunkMap.getPlayers(new ChunkPos(pos), false);
            players.forEach(player -> Network.getPlay().sendToPlayer(() -> player, new MessageFlipAnimation(pos, 0)));
        }
    }

    /**
     * Client only method. Gets the animation handler used for updating the flipping animation
     *
     * @return An animation handler for the flipping motion
     */
    public FlipAnimation getAnimation()
    {
        if(this.animation == null)
        {
            this.animation = new FlipAnimation();
        }
        return this.animation;
    }

    private void playPlaceIngredientSound(boolean client, float pitch)
    {
        Level level = Objects.requireNonNull(this.level);
        Vec3 vec = Vec3.atBottomCenterOf(this.worldPosition);
        if(client)
        {
            level.playLocalSound(vec.x, vec.y, vec.z, ModSounds.BLOCK_FRYING_PAN_PLACE_INGREDIENT.get(), SoundSource.PLAYERS, 1.0F, pitch, false);
            return;
        }
        level.playSound(null, vec.x, vec.y, vec.z, ModSounds.BLOCK_FRYING_PAN_PLACE_INGREDIENT.get(), SoundSource.PLAYERS, 1.0F, pitch);
    }

    @Override
    public void setChanged()
    {
        super.setChanged();
        this.sync();
    }

    /**
     * Marks the frying pan as needing to sync data to tracking clients
     */
    protected void sync()
    {
        BlockEntityHelper.sendCustomUpdate(this, this.getUpdateTag());
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        if(tag.contains("NeedsFlipping", Tag.TAG_BYTE))
        {
            this.needsFlipping = tag.getBoolean("NeedsFlipping");
        }
        if(tag.contains("Flipped", Tag.TAG_BYTE))
        {
            this.flipped = tag.getBoolean("Flipped");
        }
        if(tag.contains("Rotation", Tag.TAG_INT))
        {
            this.rotation = tag.getInt("Rotation");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.putBoolean("NeedsFlipping", this.needsFlipping);
        tag.putBoolean("Flipped", this.flipped);
        tag.putInt("Rotation", this.rotation);
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
    public SoundEvent getSound()
    {
        return ModSounds.BLOCK_FRYING_PAN_SIZZLING.get();
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
        return !this.isRemoved() && (this.isCooking() || this.isPartiallyCooked());
    }

    @Override
    public float getAudioVolume()
    {
        return (this.isCooking() || this.isPartiallyCooked()) && !this.getAnimation().isPlaying() ? 1.0F : 0.0F;
    }

    @Override
    public float getAudioPitch()
    {
        return this.isPartiallyCooked() ? 0.8F : 1.0F;
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
        return MAX_AUDIO_DISTANCE;
    }

    private boolean isPartiallyCooked()
    {
        return !this.getItem(0).isEmpty() && this.isFlippingNeeded();
    }
}
