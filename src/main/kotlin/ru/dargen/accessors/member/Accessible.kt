package ru.dargen.accessors.member

interface Accessible {

    val isAccessOpened: Boolean

    fun openAccess()

    fun closeAccess()

}