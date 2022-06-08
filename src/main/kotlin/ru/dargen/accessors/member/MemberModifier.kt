package ru.dargen.accessors.member

import ru.dargen.accessors.member.MemberType.*
import java.lang.reflect.AccessibleObject
import java.lang.reflect.Member
import java.lang.reflect.Modifier
import java.util.function.IntPredicate

enum class MemberModifier(val testPredicate: IntPredicate, vararg val members: MemberType) {

    PUBLIC(Modifier::isPublic, *MemberType.values()),
    PROTECTED(Modifier::isProtected, *MemberType.values()),
    PRIVATE(Modifier::isPrivate, *MemberType.values()),
    DEFAULT({ !Modifier.isPublic(it) && !Modifier.isProtected(it) && !Modifier.isPrivate(it) }, *MemberType.values()),
    ABSTRACT(Modifier::isAbstract, METHOD),
    NATIVE(Modifier::isNative, METHOD),
    FINAL(Modifier::isFinal, METHOD, CONSTRUCTOR),
    STATIC(Modifier::isStatic, FIELD, METHOD),
    SYNCHRONIZED(Modifier::isSynchronized, METHOD),
    VOLATILE(Modifier::isVolatile, FIELD), //Potoka safety
    TRANSIENT(Modifier::isTransient, FIELD) //Not serializable
    ;

    val isVisibleModifier: Boolean get() = ordinal <= 2

    fun isAllowForMember(member: MemberType) = members.contains(member)

    fun isActiveOnMember(accessibleObject: AccessibleObject) =
        isAllowForMember(MemberType.byClass(accessibleObject.javaClass)!!)
                && testPredicate.test((accessibleObject as Member).modifiers)

}

