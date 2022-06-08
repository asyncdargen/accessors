package ru.dargen.accessors.util

object JavaInternals {

    @JvmStatic
    fun <T> newArray(declaredType: Class<T>, size: Int): Array<T> {
        return java.lang.reflect.Array.newInstance(declaredType, size) as Array<T>
    }

}