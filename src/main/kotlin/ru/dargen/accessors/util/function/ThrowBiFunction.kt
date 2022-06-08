package ru.dargen.accessors.util.function

import ru.dargen.accessors.util.AccessException
import java.util.function.BiFunction

interface ThrowBiFunction<T, U, R> : BiFunction<T, U, R> {

    override fun apply(t: T, u: U): R {
        try {
            return throwingApply(t, u)
        } catch (t: Throwable) {
            throw AccessException(t)
        }
    }

    @Throws(Throwable::class)
    fun throwingApply(t: T, u: U): R

}

inline fun <T, U, R> throwBiFunction(crossinline function: (T, U) -> R): ThrowBiFunction<T, U, R> = object : ThrowBiFunction<T, U, R> {
    override fun throwingApply(t: T, u: U) = function(t, u)
}
