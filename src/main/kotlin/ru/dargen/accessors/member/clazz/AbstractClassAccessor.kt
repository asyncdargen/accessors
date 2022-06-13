package ru.dargen.accessors.member.clazz

import ru.dargen.accessors.AccessorStrategy
import ru.dargen.accessors.member.MemberModifier
import ru.dargen.accessors.member.MemberType

abstract class AbstractClassAccessor<T>(
    override val declaredClass: Class<T>,
    override val strategy: AccessorStrategy
) : ClassAccessor<T> {

    final override val modifiers: Set<MemberModifier> =
        MemberModifier.values().filter { it.testPredicate.test(declaredClass.modifiers) }.toSet()
    final override val accessModifier: MemberModifier =
        modifiers.first(MemberModifier::isVisibleModifier)

    override fun hasModifier(modifier: MemberModifier): Boolean {
        return modifiers.contains(modifier)
    }

    //Annotations
    override val annotations: Set<Annotation> = declaredClass.annotations.toSet()

    override fun <A : Annotation> getAnnotation(type: Class<out Annotation>): A? = declaredClass.getDeclaredAnnotation(type) as A?

    override fun <A : Annotation> isAnnotationPresent(type: Class<out Annotation>): Boolean = declaredClass.isAnnotationPresent(type)

    override val classes: Array<Class<*>> get() = declaredClass.classes

    override fun loadAllAccessors() {
        loadAccessors(*MemberType.values())
    }

}