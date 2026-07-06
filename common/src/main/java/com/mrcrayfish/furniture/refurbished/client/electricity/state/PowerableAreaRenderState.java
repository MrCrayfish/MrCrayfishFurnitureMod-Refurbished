package com.mrcrayfish.furniture.refurbished.client.electricity.state;

import com.mrcrayfish.furniture.refurbished.client.electricity.SimpleQuad;

import java.util.ArrayList;
import java.util.List;

public class PowerableAreaRenderState
{
    public List<SimpleQuad> sides = new ArrayList<>();
    public float alpha;
    public boolean invalid;

    public void reset()
    {
        this.sides = new ArrayList<>();
        this.alpha = 0;
        this.invalid = false;
    }
}
