package com.mrcrayfish.furniture.refurbished.blockentity;

import com.google.common.collect.ImmutableSet;
import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.client.audio.AudioManager;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.crafting.RecycleBinRecyclingRecipe;
import com.mrcrayfish.furniture.refurbished.inventory.BuildableContainerData;
import com.mrcrayfish.furniture.refurbished.inventory.RecycleBinMenu;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public class RecycleBinBlockEntity extends ElectricityModuleLootBlockEntity implements IProcessingBlock, IPowerSwitch, IAudioBlock
{
    private static Map<ResourceLocation, List<WeakReference<RecycleBinRecyclingRecipe>>> recipeLookup;
    private static final Set<ItemLike> INVALID_ITEMS = Util.make(() -> {
        ImmutableSet.Builder<ItemLike> builder = ImmutableSet.builder();
        builder.add(Items.KNOWLEDGE_BOOK);
        builder.add(Items.DEBUG_STICK);
        builder.add(Items.BARRIER);
        builder.add(Items.STRUCTURE_VOID);
        builder.add(Items.STRUCTURE_BLOCK);
        builder.add(Items.LIGHT);
        builder.add(Items.COMMAND_BLOCK);
        builder.add(Items.COMMAND_BLOCK_MINECART);
        builder.add(Items.CHAIN_COMMAND_BLOCK);
        builder.add(Items.REPEATING_COMMAND_BLOCK);
        builder.add(Items.JIGSAW);
        builder.add(Items.PETRIFIED_OAK_SLAB);
        builder.add(Items.PLAYER_HEAD);
        builder.add(Items.BEDROCK);
        builder.add(Items.REINFORCED_DEEPSLATE);
        builder.add(Items.BUDDING_AMETHYST);
        builder.add(Items.CHORUS_PLANT);
        builder.add(Items.DIRT_PATH);
        builder.add(Items.END_PORTAL_FRAME);
        builder.add(Items.FARMLAND);
        builder.add(Items.INFESTED_CHISELED_STONE_BRICKS);
        builder.add(Items.INFESTED_CRACKED_STONE_BRICKS);
        builder.add(Items.INFESTED_COBBLESTONE);
        builder.add(Items.INFESTED_DEEPSLATE);
        builder.add(Items.INFESTED_MOSSY_STONE_BRICKS);
        builder.add(Items.INFESTED_STONE);
        builder.add(Items.INFESTED_STONE_BRICKS);
        builder.add(Items.SPAWNER);
        builder.add(Items.BUNDLE);
        return builder.build();
    });

    public static final int[] INPUT_SLOTS = new int[]{0};
    public static final int[] OUTPUT_SLOTS = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
    public static final int DATA_POWERED = 0;
    public static final int DATA_ENABLED = 1;
    public static final int DATA_PROCESSING_TIME = 2;

    protected RandomSource random = RandomSource.create();
    protected SimpleContainer output = new SimpleContainer(9);
    protected boolean powered;
    protected boolean processing;
    protected int processingTime;
    protected boolean enabled;
    protected boolean stopped;
    protected long seed = this.random.nextLong();
    protected @Nullable Item inputCache;
    protected @Nullable List<ItemStack> outputCache;

    protected final ContainerData data = new BuildableContainerData(builder -> {
        builder.add(DATA_ENABLED, () -> enabled ? 1 : 0, value -> {});
        builder.add(DATA_POWERED, () -> powered ? 1 : 0, value -> {});
        builder.add(DATA_PROCESSING_TIME, () -> processingTime, value -> {});
    });

    public RecycleBinBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.RECYCLE_BIN.get(), pos, state);
    }

    public RecycleBinBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state, 10);
    }

    @Override
    public int[] getSlotsForFace(Direction direction)
    {
        return direction == Direction.DOWN ? OUTPUT_SLOTS : INPUT_SLOTS;
    }

    @Override
    public boolean isMatchingContainerMenu(AbstractContainerMenu menu)
    {
        return menu instanceof RecycleBinMenu recycleBin && recycleBin.getContainer() == this;
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "recycle_bin");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        return new RecycleBinMenu(windowId, playerInventory, this, this.data);
    }

    @Override
    public SoundEvent getSound()
    {
        return ModSounds.BLOCK_RECYCLE_BIN_ENGINE.get();
    }

    @Override
    public BlockPos getAudioPosition()
    {
        return this.worldPosition;
    }

    @Override
    public boolean canPlayAudio()
    {
        return this.isPowered() && this.processing && this.enabled && !this.isRemoved();
    }

    @Override
    public boolean isPowered()
    {
        return this.powered;
    }

    @Override
    public void setPowered(boolean powered)
    {
        this.powered = powered;
        this.setChanged();
        this.sync();
    }

    @Override
    public int getEnergy()
    {
        return 0;
    }

    @Override
    public void addEnergy(int energy) {}

    @Override
    public boolean requiresEnergy()
    {
        return false;
    }

    @Override
    public int retrieveEnergy(boolean simulate)
    {
        return 0;
    }

    @Override
    public int updateAndGetTotalProcessingTime()
    {
        return this.getTotalProcessingTime();
    }

    @Override
    public int getTotalProcessingTime()
    {
        return Config.SERVER.recycleBin.processingTime.get();
    }

    @Override
    public int getProcessingTime()
    {
        return this.processingTime;
    }

    @Override
    public void setProcessingTime(int time)
    {
        this.processingTime = time;
    }

    @Override
    public void onCompleteProcess()
    {
        this.recycleItem();
        this.seed = this.level.random.nextLong();
        this.resetCache();
    }

    @Override
    public boolean canProcess()
    {
        if(!this.stopped && this.enabled && this.isPowered() && recipeLookup != null)
        {
            ItemStack input = this.getItem(0);
            if(input.isEmpty())
                return false;

            // A unique seed per item
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(input.getItem());
            this.random.setSeed(this.seed + id.hashCode());

            // Don't process if input doesn't have an output
            if(!Config.SERVER.recycleBin.recycleEveryItem.get() && !recipeLookup.containsKey(id))
            {
                this.stopped = true;
                return false;
            }

            // Checks if the recycled items can be added output. The output is cached for later use
            // when completing a recycling process. This means the item will be exactly the same
            // as when initial testing here when adding to the output.
            if(this.inputCache == null || this.inputCache != input.getItem() || this.outputCache == null)
            {
                this.inputCache = input.getItem();
                this.outputCache = this.getRecycledItems(input);
                if(!this.canAddItemsToOutput(this.outputCache))
                {
                    this.stopped = true;
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void setLevel(Level level)
    {
        super.setLevel(level);
        refreshRecipeLookup(level, false);
    }

    @Override
    public void setChanged()
    {
        super.setChanged();
        this.resetCache();
    }

    @Override
    public void setItem(int index, ItemStack stack)
    {
        super.setItem(index, stack);
        if(index >= 1 && index < this.getContainerSize())
        {
            this.output.setItem(index - 1, this.getItem(index));
        }
    }

    @Override
    public void toggle()
    {
        this.enabled = !this.enabled;
        this.setChanged();
        this.sync();
    }

    private void resetCache()
    {
        this.inputCache = null;
        this.outputCache = null;
        this.stopped = false;
    }

    private void updateOutputContainer()
    {
        for(int i = 1; i < this.getContainerSize(); i++)
        {
            this.output.setItem(i - 1, this.getItem(i));
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, RecycleBinBlockEntity recycleBin)
    {
        recycleBin.updatePoweredState();
        recycleBin.setReceivingPower(false);

        boolean processing = recycleBin.processTick();
        if(recycleBin.processing != processing)
        {
            recycleBin.processing = processing;
            recycleBin.sync();
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, RecycleBinBlockEntity recycleBin)
    {
        AudioManager.get().playAudioBlock(recycleBin);
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);
        this.updateOutputContainer();
        if(tag.contains("Powered", Tag.TAG_BYTE))
        {
            this.powered = tag.getBoolean("Powered");
        }
        if(tag.contains("Enabled", Tag.TAG_BYTE))
        {
            this.enabled = tag.getBoolean("Enabled");
        }
        if(tag.contains("ProcessTime", Tag.TAG_INT))
        {
            this.processingTime = tag.getInt("ProcessTime");
        }
        if(tag.contains("Seed", CompoundTag.TAG_LONG))
        {
            this.seed = tag.getLong("Seed");
        }
        if(tag.contains("Processing", CompoundTag.TAG_BYTE))
        {
            this.processing = tag.getBoolean("Processing");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);
        tag.putBoolean("Powered", this.powered);
        tag.putBoolean("Enabled", this.enabled);
        tag.putInt("ProcessTime", this.processingTime);
        tag.putLong("Seed", this.seed);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        CompoundTag tag = new CompoundTag();
        this.writeNodeNbt(tag);
        tag.putBoolean("Powered", this.powered);
        tag.putBoolean("Enabled", this.enabled);
        tag.putBoolean("Processing", this.processing);
        return tag;
    }

    private void sync()
    {
        if(!this.level.isClientSide())
        {
            BlockEntityHelper.sendCustomUpdate(this, this.getUpdateTag());
        }
    }

    /**
     * Performs the recycle action
     */
    private void recycleItem()
    {
        if(this.outputCache == null)
            return;

        ItemStack input = this.getItem(0);
        if(input.isEmpty())
            return;

        input.shrink(1);

        for(ItemStack stack : this.outputCache)
        {
            this.output.addItem(stack);
        }

        // Update the output slots from the output container
        for(int i = 0; i < 9; i++)
        {
            this.setItem(i + 1, this.output.getItem(i));
        }
    }

    /**
     * Gets the item stack that is returned when recycling the given input item stack. If the input
     * item has no recipe, an empty item stack will be returned. The returned item stack may not be
     * the same since the input may have multiple outputs, which is influenced by the {@link #seed}.
     *
     * @param input the input item stack to get the item stack for
     * @return An item stack or empty if no recipe found for the input
     */
    private List<ItemStack> getRecycledItems(ItemStack input)
    {
        List<RecycleBinRecyclingRecipe> recipes = this.getRecyclingRecipes(input.getItem());
        if(recipes == null || recipes.isEmpty())
            return Collections.emptyList();

        Utils.shuffle(recipes, this.random);
        recipes = recipes.subList(0, Math.min(recipes.size(), this.output.getContainerSize()));

        // Create an initial chance based on the config property
        float chance = Config.SERVER.recycleBin.baseOutputChance.get().floatValue();

        // Items that are more damaged should yield a lower chance of returning an item
        if(input.isDamageableItem())
        {
            float damaged = (float) input.getDamageValue() / (float) input.getMaxDamage();
            damaged = 1.0F - Mth.square(1.0F - damaged); // Ramp the damage for balancing
            damaged = 1.0F - damaged; // Invert since the higher the damage, the less chance to recycle
            chance *= Mth.clamp(damaged, 0.0F, 1.0F);; // Finally multiply the chance
        }

        List<ItemStack> items = new ArrayList<>();
        for(RecycleBinRecyclingRecipe recipe : recipes)
        {
            NonNullList<Ingredient> ingredients = recipe.getIngredients();
            if(ingredients.isEmpty())
                continue;

            Ingredient randomIngredient = ingredients.get(this.random.nextInt(ingredients.size()));
            ItemStack[] stacks = randomIngredient.getItems();
            if(stacks.length == 0)
                continue;

            ItemStack randomItem = stacks[this.random.nextInt(stacks.length)];
            if(this.isInvalidItem(randomItem))
                continue;

            // Return an output if lucky
            if(this.random.nextFloat() < chance)
            {
                ItemStack copy = randomItem.copy();
                int count = recipe.getResultItem(this.level.registryAccess()).getCount();
                boolean randomCount = Config.SERVER.recycleBin.randomizeOutputCount.get();
                copy.setCount(!randomCount ? count : this.random.nextIntBetweenInclusive(1, count));
                items.add(copy);
            }
        }
        return items;
    }

    /**
     * Gets the recycling recipe for the given item.
     *
     * @param item the item to get the recipe for
     * @return The recycling recipe for the item or null if none
     */
    @Nullable
    private List<RecycleBinRecyclingRecipe> getRecyclingRecipes(Item item)
    {
        if(this.level == null || recipeLookup == null)
            return null;

        ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
        List<WeakReference<RecycleBinRecyclingRecipe>> list = recipeLookup.get(id);
        if(list == null || list.isEmpty())
            return null;

        List<RecycleBinRecyclingRecipe> recipes = new ObjectArrayList<>();
        for(WeakReference<RecycleBinRecyclingRecipe> recipeRef : list)
        {
            RecycleBinRecyclingRecipe recipe = recipeRef.get();
            if(recipe == null)
            {
                refreshRecipeLookup(this.level, true);
                return this.getRecyclingRecipes(item);
            }
            recipes.add(recipe);
        }
        return recipes;
    }

    /**
     * Determines if the given item stack is an invalid item for the recycling output.
     * An invalid item is generally creative-only, like game tools or unobtainable items based on
     * survival mode.
     *
     * @param stack the item stack to test if invalid
     * @return True if the item is invalid
     */
    private boolean isInvalidItem(ItemStack stack)
    {
        if(stack.isEmpty())
            return true;

        if(stack.getItem() instanceof SpawnEggItem)
            return true;

        // Prevents things like buckets from generating
        if(stack.getItem().hasCraftingRemainingItem())
            return true;

        // TODO config options

        return INVALID_ITEMS.contains(stack.getItem());
    }

    // TODO update docs
    /**
     * Performs a simulation if the given item stack can be added to the output slots.
     * This method creates a temporary container and uses a built-in utility method that
     * adds an item, which the result is the remaining item if any. If empty, it can be added.
     *
     * @param items the item stack for the simulation
     * @return True if can be added to the output
     */
    private boolean canAddItemsToOutput(List<ItemStack> items)
    {
        SimpleContainer copy = new SimpleContainer(this.output.getContainerSize());
        for(int i = 0; i < this.output.getContainerSize(); i++)
        {
            copy.setItem(i, this.output.getItem(i).copy());
        }
        for(ItemStack stack : items)
        {
            if(!copy.addItem(stack.copy()).isEmpty())
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Builds the recipe lookup map for efficient access to the possible recipes that the
     * recycle bin can recycle items into.
     *
     * @param level the level where the recycle bin is located
     * @param force true if it should force a rebuild of the map
     */
    private static void refreshRecipeLookup(Level level, boolean force)
    {
        if(!level.isClientSide() && (recipeLookup == null || force))
        {
            recipeLookup = new HashMap<>();
            List<RecycleBinRecyclingRecipe> recipes = level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.RECYCLE_BIN_RECYCLING.get());
            recipes.forEach(recipe -> {
                if(recipe.isSpecial() || recipe.isIncomplete())
                    return;
                ItemStack input = recipe.getResultItem(level.registryAccess());
                ResourceLocation id = BuiltInRegistries.ITEM.getKey(input.getItem());
                List<WeakReference<RecycleBinRecyclingRecipe>> list = recipeLookup.computeIfAbsent(id, id2 -> new ObjectArrayList<>());
                list.add(new WeakReference<>(recipe));
            });
        }
    }

    public static void clearRecipeLookup()
    {
        recipeLookup = null;
    }
}
