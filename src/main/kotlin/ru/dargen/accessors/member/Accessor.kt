package ru.dargen.accessors.member

interface Accessor {

    val declaredClass: Class<*>

    val modifiers: Set<MemberModifier>
    val accessModifier: MemberModifier

    fun hasModifier(modifier: MemberModifier): Boolean

}