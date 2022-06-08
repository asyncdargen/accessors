package ru.dargen.accessors.util.function

interface ThreeFunction<T, U, O, R> {

    fun apply(t: T, u: U, o: O): R

}