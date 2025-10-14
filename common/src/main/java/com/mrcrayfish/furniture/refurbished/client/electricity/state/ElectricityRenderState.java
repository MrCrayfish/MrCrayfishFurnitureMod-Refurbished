package com.mrcrayfish.furniture.refurbished.client.electricity.state;

import java.util.HashSet;
import java.util.Set;

public class ElectricityRenderState
{
    public LinkingConnectionRenderState link;
    public Set<NodeRenderState> nodes = new HashSet<>();
    public Set<ConnectionRenderState> connections = new HashSet<>();

    public void reset()
    {
        this.link = null;
        this.nodes.clear();
        this.connections.clear();
    }

    public boolean isEmpty()
    {
        return this.link == null && this.nodes.isEmpty() && this.connections.isEmpty();
    }
}
