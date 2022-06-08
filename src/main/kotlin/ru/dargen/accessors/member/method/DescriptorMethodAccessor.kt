package ru.dargen.accessors.member.method

import ru.dargen.accessors.member.AbstractMemberAccessor
import ru.dargen.accessors.member.MemberModifier
import ru.dargen.accessors.util.DescriptorUtil
import ru.dargen.accessors.util.function.throwFunction
import java.lang.invoke.MethodHandle
import java.lang.reflect.Method

open class DescriptorMethodAccessor<T>(
    method: Method, val methodHandle: MethodHandle
) : AbstractMemberAccessor<Method>(method), MethodAccessor<T> {

    override fun invoke(instance: Any?, vararg arguments: Any?): T? {
        return manipulate(throwFunction {
            if (hasModifier(MemberModifier.STATIC)) DescriptorUtil.invokeDescriptor<T>(methodHandle, *arguments)
            else DescriptorUtil.invokeDescriptor<T>(methodHandle, instance, *arguments)
        })
    }

}