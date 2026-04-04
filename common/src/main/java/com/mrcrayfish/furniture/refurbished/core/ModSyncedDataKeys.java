package com.mrcrayfish.furniture.refurbished.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.sync.Serializers;
import com.mrcrayfish.framework.api.sync.SyncedClassKey;
import com.mrcrayfish.framework.api.sync.SyncedDataKey;
import com.mrcrayfish.furniture.refurbished.entity.Seat;
import com.mrcrayfish.furniture.refurbished.util.Utils;

@RegistryContainer
public class ModSyncedDataKeys
{
    public static final SyncedClassKey<Seat> SEAT = new SyncedClassKey<>(Seat.class, Utils.id("seat"));
    public static final SyncedDataKey<Seat, Boolean> LOCK_YAW = SyncedDataKey.builder(SEAT, Serializers.BOOLEAN)
            .id(Utils.id("lock_yaw"))
            .defaultValueSupplier(() -> false)
            .syncMode(SyncedDataKey.SyncMode.TRACKING_ONLY)
            .saveToFile()
            .build();
}
