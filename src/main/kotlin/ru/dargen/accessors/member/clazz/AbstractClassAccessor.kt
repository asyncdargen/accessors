package ru.dargen.accessors.member.clazz

import ru.dargen.accessors.AccessorStrategy
import ru.dargen.accessors.member.MemberModifier
import ru.dargen.accessors.member.MemberType
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method

abstract class AbstractClassAccessor<T>(
    override val declaredClass: Class<T>,
    override val strategy: AccessorStrategy
) : ClassAccessor<T> {

    final override val modifiers: Set<MemberModifier> =
        MemberModifier.values().filter { it.testPredicate.test(declaredClass.modifiers) }.toSet()
    final override val accessModifier: MemberModifier =
        modifiers.firstOrNull(MemberModifier::isVisibleModifier) ?: MemberModifier.PUBLIC

    override fun hasModifier(modifier: MemberModifier): Boolean {
        return modifiers.contains(modifier)
    }

    //Annotations
    override val annotations: Set<Annotation> = declaredClass.annotations.toSet()

    override fun <A : Annotation> getAnnotation(type: Class<out Annotation>): A? = declaredClass.getDeclaredAnnotation(type) as A?

    override fun <A : Annotation> isAnnotationPresent(type: Class<out Annotation>): Boolean = declaredClass.isAnnotationPresent(type)

    override val innerClasses: Array<Class<*>> = declaredClass.declaredClasses
    override val fields: Array<Field> = declaredClass.declaredFields
    override val methods: Array<Method> = declaredClass.declaredMethods
    override val constructors: Array<Constructor<T>> = declaredClass.declaredConstructors as Array<Constructor<T>>

    override fun getField(fieldName: String): Field {
        return declaredClass.getDeclaredField(fieldName)
    }

    override fun getMethod(methodName: String, vararg methodArguments: Class<*>): Method {
        return declaredClass.getDeclaredMethod(methodName, *methodArguments)
    }

    override fun getConstructor(vararg constructorArguments: Class<*>): Constructor<T> {
        return declaredClass.getDeclaredConstructor(*constructorArguments)
    }

    override fun getInnerClass(className: String): Class<*> {
        return innerClasses.first { it.simpleName == className }
    }

    override fun loadAllAccessors() {
        loadAccessors(*MemberType.values())
    }

}