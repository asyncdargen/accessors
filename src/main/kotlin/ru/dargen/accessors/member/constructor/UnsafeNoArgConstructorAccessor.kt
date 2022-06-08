package ru.dargen.accessors.member.constructor

import ru.dargen.accessors.member.AbstractMemberAccessor
import ru.dargen.accessors.util.unsafe.UnsafeUtil
import ru.dargen.accessors.util.function.throwFunction
import java.lang.reflect.Constructor

open class UnsafeNoArgConstructorAccessor<T>(
    constructor: Constructor<T>
) : AbstractMemberAccessor<Constructor<T>>(constructor), ConstructorAccessor<T> {

    override fun newInstance(vararg arguments: Any?): T {
        return manipulate(throwFunction { UnsafeUtil.allocateInstance(declaredClass as Class<T>) })
    }

}