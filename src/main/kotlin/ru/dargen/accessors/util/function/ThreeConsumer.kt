package ru.dargen.accessors.util.function

interface ThreeConsumer<T, U, O> {

    fun accept(t: T, u: U, o: O)

}