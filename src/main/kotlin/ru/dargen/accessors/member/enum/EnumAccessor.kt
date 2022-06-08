package ru.dargen.accessors.member.enum

import ru.dargen.accessors.member.Accessor
import ru.dargen.accessors.member.clazz.ClassAccessor
import java.rmi.AccessException

interface EnumAccessor<E : Enum<E>> : ClassAccessor<E> {

    val values: Array<E>

    @Throws(AccessException::class)
    fun addConstant(name: String, ordinal: Int, vararg fieldValues: Any?): E

    @Throws(AccessException::class)
    fun addConstant(name: String, vararg fieldValues: Any?): E

    fun valueOf(name: String): E

    fun valueOfOrNull(name: String): E?

    fun byOrdinal(ordinal: Int): E

    fun byOrdinalOrNull(ordinal: Int): E?

}