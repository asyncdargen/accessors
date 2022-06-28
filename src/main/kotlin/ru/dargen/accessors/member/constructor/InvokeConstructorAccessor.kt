package ru.dargen.accessors.member.constructor

import ru.dargen.accessors.member.AbstractMemberAccessor
import ru.dargen.accessors.util.InvokeUtil
import ru.dargen.accessors.util.function.throwFunction
import java.lang.invoke.MethodHandle
import java.lang.reflect.Constructor

open class InvokeConstructorAccessor<T>(
    constructor: Constructor<T>, val methodHandle: MethodHandle
) : AbstractMemberAccessor<Constructor<T>>(constructor), ConstructorAccessor<T> {

    override fun newInstance(vararg arguments: Any?): T {
        return manipulate(throwFunction { InvokeUtil.invokeDescriptor<T>(methodHandle, *arguments)!! })
    }

}