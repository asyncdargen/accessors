package ru.dargen.accessors.member.field

import ru.dargen.accessors.member.MemberAccessor
import ru.dargen.accessors.util.AccessException
import java.lang.reflect.Field

interface FieldAccessor<T> : MemberAccessor<Field> {

    @Throws(AccessException::class)
    fun getValue(instance: Any?): T?

    @Throws(AccessException::class)
    fun setValue(instance: Any?, value: T?)

}