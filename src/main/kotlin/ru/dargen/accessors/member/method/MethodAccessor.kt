package ru.dargen.accessors.member.method

import ru.dargen.accessors.member.MemberAccessor
import java.lang.reflect.Method

interface MethodAccessor<T> : MemberAccessor<Method> {

    fun invoke(instance: Any?, vararg arguments: Any?): T?

}