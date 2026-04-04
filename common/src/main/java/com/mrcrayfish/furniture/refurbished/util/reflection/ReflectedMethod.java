package com.mrcrayfish.furniture.refurbished.util.reflection;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

// TODO replace with Framework version
public final class ReflectedMethod<C, R>
{
    private final Method method;

    public ReflectedMethod(Class<C> targetClass, String method, Class<?> ... parameters)
    {
        try
        {
            this.method = targetClass.getDeclaredMethod(method, parameters);
            this.method.setAccessible(true);
        }
        catch(NoSuchMethodException e)
        {
            throw new RuntimeException(e);
        }
    }

    public ReflectedMethod(String className, String method, Class<?> ... parameters)
    {
        try
        {
            Class<?> targetClass = Class.forName(className);
            this.method = targetClass.getDeclaredMethod(method, parameters);
            this.method.setAccessible(true);
        }
        catch(NoSuchMethodException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public R invoke(@Nullable C obj, Object ... arguments)
    {
        try
        {
            return (R) this.method.invoke(obj, arguments);
        }
        catch(InvocationTargetException | IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
    }
}
