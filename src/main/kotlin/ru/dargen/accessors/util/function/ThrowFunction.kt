package ru.dargen.accessors.util.function

import ru.dargen.accessors.util.AccessException
import java.util.function.Function

interface ThrowFunction<T, R> : Function<T, R> {

    override fun apply(t: T): R {
        try {
            return throwingApply(t)
        } catch (t: Throwable) {
            throw AccessException(t)
        }
    }

    @Throws(Throwable::class)
    fun throwingApply(t: T): R

}

inline fun <T, R> throwFunction(crossinline function: (T) -> R): ThrowFunction<T, R> = object : ThrowFunction<T, R> {
    override fun throwingApply(t: T) = function(t)
}
