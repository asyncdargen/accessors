package ru.dargen.accessors.member.field

import ru.dargen.accessors.member.AbstractMemberAccessor
import ru.dargen.accessors.util.unsafe.UnsafeFieldValueAccessor
import java.lang.reflect.Field

open class UnsafeFieldAccessor<T>(
    field: Field, val fieldOffset: Long, val fieldValueAccessor: UnsafeFieldValueAccessor<T>
) : AbstractMemberAccessor<Field>(field), FieldAccessor<T> {

    override fun getValue(instance: Any?): T? {
        return fieldValueAccessor.getValue(instance!!, fieldOffset)
    }

    override fun setValue(instance: Any?, value: T?) {
        fieldValueAccessor.setValue(instance!!, fieldOffset, value)
    }

}