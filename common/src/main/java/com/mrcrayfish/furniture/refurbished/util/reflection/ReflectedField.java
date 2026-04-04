package com.mrcrayfish.furniture.refurbished.util.reflection;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

// TODO replace with Framework version
public final class ReflectedField<C, R>
{
    private final Field field;

    public ReflectedField(Class<C> targetClass, String field)
    {
        try
        {
            this.field = targetClass.getDeclaredField(field);
            this.field.setAccessible(true);
        }
        catch(NoSuchFieldException e)
        {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public R get(@Nullable C obj)
    {
        try
        {
            return (R) this.field.get(obj);
        }
        catch(IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
    }
}
