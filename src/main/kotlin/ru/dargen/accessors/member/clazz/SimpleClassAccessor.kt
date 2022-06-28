package ru.dargen.accessors.member.clazz

import ru.dargen.accessors.AccessorStrategy
import ru.dargen.accessors.member.MemberType
import ru.dargen.accessors.member.constructor.ConstructorAccessor
import ru.dargen.accessors.member.field.FieldAccessor
import ru.dargen.accessors.member.method.MethodAccessor

open class SimpleClassAccessor<T>(declaredClass: Class<T>, strategy: AccessorStrategy) : AbstractClassAccessor<T>(declaredClass, strategy) {

    override val fieldAccessors: MutableMap<String, FieldAccessor<*>> by lazy { HashMap() }
    override val methodAccessors: MutableMap<Pair<String, Array<Class<*>>>, MethodAccessor<*>> by lazy { HashMap() }
    override val constructorAccessors: MutableMap<Array<Class<*>>, ConstructorAccessor<T>> by lazy { HashMap() }

    override fun <T> getFieldAccessor(fieldName: String): FieldAccessor<T> {
        return fieldAccessors.computeIfAbsent(fieldName) {
            strategy.fieldAccessor<T>(declaredClass, fieldName)
        } as FieldAccessor<T>
    }

    override fun <T> getMethodAccessor(methodName: String, vararg methodArguments: Class<*>): MethodAccessor<T> {
        return methodAccessors.computeIfAbsent((methodName to methodArguments) as Pair<String, Array<Class<*>>>) {
            strategy.methodAccessor<T>(declaredClass, methodName, *methodArguments)
        } as MethodAccessor<T>
    }

    override fun getConstructorAccessor(vararg constructorArguments: Class<*>): ConstructorAccessor<T> {
        return constructorAccessors.computeIfAbsent(constructorArguments as Array<Class<*>>) {
            strategy.constructorAccessor(declaredClass, *constructorArguments)
        }
    }

    override fun loadAccessors(vararg members: MemberType) {
        members.forEach {
            when (it) {
                MemberType.CONSTRUCTOR -> if (strategy == AccessorStrategy.UNSAFE) getConstructorAccessor()
                else constructors.forEach { getConstructorAccessor(*it.parameterTypes) }
                MemberType.METHOD -> methods.forEach { getMethodAccessor<Any>(it.name, *it.parameterTypes) }
                MemberType.FIELD -> fields.forEach { getFieldAccessor<Any>(it.name) }
            }
        }
    }

}