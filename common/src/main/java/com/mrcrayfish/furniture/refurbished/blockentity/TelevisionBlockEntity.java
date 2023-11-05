package com.mrcrayfish.furniture.refurbished.blockentity;

import com.mrcrayfish.furniture.refurbished.client.audio.AudioManager;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class TelevisionBlockEntity extends ElectricityModuleBlockEntity implements IAudioBlock
{
    public static final Channel WHITE_NOISE = new Channel(Utils.resource("white_noise"), () -> null);
    public static final Channel HEART_SCREENSAVER = new Channel(Utils.resource("heart_screensaver"), () -> null);
    public static final Channel COLOUR_TEST = new Channel(Utils.resource("colour_test"), ModSounds.BLOCK_TELEVISION_CHANNEL_COLOUR_TEST::get);
    public static final List<Channel> VIEWABLE_CHANNELS = List.of(HEART_SCREENSAVER, COLOUR_TEST);
    public static final List<Channel> ALL_CHANNELS = Util.make(new ArrayList<>(), channels -> {
        channels.add(WHITE_NOISE);
        channels.addAll(VIEWABLE_CHANNELS);
    });
    public static final double MAX_AUDIO_DISTANCE = Mth.square(8);

    public Channel currentChannel = COLOUR_TEST;

    public TelevisionBlockEntity(BlockPos $$1, BlockState $$2)
    {
        this(ModBlockEntities.TELEVISION.get(), $$1, $$2);
    }

    public TelevisionBlockEntity(BlockEntityType<?> $$0, BlockPos $$1, BlockState $$2)
    {
        super($$0, $$1, $$2);
    }

    public Channel getCurrentChannel()
    {
        return this.currentChannel;
    }

    @Override
    public SoundEvent getSound()
    {
        return this.currentChannel.sound().get();
    }

    // TODO change to vec3
    @Override
    public BlockPos getAudioPosition()
    {
        return this.worldPosition;
    }

    @Override
    public boolean canPlayAudio()
    {
        return true;
    }

    @Override
    public double getAudioRadiusSqr()
    {
        return MAX_AUDIO_DISTANCE;
    }

    @Override
    public boolean isPowered()
    {
        // TODO implement and create different block model
        return true;
    }

    @Override
    public void setPowered(boolean powered)
    {
        // TODO
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, TelevisionBlockEntity television)
    {
        SoundEvent sound = television.currentChannel.sound().get();
        if(sound != null)
        {
            AudioManager.get().playAudioBlock(television);
        }
    }

    public record Channel(ResourceLocation id, Supplier<SoundEvent> sound) {}
}
