@file:Suppress("UNCHECKED_CAST")

package ru.dargen.accessors.util

import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method

object ReflectionUtil {

    @JvmStatic
    @Throws(AccessException::class)
    fun getField(declaredClass: Class<*>, fieldName: String): Field {
        try {
            return declaredClass.getDeclaredField(fieldName)
        } catch (e: NoSuchFieldException) {
            throw AccessException(e)
        }
    }

    @JvmStatic
    @Throws(AccessException::class)
    fun <T> getValue(field: Field, instance: Any?): T? {
        try {
            return field[instance] as T?
        } catch (e: Throwable) {
            throw AccessException(e)
        }
    }

    @JvmStatic
    @Throws(AccessException::class)
    fun setValue(field: Field, instance: Any?, value: Any?) {
        try {
            field[instance] = value
        } catch (e: Throwable) {
            throw AccessException(e)
        }
    }

    @JvmStatic
    @Throws(AccessException::class)
    fun <T> getValue(instance: Any, fieldName: String): T? {
        try {
            return getField(instance.javaClass, fieldName)[instance] as T?
        } catch (e: Throwable) {
            throw AccessException(e)
        }
    }

    @JvmStatic
    @Throws(AccessException::class)
    fun setValue(instance: Any, fieldName: String, value: Any?) {
        try {
            getField(instance.javaClass, fieldName)[instance] = value
        } catch (e: Throwable) {
            throw AccessException(e)
        }
    }

    @JvmStatic
    @Throws(AccessException::class)
    fun getMethod(declaredClass: Class<*>, methodName: String, vararg methodArgumentsTypes: Class<*>): Method {
        try {
            return declaredClass.getDeclaredMethod(methodName, *methodArgumentsTypes)
        } catch (e: NoSuchMethodException) {
            throw AccessException(e)
        }
    }

    @JvmStatic
    @Throws(AccessException::class)
    fun <T> getConstructor(declaredClass: Class<*>, vararg constructorArgumentsTypes: Class<*>): Constructor<T> {
        try {
            return declaredClass.getDeclaredConstructor(*constructorArgumentsTypes) as Constructor<T>
        } catch (e: NoSuchMethodException) {
            throw AccessException(e)
        }
    }

    @JvmStatic
    @Throws(AccessException::class)
    fun <T> getNoArgumentsConstructor(declaredClass: Class<*>): Constructor<T> {
        return getConstructor(declaredClass)
    }

    @JvmStatic
    @Throws(AccessException::class)
    fun <T> getClass(classPath: String): Class<T> {
        try {
            return Class.forName(classPath) as Class<T>
        } catch (e: ClassNotFoundException) {
            throw AccessException(e)
        } catch (e: ExceptionInInitializerError) {
            throw AccessException(e)
        }
    }

}