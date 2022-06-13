package ru.dargen.accessors.member

import ru.dargen.accessors.util.function.ThrowConsumer
import ru.dargen.accessors.util.function.ThrowFunction
import java.lang.reflect.AccessibleObject
import java.lang.reflect.Member

abstract class AbstractMemberAccessor<T : AccessibleObject>(override val member: T) : MemberAccessor<T> {

    override val declaredClass: Class<*>
        get() = (member as Member).declaringClass

    //Modifiers

    final override val modifiers: Set<MemberModifier> =
        MemberModifier.values().filter { it.isActiveOnMember(member) }.toSet()
    final override val accessModifier: MemberModifier =
        modifiers.first(MemberModifier::isVisibleModifier)

    override fun hasModifier(modifier: MemberModifier): Boolean {
        return modifiers.contains(modifier)
    }

    //Annotations
    override val annotations: Set<Annotation> = member.annotations.toSet()

    override fun <A : Annotation> getAnnotation(type: Class<out Annotation>): A? = member.getDeclaredAnnotation(type) as A?

    override fun <A : Annotation> isAnnotationPresent(type: Class<out Annotation>): Boolean = member.isAnnotationPresent(type)

    //Access

    override val isAccessOpened: Boolean get() = member.isAccessible

    override fun openAccess() {
        member.isAccessible = true
    }

    override fun closeAccess() {
        member.isAccessible = false
    }

    //Manipulable

    override fun <R> manipulate(manipulator: ThrowFunction<T, R>): R {
        if (!isAccessOpened) openAccess()

        return manipulator.apply(member);
    }

    override fun manipulate(manipulator: ThrowConsumer<T>) {
        if (!isAccessOpened) openAccess()

        manipulator.accept(member)
    }

}