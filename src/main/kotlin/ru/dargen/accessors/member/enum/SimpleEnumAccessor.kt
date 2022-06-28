package ru.dargen.accessors.member.enum

import ru.dargen.accessors.AccessorStrategy
import ru.dargen.accessors.member.MemberModifier
import ru.dargen.accessors.member.MemberType
import ru.dargen.accessors.member.clazz.SimpleClassAccessor
import ru.dargen.accessors.member.field.FieldAccessor
import ru.dargen.accessors.util.EnumUtil

class SimpleEnumAccessor<E : Enum<E>>(
    declaredClass: Class<E>, strategy: AccessorStrategy
) : SimpleClassAccessor<E>(declaredClass, strategy), EnumAccessor<E> {

    init {
        loadAccessors(MemberType.FIELD)
    }

    override val values: Array<E> get() = EnumUtil.values(declaredClass)

    override fun addConstant(name: String, ordinal: Int, vararg fieldValues: Any?): E {
        val newType = EnumUtil.addConstant(declaredClass, name, ordinal)
        return putConstantFields(newType, *fieldValues)
    }

    override fun addConstant(name: String, vararg fieldValues: Any?): E {
        val newType = EnumUtil.addConstant(declaredClass, name)
        return putConstantFields(newType, *fieldValues)
    }

    private fun putConstantFields(newType: E, vararg fieldValues: Any?): E {
        fieldAccessors.values.filter { !it.hasModifier(MemberModifier.STATIC) }
            .forEachIndexed { index, fieldAccessor ->
                fieldValues.getOrNull(index)?.let { (fieldAccessor as FieldAccessor<Any?>).setValue(newType, it) }
            }

        return newType
    }

    override fun valueOf(name: String): E {
        return EnumUtil.valueOf(declaredClass, name)
    }

    override fun valueOfOrNull(name: String): E? {
        return EnumUtil.valueOfOrNull(declaredClass, name)
    }

    override fun byOrdinal(ordinal: Int): E {
        return EnumUtil.byOrdinal(declaredClass, ordinal)
    }

    override fun byOrdinalOrNull(ordinal: Int): E? {
        return EnumUtil.byOrdinalOrNull(declaredClass, ordinal)
    }

}