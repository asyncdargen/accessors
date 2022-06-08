package ru.dargen.accessors.member.method

import ru.dargen.accessors.member.AbstractMemberAccessor
import ru.dargen.accessors.util.function.throwFunction
import java.lang.reflect.Method

open class ReflectMethodAccessor<T>(method: Method) : AbstractMemberAccessor<Method>(method), MethodAccessor<T> {

    override fun invoke(instance: Any?, vararg arguments: Any?): T? {
        return manipulate(throwFunction { it.invoke(instance, *arguments) as T? })
    }

}