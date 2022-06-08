package ru.dargen.accessors.util.function

import ru.dargen.accessors.util.AccessException

interface ThrowThreeConsumer<T, U, O> : ThreeConsumer<T, U, O> {

    override fun accept(t: T, u: U, o: O) {
        try {
            throwingAccept(t, u, o)
        } catch (t: Throwable) {
            throw AccessException(t)
        }
    }

    @Throws(Throwable::class)
    fun throwingAccept(t: T, u: U, o: O)

}

inline fun <T, U, O> throwThreeConsumer(crossinline function: (T, U, O) -> Unit): ThrowThreeConsumer<T, U, O> =
    object : ThrowThreeConsumer<T, U, O> {
        override fun throwingAccept(t: T, u: U, o: O) = function(t, u, o)
    }