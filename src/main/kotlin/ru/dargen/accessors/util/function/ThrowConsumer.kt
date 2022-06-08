package ru.dargen.accessors.util.function

import ru.dargen.accessors.util.AccessException
import java.util.function.Consumer

@FunctionalInterface
interface ThrowConsumer<T> : Consumer<T> {

    override fun accept(t: T) {
        try {
            throwingAccept(t)
        } catch (t: Throwable) {
            throw AccessException(t)
        }
    }

    @Throws(Throwable::class)
    fun throwingAccept(t: T)

}

inline fun <T> throwConsumer(crossinline consumer: (T) -> Unit): ThrowConsumer<T> = object : ThrowConsumer<T> {
    override fun throwingAccept(t: T) = consumer(t)
}