package ru.dargen.accessors.member.obj

import ru.dargen.accessors.AccessorStrategy
import ru.dargen.accessors.member.clazz.ClassAccessor

interface ObjectAccessor<A> {

    var accessedObject: A
    val strategy: AccessorStrategy
    val declaredClass: Class<A>
    val classAccessor: ClassAccessor<A>

    fun <T> getFieldValue(fieldName: String): T?

    fun setFieldValue(fieldName: String, value: Any?)

    fun <T> invokeMethod(methodName: String, vararg parameters: Any): T?

    fun invokeVoidMethod(methodName: String, vararg parameters: Any)

}