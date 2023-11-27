package com.mrcrayfish.furniture.refurbished.util;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class LazyValue<T>
{
    private final Supplier<T> initializer;
    private T value;

    public LazyValue(Supplier<T> initializer)
    {
        this.initializer = initializer;
    }
    
    public T get()
    {
        if(this.value == null)
        {
            this.value = this.initializer.get();
        }
        return this.value;
    }
}
