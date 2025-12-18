package com.mrcrayfish.furniture.refurbished.mail;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public record PackageInfo(Optional<String> sender, Optional<String> message)
{
    public static final Codec<PackageInfo> CODEC = RecordCodecBuilder.create(builder -> builder.group(
        Codec.STRING.optionalFieldOf("sender").forGetter(PackageInfo::sender),
        Codec.STRING.optionalFieldOf("message").forGetter(PackageInfo::message)
    ).apply(builder, PackageInfo::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, PackageInfo> STREAM_CODEC = StreamCodec.of((buf, info) -> {
        buf.writeOptional(info.sender, (o, s) -> o.writeUtf(StringUtils.truncate(s, 128), 128));
        buf.writeOptional(info.message, (o, s) -> o.writeUtf(StringUtils.truncate(s, 1024), 1024));
    }, buf -> {
        Optional<String> sender = buf.readOptional(o -> o.readUtf(128));
        Optional<String> message = buf.readOptional(o -> o.readUtf(1024));
        return new PackageInfo(sender, message);
    });

    public static PackageInfo create(@Nullable String message, @Nullable String sender)
    {
        if(message != null) message = StringUtils.truncate(message, 1024);
        if(sender != null) sender = StringUtils.truncate(sender, 128);
        return new PackageInfo(Optional.ofNullable(sender), Optional.ofNullable(message));
    }
}
