package ru.dargen.accessors.member.field

import ru.dargen.accessors.member.AbstractMemberAccessor
import ru.dargen.accessors.util.function.throwConsumer
import ru.dargen.accessors.util.function.throwFunction
import java.lang.reflect.Field

open class ReflectFieldAccessor<T>(field: Field) : AbstractMemberAccessor<Field>(field), FieldAccessor<T> {

    override fun getValue(instance: Any?): T? {
        return manipulate(throwFunction { it[instance] as T? })
    }

    override fun setValue(instance: Any?, value: T?) {
        manipulate(throwConsumer { it[instance] = value })
    }

}