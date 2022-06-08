package ru.dargen.accessors.member.field

import ru.dargen.accessors.util.unsafe.UnsafeFieldValueAccessor
import java.lang.reflect.Field

open class UnsafeStaticFieldAccessor<T>(
    field: Field, fieldOffset: Long, fieldValueAccessor: UnsafeFieldValueAccessor<T>, val staticBase: Any
) : UnsafeFieldAccessor<T>(field, fieldOffset, fieldValueAccessor) {

    override fun setValue(instance: Any?, value: T?) {
        super.setValue(staticBase, value)
    }

    override fun getValue(instance: Any?): T? {
        return super.getValue(staticBase)
    }

}