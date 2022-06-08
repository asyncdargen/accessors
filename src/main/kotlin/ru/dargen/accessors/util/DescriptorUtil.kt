package ru.dargen.accessors.util

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles.Lookup
import java.lang.invoke.MethodType
import java.lang.invoke.WrongMethodTypeException
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method

object DescriptorUtil {

    @JvmField
    val LOOKUP: Lookup = Lookup::class.java.getDeclaredField("IMPL_LOOKUP").runCatching {
        isAccessible = true
        this[null] as Lookup
    }.getOrElse { throw AccessException(it) }

    @JvmStatic
    @Throws(AccessException::class)
    fun findGetter(declaredClass: Class<*>, fieldName: String, fieldType: Class<*>): MethodHandle {
        try {
            return LOOKUP.findGetter(declaredClass, fieldName, fieldType)
        } catch (t: Throwable) {
            throw AccessException(t)
        }
    }

    @JvmStatic
    @Throws(AccessException::class)
    fun findStaticGetter(declaredClass: Class<*>, fieldName: String, fieldType: Class<*>): MethodHandle {
        try {
            return LOOKUP.findStaticGetter(declaredClass, fieldName, fieldType)
        } catch (t: Throwable) {
            throw AccessException(t)
        }
    }

    @JvmStatic
    @Throws(AccessException::class)
    fun unreflectGetter(field: Field): MethodHandle {
        try {
            return LOOKUP.unreflectGetter(field)
        } catch (t: Throwable) {
            throw AccessException(t)
        }
    }

    @JvmStatic
    @Throws(AccessException::class)
    fun findSetter(declaredClass: Class<*>, fieldName: String, fieldType: Class<*>): MethodHandle {
        try {
            return LOOKUP.findSetter(declaredClass, fieldName, fieldType)
        } catch (t: Throwable) {
            throw AccessException(t)
        }
    }

    @JvmStatic
    @Throws(AccessException::class)
    fun findStaticSetter(declaredClass: Class<*>, fieldName: String, fieldType: Class<*>): MethodHandle {
        try {
            return LOOKUP.findStaticSetter(declaredClass, fieldName, fieldType)
        } catch (t: Throwable) {
            throw AccessException(t)
        }
    }

    @JvmStatic
    @Throws(AccessException::class)
    fun unreflectSetter(field: Field): MethodHandle {
        try {
            return LOOKUP.unreflectSetter(field)
        } catch (t: Throwable) {
            throw AccessException(t)
        }
    }

    @JvmStatic
    @Throws(AccessException::class)
    fun findConstructor(declaredClass: Class<*>, vararg constructorArgumentsTypes: Class<*>): MethodHandle {
        try {
            return LOOKUP.findConstructor(declaredClass, MethodType.methodType(Void.TYPE, constructorArgumentsTypes))
        } catch (t: Throwable) {
            throw AccessException(t)
        }
    }

    @JvmStatic
    @Throws(AccessException::class)
    fun unreflectConstructor(constructor: Constructor<*>): MethodHandle {
        try {
            return LOOKUP.unreflectConstructor(constructor)
        } catch (t: Throwable) {
            throw AccessException(t)
        }
    }

    @JvmStatic
    @Throws(AccessException::class)
    fun findVirtual(declaredClass: Class<*>, methodName: String, returnType: Class<*>, vararg methodArgumentsTypes: Class<*>): MethodHandle {
        try {
            return LOOKUP.findStatic(declaredClass, methodName, MethodType.methodType(returnType, methodArgumentsTypes))
        } catch (t: Throwable) {
            throw AccessException(t)
        }
    }

    @JvmStatic
    @Throws(AccessException::class)
    fun findStatic(declaredClass: Class<*>, methodName: String, returnType: Class<*>, vararg methodArgumentsTypes: Class<*>): MethodHandle {
        try {
            return LOOKUP.findVirtual(declaredClass, methodName, MethodType.methodType(returnType, methodArgumentsTypes))
        } catch (t: Throwable) {
            throw AccessException(t)
        }
    }

    @JvmStatic
    @Throws(AccessException::class)
    fun unreflectMethod(method: Method): MethodHandle {
        try {
            return LOOKUP.unreflect(method)
        } catch (t: Throwable) {
            throw AccessException(t)
        }
    }

    @JvmStatic
    @Throws(AccessException::class)
    fun <T> invokeDescriptor(methodHandle: MethodHandle, vararg arguments: Any?): T? {
        try {
            return methodHandle.invokeWithArguments(*arguments) as T?
        } catch (e: WrongMethodTypeException) {
            throw AccessException(e)
        } catch (e: java.lang.ClassCastException) {
            throw AccessException(e)
        }
    }

}