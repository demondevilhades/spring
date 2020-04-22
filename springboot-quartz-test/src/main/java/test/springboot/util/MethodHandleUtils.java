package test.springboot.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;

public class MethodHandleUtils {

    public static Object newInstance(Class<?> clazz) throws Throwable {
        Lookup lookup = MethodHandles.lookup();
        MethodHandle methodHandle = lookup.findConstructor(clazz, MethodType.methodType(void.class));
        return methodHandle.invoke();
    }

    public static Object newInstance(Class<?> clazz, Class<?>[] parameterTypes, Object[] args) throws Throwable {
        Lookup lookup = MethodHandles.lookup();
        MethodHandle methodHandle = lookup.findConstructor(clazz, MethodType.methodType(void.class, parameterTypes));
        return methodHandle.invoke(args);
    }

    public static Object invockMethod(Class<?> clazz, String methodName, Class<?> returnType, Object o)
            throws Throwable {
        MethodHandle methodHandle = MethodHandles.lookup().findVirtual(clazz, methodName,
                MethodType.methodType(returnType));
        return methodHandle.invoke(o);
    }

    public static Object invockMethod(Class<?> clazz, String methodName, Class<?> parameterType, Class<?> returnType,
            Object o, Object arg) throws Throwable {
        MethodHandle methodHandle = MethodHandles.lookup().findVirtual(clazz, methodName,
                MethodType.methodType(returnType, parameterType));
        return methodHandle.invoke(o, arg);
    }

    public static Object invockMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypes, Class<?> returnType,
            Object o, Object[] args) throws Throwable {
        MethodHandle methodHandle = MethodHandles.lookup().findVirtual(clazz, methodName,
                MethodType.methodType(returnType, parameterTypes));
        return methodHandle.invoke(o, args);
    }
}
