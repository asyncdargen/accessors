package ru.dargen.accessors.member.clazz

import ru.dargen.accessors.AccessorStrategy
import ru.dargen.accessors.member.MemberModifier
import ru.dargen.accessors.member.MemberType

abstract class AbstractClassAccessor<T>(
    override val declaredClass: Class<T>,
    override val strategy: AccessorStrategy
) : ClassAccessor<T> {

    final override val modifiers: Set<MemberModifier> =
        MemberModifier.values().filter { it.testPredicate.test(declaredClass.modifiers) }.toSet()
    final override val accessModifier: MemberModifier =
        modifiers.first(MemberModifier::isVisibleModifier)

    override fun hasModifier(modifier: MemberModifier): Boolean {
        return modifiers.contains(modifier)
    }

    override val classes: Array<Class<*>> get() = declaredClass.classes

    override fun loadAllAccessors() {
        loadAccessors(*MemberType.values())
    }

}