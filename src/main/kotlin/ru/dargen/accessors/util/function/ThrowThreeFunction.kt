package ru.dargen.accessors.util.function

import ru.dargen.accessors.util.AccessException

interface ThrowThreeFunction<T, U, O, R> : ThreeFunction<T, U, O, R> {

    override fun apply(t: T, u: U, o: O): R {
        try {
            return throwingApply(t, u, o)
        } catch (t: Throwable) {
            throw AccessException(t)
        }
    }

    @Throws(Throwable::class)
    fun throwingApply(t: T, u: U, o: O): R

}

inline fun <T, U, O, R> throwThreeFunction(crossinline function: (T, U, O) -> R): ThrowThreeFunction<T, U, O, R> =
    object : ThrowThreeFunction<T, U, O, R> {
        override fun throwingApply(t: T, u: U, o: O) = function(t, u, o)
    }