package ru.dargen.accessors.member.field

import ru.dargen.accessors.member.AbstractMemberAccessor
import ru.dargen.accessors.member.MemberModifier
import ru.dargen.accessors.util.DescriptorUtil
import ru.dargen.accessors.util.function.throwConsumer
import ru.dargen.accessors.util.function.throwFunction
import java.lang.invoke.MethodHandle
import java.lang.reflect.Field

open class DescriptorFieldAccessor<T>(
    field: Field, val methodHandleGetter: MethodHandle, val methodHandleSetter: MethodHandle,
) : AbstractMemberAccessor<Field>(field), FieldAccessor<T> {

    override fun getValue(instance: Any?): T? {
        return manipulate(throwFunction {
            if (hasModifier(MemberModifier.STATIC)) DescriptorUtil.invokeDescriptor<T>(methodHandleGetter)
            else DescriptorUtil.invokeDescriptor<T>(methodHandleGetter, instance)
        })
    }

    override fun setValue(instance: Any?, value: T?) {
        return manipulate(throwConsumer {
            if (hasModifier(MemberModifier.STATIC)) DescriptorUtil.invokeDescriptor<Void>(methodHandleSetter, value)
            else DescriptorUtil.invokeDescriptor<Void>(methodHandleSetter, instance, value)
        })
    }

}