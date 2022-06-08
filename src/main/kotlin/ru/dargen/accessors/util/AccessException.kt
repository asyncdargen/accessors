package ru.dargen.accessors.util

class AccessException : RuntimeException {

    constructor(throwable: Throwable) : super(throwable)
    constructor(message: String) : super(message)

}