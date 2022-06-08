package ru.dargen.accessors.member.clazz

import ru.dargen.accessors.AccessorStrategy
import ru.dargen.accessors.member.Accessor
import ru.dargen.accessors.member.MemberType
import ru.dargen.accessors.member.constructor.ConstructorAccessor
import ru.dargen.accessors.member.field.FieldAccessor
import ru.dargen.accessors.member.method.MethodAccessor

interface ClassAccessor<T> : Accessor {

//    override val declaredClass: Class<T>
    val strategy: AccessorStrategy
    val classes: Array<Class<*>>

    fun <T> getFieldAccessor(fieldName: String): FieldAccessor<T>

    fun <T> getMethodAccessor(methodName: String, vararg methodArguments: Class<*>): MethodAccessor<T>

    fun getConstructorAccessor(vararg constructorArguments: Class<*>): ConstructorAccessor<T>

    fun loadAccessors(vararg members: MemberType)

    fun loadAllAccessors()

}