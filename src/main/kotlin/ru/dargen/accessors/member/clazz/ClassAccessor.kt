package ru.dargen.accessors.member.clazz

import ru.dargen.accessors.AccessorStrategy
import ru.dargen.accessors.member.Accessor
import ru.dargen.accessors.member.MemberType
import ru.dargen.accessors.member.constructor.ConstructorAccessor
import ru.dargen.accessors.member.field.FieldAccessor
import ru.dargen.accessors.member.method.MethodAccessor
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method

interface ClassAccessor<T> : Accessor {

//    override val declaredClass: Class<T>
    override val declaredClass: Class<T>
    val strategy: AccessorStrategy

    val fieldAccessors: Map<String, FieldAccessor<*>>
    val methodAccessors: Map<Pair<String, Array<Class<*>>>, MethodAccessor<*>>
    val constructorAccessors: Map<Array<Class<*>>, ConstructorAccessor<T>>
    
    val innerClasses: Array<Class<*>>
    val fields: Array<Field>
    val methods: Array<Method>
    val constructors: Array<Constructor<T>>

    fun getField(fieldName: String): Field

    fun getMethod(methodName: String, vararg methodArguments: Class<*>): Method

    fun getConstructor(vararg constructorArguments: Class<*>): Constructor<T>

    fun getInnerClass(className: String): Class<*>

    fun <T> getFieldAccessor(fieldName: String): FieldAccessor<T>

    fun <T> getMethodAccessor(methodName: String, vararg methodArguments: Class<*>): MethodAccessor<T>

    fun getConstructorAccessor(vararg constructorArguments: Class<*>): ConstructorAccessor<T>

    fun loadAccessors(vararg members: MemberType)

    fun loadAllAccessors()

}