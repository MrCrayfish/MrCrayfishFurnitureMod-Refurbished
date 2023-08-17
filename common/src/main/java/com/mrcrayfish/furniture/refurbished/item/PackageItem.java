package com.mrcrayfish.furniture.refurbished.item;

import com.mrcrayfish.framework.api.Environment;
import com.mrcrayfish.framework.api.util.EnvironmentHelper;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public class PackageItem extends Item
{
    public PackageItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean canFitInsideContainerItems()
    {
        return false; // Prevent package being put in shulkers, etc.
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> lines, TooltipFlag flag)
    {
        if(level == null)
            return;

        this.loadString(stack, "Sender", s -> {
            lines.add(Utils.translation("gui", "package_sent_by", s).withStyle(ChatFormatting.AQUA));
        });
        this.loadString(stack, "Message", s -> {
            EnvironmentHelper.runOn(Environment.CLIENT, () -> () -> {
                ScreenHelper.splitText(s, 170).forEach(component -> lines.add(component.withStyle(ChatFormatting.GRAY)));
            });
        });
        lines.add(Utils.translation("gui", "package_open").withStyle(ChatFormatting.YELLOW));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);
        if(!level.isClientSide())
        {
            getPackagedItems(stack).forEach(s -> Containers.dropItemStack(level, player.getX(), player.getY(), player.getZ(), s));
        }
        stack.shrink(1);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    /**
     * Gets a list of all the items packaged in the stack.
     *
     * @param stack teh stack to get the items from
     * @return a list of items
     */
    public static List<ItemStack> getPackagedItems(ItemStack stack)
    {
        NonNullList<ItemStack> items = NonNullList.withSize(6, ItemStack.EMPTY);
        CompoundTag tag = stack.getOrCreateTag();
        ContainerHelper.loadAllItems(tag, items);
        return items;
    }

    /**
     * Loads a String from the ItemStack tag and applies the consumer if exists.
     *
     * @param stack    the stack to get the tag
     * @param key      the key of the string
     * @param consumer the callback consumer if the string is found
     */
    private void loadString(ItemStack stack, String key, Consumer<String> consumer)
    {
        CompoundTag tag = stack.getOrCreateTag();
        if(tag.contains(key, Tag.TAG_STRING))
        {
            consumer.accept(tag.getString(key));
        }
    }

    /**
     * Constructs an ItemStack of a package from the given container, message and author.
     *
     * @param container a container instance
     * @param message   an optional string to represent the message
     * @param sender    an optional string to represent the sender
     * @return a new package ItemStack
     */
    public static ItemStack create(Container container, @Nullable String message, @Nullable String sender)
    {
        return create(BlockEntityHelper.nonNullListFromContainer(container), message, sender);
    }

    /**
     * Constructs an ItemStack of a package from the given items, message and author.
     *
     * @param items   a non-null list of items
     * @param message an optional string to represent the message
     * @param sender  an optional string to represent the sender
     * @return a new package ItemStack
     */
    public static ItemStack create(NonNullList<ItemStack> items, @Nullable String message, @Nullable String sender)
    {
        ItemStack stack = new ItemStack(ModItems.PACKAGE.get());
        CompoundTag tag = stack.getOrCreateTag();
        ContainerHelper.saveAllItems(tag, items);
        Optional.ofNullable(message).ifPresent(s -> tag.putString("Message", s));
        Optional.ofNullable(sender).ifPresent(s -> tag.putString("Sender", s));
        return stack;
    }
}
