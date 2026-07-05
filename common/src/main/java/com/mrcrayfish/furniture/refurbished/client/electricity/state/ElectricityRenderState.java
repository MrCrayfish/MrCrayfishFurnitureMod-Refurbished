package com.mrcrayfish.furniture.refurbished.client.electricity.state;

import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class ElectricityRenderState
{
    public @Nullable LinkingConnectionRenderState linkingConnectionRenderState;
    public final Set<NodeRenderState> nodeRenderStates = new HashSet<>();
    public final Set<ConnectionRenderState> connectionRenderStates = new HashSet<>();

    public void reset()
    {
        this.linkingConnectionRenderState = null;
        this.nodeRenderStates.clear();
        this.connectionRenderStates.clear();
    }

    public boolean isEmpty()
    {
        return this.linkingConnectionRenderState == null && this.nodeRenderStates.isEmpty() && this.connectionRenderStates.isEmpty();
    }
}
