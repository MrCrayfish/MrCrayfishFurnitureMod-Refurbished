package com.mrcrayfish.furniture.refurbished.blockentity;

import com.google.common.base.Preconditions;
import com.mrcrayfish.furniture.refurbished.block.TelevisionBlock;
import com.mrcrayfish.furniture.refurbished.client.audio.AudioManager;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModSounds;
import com.mrcrayfish.furniture.refurbished.electricity.IModuleNode;
import com.mrcrayfish.furniture.refurbished.network.Network;
import com.mrcrayfish.furniture.refurbished.network.message.MessageTelevisionChannel;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class TelevisionBlockEntity extends ElectricityModuleBlockEntity implements ILevelAudio
{
    public static final Channel WHITE_NOISE = new Channel(Utils.resource("white_noise"), ModSounds.BLOCK_TELEVISION_CHANNEL_WHITE_NOISE::get, 0);
    public static final Channel BLACK_NOISE = new Channel(Utils.resource("black_noise"), ModSounds.BLOCK_TELEVISION_CHANNEL_WHITE_NOISE::get, 0);
    public static final Channel HEART_SCREENSAVER = new Channel(Utils.resource("heart_screensaver"), () -> null, 10);
    public static final Channel COLOUR_TEST = new Channel(Utils.resource("colour_test"), ModSounds.BLOCK_TELEVISION_CHANNEL_COLOUR_TEST::get, 1);
    public static final Channel HEROBRINE = new Channel(Utils.resource("herobrine"), ModSounds.BLOCK_TELEVISION_CHANNEL_COLOUR_TEST::get, 0);
    public static final Channel DANCE_MUSIC = new Channel(Utils.resource("dance_music"), ModSounds.BLOCK_TELEVISION_CHANNEL_DANCE_MUSIC::get, 10);
    public static final Channel VILLAGER_NEWS = new Channel(Utils.resource("villager_news"), ModSounds.BLOCK_TELEVISION_CHANNEL_VILLAGER_NEWS::get, 10);
    public static final Channel RIP_BLIZZARD = new Channel(Utils.resource("rip_blizzard"), ModSounds.BLOCK_TELEVISION_CHANNEL_CHIRP_SONG::get, 5);
    public static final Channel OCEAN_SUNSET = new Channel(Utils.resource("ocean_sunset"), ModSounds.BLOCK_TELEVISION_CHANNEL_OCEAN_SUNSET::get, 10);
    public static final Channel BLOCK_GAME = new Channel(Utils.resource("block_game"), ModSounds.BLOCK_TELEVISION_CHANNEL_BLOCKY_GAME::get, 10);
    public static final Channel PONG = new Channel(Utils.resource("pong"), ModSounds.BLOCK_TELEVISION_CHANNEL_RETRO_SONG::get, 10);
    public static final Channel SILLY_FACE = new Channel(Utils.resource("silly_face"), () -> null, 10);
    public static final List<Channel> VIEWABLE_CHANNELS = List.of(HEART_SCREENSAVER, COLOUR_TEST, DANCE_MUSIC, VILLAGER_NEWS, RIP_BLIZZARD, OCEAN_SUNSET, BLOCK_GAME, PONG, SILLY_FACE);
    public static final List<Channel> ALL_CHANNELS = Util.make(new ArrayList<>(), channels -> {
        channels.add(WHITE_NOISE);
        channels.add(BLACK_NOISE);
        channels.add(HEROBRINE);
        channels.addAll(VIEWABLE_CHANNELS);
    });
    public static final Map<ResourceLocation, Channel> ID_TO_CHANNEL = ALL_CHANNELS.stream().collect(Collectors.toMap(c -> c.id, Function.identity()));
    public static final double MAX_AUDIO_DISTANCE = Mth.square(16);

    protected final Vec3 audioPosition;
    protected Channel currentChannel = OCEAN_SUNSET;
    protected Channel lastChannel;
    protected boolean transitioning;
    protected int timer;

    public TelevisionBlockEntity(BlockPos pos, BlockState state)
    {
        this(ModBlockEntities.TELEVISION.get(), pos, state);
    }

    public TelevisionBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
        this.audioPosition = pos.getCenter();
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

    @Override
    public SoundSource getSource()
    {
        return SoundSource.BLOCKS;
    }

    @Override
    public Vec3 getAudioPosition()
    {
        BlockState state = this.getBlockState();
        if(state.hasProperty(TelevisionBlock.DIRECTION))
        {
            Direction direction = state.getValue(TelevisionBlock.DIRECTION).getOpposite();
            Vec3i normal = direction.getNormal();
            return this.audioPosition.add(normal.getX() * 0.375, normal.getY(), normal.getZ() * 0.375);
        }
        return this.audioPosition;
    }

    @Override
    public boolean canPlayAudio()
    {
        return !this.isRemoved() && this.isNodePowered();
    }

    @Override
    public double getAudioRadiusSqr()
    {
        return MAX_AUDIO_DISTANCE;
    }

    @Override
    public float getAudioPitch()
    {
        if(this.currentChannel == HEROBRINE || this.currentChannel == BLACK_NOISE)
        {
            return 0.25F;
        }
        return 1.0F;
    }

    @Override
    public int getAudioHash()
    {
        return this.worldPosition.hashCode();
    }

    @Override
    public boolean isAudioEqual(ILevelAudio other)
    {
        return other == this;
    }

    @Override
    public boolean isNodePowered()
    {
        BlockState state = this.getBlockState();
        return state.hasProperty(BlockStateProperties.POWERED) && state.getValue(BlockStateProperties.POWERED);
    }

    @Override
    public void setNodePowered(boolean powered)
    {
        BlockState state = this.getBlockState();
        if(state.hasProperty(BlockStateProperties.POWERED))
        {
            this.level.setBlock(this.worldPosition, state.setValue(BlockStateProperties.POWERED, powered), Block.UPDATE_ALL);
        }
    }

    public void interact()
    {
        if(!this.transitioning && this.isNodePowered())
        {
            Preconditions.checkState(this.level instanceof ServerLevel);
            int transitionTime = this.level.random.nextInt(5, 20);
            this.level.scheduleTick(this.worldPosition, this.getBlockState().getBlock(), transitionTime);
            this.lastChannel = this.currentChannel;
            this.setChannel(WHITE_NOISE);
            this.transitioning = true;
        }
    }

    public void selectRandomChannel()
    {
        Preconditions.checkState(this.level instanceof ServerLevel);

        if(this.level.dimension() == Level.OVERWORLD && this.worldPosition.getY() <= 0)
        {
            this.setChannel(COLOUR_TEST);
            this.transitioning = false;
            return;
        }

        List<Channel> channels = new ArrayList<>(VIEWABLE_CHANNELS);
        channels.remove(this.lastChannel); // Don't select the current channel
        int totalWeight = channels.stream().mapToInt(Channel::weight).sum();
        int randomIndex = 0;
        for(int i = this.level.random.nextIntBetweenInclusive(0, totalWeight); randomIndex < channels.size() - 1; randomIndex++)
        {
            i -= channels.get(randomIndex).weight();
            if(i < 0) break;
        }
        this.setChannel(channels.get(randomIndex));
        this.transitioning = false;
    }

    private void setChannel(Channel channel)
    {
        Preconditions.checkNotNull(this.level);
        this.currentChannel = channel;
        this.setChanged();
        if(!this.level.isClientSide())
        {
            Network.getPlay().sendToTrackingBlockEntity(() -> this, new MessageTelevisionChannel(this.worldPosition, channel.id));
        }
    }

    public void setChannelFromId(ResourceLocation id)
    {
        Channel channel = ID_TO_CHANNEL.get(id);
        if(channel != null)
        {
            this.setChannel(channel);
        }
    }

    private void specialTick()
    {
        Preconditions.checkNotNull(this.level);
        if(this.isNodePowered() && this.level.dimension() == Level.OVERWORLD && this.worldPosition.getY() <= 0)
        {
            if(this.currentChannel == COLOUR_TEST)
            {
                if(this.timer++ >= 200)
                {
                    this.setChannel(HEROBRINE);
                    this.timer = 0;
                }
            }
            else if(this.currentChannel == HEROBRINE)
            {
                if(this.timer++ == 100)
                {
                    this.setChannel(BLACK_NOISE);
                    this.timer = 0;
                }
            }
            return;
        }
        this.timer = 0;
    }

    @Override
    public void moduleTick(Level level)
    {
        super.moduleTick(level);
        if(!level.isClientSide)
        {
            this.specialTick();
        }
        else
        {
            AudioManager.get().playLevelAudio(this);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider)
    {
        super.loadAdditional(tag, provider);
        if(tag.contains("CurrentChannel", Tag.TAG_STRING))
        {
            ResourceLocation id = ResourceLocation.tryParse(tag.getString("CurrentChannel"));
            if(id != null && !id.equals(WHITE_NOISE.id) && ID_TO_CHANNEL.containsKey(id))
            {
                this.currentChannel = ID_TO_CHANNEL.get(id);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider)
    {
        super.saveAdditional(tag, provider);
        if(this.currentChannel != null && this.currentChannel != WHITE_NOISE)
        {
            tag.putString("CurrentChannel", this.currentChannel.id.toString());
        }
    }

    public record Channel(ResourceLocation id, Supplier<SoundEvent> sound, int weight) {}
}
