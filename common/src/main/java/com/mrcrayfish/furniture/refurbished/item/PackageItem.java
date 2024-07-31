package com.mrcrayfish.furniture.refurbished.item;

import com.mrcrayfish.framework.api.Environment;
import com.mrcrayfish.framework.api.util.TaskRunner;
import com.mrcrayfish.furniture.refurbished.client.util.ScreenHelper;
import com.mrcrayfish.furniture.refurbished.core.ModDataComponents;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.mail.PackageInfo;
import com.mrcrayfish.furniture.refurbished.util.BlockEntityHelper;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.Nullable;
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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> lines, TooltipFlag flag)
    {
        PackageInfo info = stack.get(ModDataComponents.PACKAGE_INFO.get());
        if(info != null)
        {
            info.sender().ifPresent(s -> {
                lines.add(Utils.translation("gui", "package_sent_by", s).withStyle(ChatFormatting.AQUA));
            });
            info.message().ifPresent(s -> {
                TaskRunner.runIf(Environment.CLIENT, () -> () -> {
                    ScreenHelper.splitText(s, 170).forEach(component -> lines.add(component.withStyle(ChatFormatting.GRAY)));
                });
            });
        }
        lines.add(Utils.translation("gui", "package_open").withStyle(ChatFormatting.YELLOW));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);
        if(!level.isClientSide())
        {
            float pitch = 0.9F + 0.2F * level.random.nextFloat();
            level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.ITEM_PACKAGE_OPEN.get(), SoundSource.PLAYERS, 1.0F, pitch);
            getPackagedItems(stack).stream().forEach(s -> Containers.dropItemStack(level, player.getX(), player.getY(), player.getZ(), s));
            player.setItemInHand(hand, ItemStack.EMPTY);
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
    public static ItemContainerContents getPackagedItems(ItemStack stack)
    {
        return stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
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
        stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(items));
        stack.set(ModDataComponents.PACKAGE_INFO.get(), PackageInfo.create(message, sender));
        return stack;
    }
}
