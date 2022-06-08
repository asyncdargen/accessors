package ru.dargen.accessors.member

import java.lang.reflect.AccessibleObject

interface MemberAccessor<T : AccessibleObject> : Manipulable<T>, Accessible, Accessor {

    val member: T

}