package com.mrcrayfish.furniture.refurbished.blockentity;

import com.google.common.collect.ImmutableSet;
import com.mrcrayfish.furniture.refurbished.Config;
import com.mrcrayfish.furniture.refurbished.client.audio.AudioManager;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.inventory.BuildableContainerData;
import com.mrcrayfish.furniture.refurbished.inventory.RecyclingBinMenu;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
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
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
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
// TODO forge version with capabilities
public class RecyclingBinBlockEntity extends ElectricityModuleLootBlockEntity implements IProcessingBlock, IPowerSwitch, IAudioBlock
{
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

    // TODO Convert value to list
    private static Map<ResourceLocation, WeakReference<CraftingRecipe>> recipeLookup;

    protected RandomSource random = RandomSource.create();
    protected SimpleContainer output = new SimpleContainer(9);
    protected boolean powered;
    protected boolean processing;
    protected int processingTime;
    protected boolean enabled;
    protected boolean full;
    protected long seed = this.random.nextLong();
    protected Item lastItem;
    protected List<ItemStack> outputCache;

    protected final ContainerData data = new BuildableContainerData(builder -> {
        builder.add(DATA_ENABLED, () -> enabled ? 1 : 0, value -> {});
        builder.add(DATA_POWERED, () -> powered ? 1 : 0, value -> {});
        builder.add(DATA_PROCESSING_TIME, () -> processingTime, value -> {});
    });

    public RecyclingBinBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.RECYCLING_BIN.get(), pos, state);
    }

    public RecyclingBinBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
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
        return menu instanceof RecyclingBinMenu recyclingBin && recyclingBin.getContainer() == this;
    }

    @Override
    protected Component getDefaultName()
    {
        return Utils.translation("container", "recycling_bin");
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory)
    {
        return new RecyclingBinMenu(windowId, playerInventory, this, this.data);
    }

    @Override
    public SoundEvent getSound()
    {
        return ModSounds.BLOCK_RECYCLING_BIN_ENGINE.get();
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
        return Config.SERVER.recyclingBin.processingTime.get();
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
        this.recycleItems();
        this.seed = this.level.random.nextLong();
        this.outputCache = null;
        this.full = false;
    }

    @Override
    public boolean canProcess()
    {
        if(!this.full && this.enabled && this.isPowered())
        {
            ItemStack input = this.getItem(0);
            if(input.isEmpty())
                return false;

            // A unique seed per item
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(input.getItem());
            this.random.setSeed(this.seed + id.hashCode());

            // Check if the next result can be added to the output.
            // We don't check for invalid items until completing a recycle process
            if(this.lastItem == null || this.lastItem != input.getItem() || this.outputCache == null)
            {
                CraftingRecipe recipe = this.getRecipe(input.getItem());
                this.outputCache = this.getRandomItems(recipe, input);
                if(!this.outputCache.isEmpty() && !this.canAddItemsToOutput(this.outputCache))
                {
                    this.full = true;
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void setChanged()
    {
        super.setChanged();
        this.full = false;
        this.outputCache = null;
    }

    @Override
    public void setLevel(Level level)
    {
        super.setLevel(level);
        refreshRecipeLookup(this.level, false);
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

    private void updateOutputContainer()
    {
        for(int i = 1; i < this.getContainerSize(); i++)
        {
            this.output.setItem(i - 1, this.getItem(i));
        }
    }

    public void recycleItems()
    {
        if(recipeLookup == null)
            return;

        if(this.outputCache == null)
            return;

        ItemStack input = this.getItem(0);
        if(input.isEmpty())
            return;

        input.shrink(1);

        for(ItemStack stack : this.outputCache)
        {
            this.output.addItem(stack.copy());
        }

        for(int i = 0; i < 9; i++)
        {
            ItemStack stack = this.output.getItem(i);
            this.setItem(i + 1, stack);
        }

        this.setChanged();
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, RecyclingBinBlockEntity recyclingBin)
    {
        recyclingBin.updatePoweredState();
        recyclingBin.setReceivingPower(false);

        boolean processing = recyclingBin.processTick();
        if(recyclingBin.processing != processing)
        {
            recyclingBin.processing = processing;
            recyclingBin.sync();
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, RecyclingBinBlockEntity recyclingBin)
    {
        AudioManager.get().playAudioBlock(recyclingBin);
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

    private List<ItemStack> getRandomItems(@Nullable CraftingRecipe recipe, ItemStack input)
    {
        if(recipe == null)
            return Collections.emptyList();

        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        if(ingredients.isEmpty())
            return Collections.emptyList();

        // Create an initial chance based on the result count of the recipe
        float chance = 1.0F / recipe.getResultItem(this.level.registryAccess()).getCount();

        // Items that are more damaged should yield a lower chance of returning an item
        if(input.isDamageableItem())
        {
            float damageScale = 1.0F - Mth.clamp((float) input.getDamageValue() / (float) input.getMaxDamage(), 0.0F, 1.0F);
            chance *= damageScale;
        }

        // Finally recycling should have diminishing returns. Half the chance
        chance /= 2.0F;

        List<ItemStack> randomItems = new ArrayList<>(ingredients.size() + 1);
        for(Ingredient ingredient : ingredients)
        {
            if(this.random.nextFloat() >= chance)
                continue;

            ItemStack[] items = ingredient.getItems();
            if(items.length == 0)
                continue;

            ItemStack randomStack = items[this.random.nextInt(items.length)];
            if(!this.isInvalidItem(randomStack) && randomStack.getItem() != input.getItem())
            {
                randomItems.add(randomStack);
            }
        }
        return randomItems;
    }

    private CraftingRecipe getRecipe(Item item)
    {
        if(this.level == null)
            return null;

        ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
        WeakReference<CraftingRecipe> recipeRef = recipeLookup.get(id);
        if(recipeRef == null)
            return null;

        CraftingRecipe recipe = recipeRef.get();
        if(recipe != null)
            return recipe;

        refreshRecipeLookup(this.level, true);
        recipeRef = recipeLookup.get(id);
        return recipeRef != null ? recipeRef.get() : null;
    }

    private boolean isInvalidItem(ItemStack stack)
    {
        if(stack.getItem() instanceof SpawnEggItem)
            return true;

        // Prevents things like buckets from generating
        if(stack.getItem().hasCraftingRemainingItem())
            return true;

        // TODO config options

        return INVALID_ITEMS.contains(stack.getItem());
    }

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

    private static void refreshRecipeLookup(Level level, boolean force)
    {
        if(!level.isClientSide() && (recipeLookup == null || force))
        {
            recipeLookup = new HashMap<>();
            List<CraftingRecipe> recipes = level.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING);
            recipes.forEach(recipe -> {
                if(!recipe.isSpecial() && !recipe.isIncomplete()) {
                    ItemStack result = recipe.getResultItem(level.registryAccess());
                    ResourceLocation id = BuiltInRegistries.ITEM.getKey(result.getItem());
                    recipeLookup.putIfAbsent(id, new WeakReference<>(recipe));
                }
            });
        }
    }

    public static void clearRecipeLookup()
    {
        recipeLookup = null;
    }
}
