package ru.dargen.accessors.member

import java.lang.reflect.AccessibleObject
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method

enum class MemberType(val declaredClass: Class<out AccessibleObject>) {

    FIELD(Field::class.java),
    METHOD(Method::class.java),
    CONSTRUCTOR(Constructor::class.java);

    companion object {
        val DECLARED_CLASS_TO_TYPE = values().associateBy { it.declaredClass }

        @JvmStatic
        fun byClass(declaredClass: Class<out AccessibleObject>) = DECLARED_CLASS_TO_TYPE[declaredClass]

    }

}