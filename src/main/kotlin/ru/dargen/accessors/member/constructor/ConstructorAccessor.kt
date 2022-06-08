package ru.dargen.accessors.member.constructor

import ru.dargen.accessors.member.MemberAccessor
import java.lang.reflect.Constructor

interface ConstructorAccessor<T> : MemberAccessor<Constructor<T>> {

    fun newInstance(vararg arguments: Any?): T

}