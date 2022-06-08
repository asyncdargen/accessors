package ru.dargen.accessors.member

import ru.dargen.accessors.util.function.ThrowConsumer
import ru.dargen.accessors.util.function.ThrowFunction

interface Manipulable<T> {

    fun manipulate(manipulator: ThrowConsumer<T>)

    fun <R> manipulate(manipulator: ThrowFunction<T, R>): R

}