package ru.dargen.accessors.member.constructor

import ru.dargen.accessors.member.AbstractMemberAccessor
import ru.dargen.accessors.util.function.throwFunction
import java.lang.reflect.Constructor

open class ReflectConstructorAccessor<T>(constructor: Constructor<T>) : AbstractMemberAccessor<Constructor<T>>(constructor), ConstructorAccessor<T> {

    override fun newInstance(vararg arguments: Any?): T {
        return manipulate(throwFunction { it.newInstance(*arguments) as T })
    }

}