package ru.dargen.accessors.util

import ru.dargen.accessors.Accessors
import ru.dargen.accessors.member.field.FieldAccessor
import ru.dargen.accessors.util.unsafe.UnsafeUtil

typealias JEnum<E> = java.lang.Enum<E>

object EnumUtil {

    private val ENUM_CLASS_ACCESSOR = Accessors.unsafeClassAccessor(JEnum::class.java)
    private val ENUM_VALUES_ACCESSORS: MutableMap<Class<out Enum<*>>, FieldAccessor<Array<out Enum<*>>>> by lazy { HashMap() }

    @JvmStatic
    fun <E : Enum<E>> valuesFieldAccessor(declaredClass: Class<E>): FieldAccessor<Array<E>> {
        return ENUM_VALUES_ACCESSORS.computeIfAbsent(declaredClass) {
            Accessors.invokeFieldAccessor(declaredClass, "\$VALUES")
        } as FieldAccessor<Array<E>>
    }

    @JvmStatic
    fun <E : Enum<E>> values(declaredClass: Class<E>): Array<E> {
        return valuesFieldAccessor(declaredClass).getValue(null)!!
    }

    @JvmStatic
    fun <E : Enum<E>> valueOf(declaredClass: Class<E>, name: String): E {
        return JEnum.valueOf(declaredClass, name)
    }

    @JvmStatic
    fun <E : Enum<E>> valueOfOrNull(declaredClass: Class<E>, name: String): E? {
        return runCatching { valueOf(declaredClass, name) }.getOrNull()
    }

    @JvmStatic
    fun <E : Enum<E>> byOrdinal(declaredClass: Class<E>, ordinal: Int): E {
        return values(declaredClass)[ordinal]
    }

    @JvmStatic
    fun <E : Enum<E>> byOrdinalOrNull(declaredClass: Class<E>, ordinal: Int): E? {
        return runCatching { byOrdinal(declaredClass, ordinal) }.getOrNull()
    }

    @JvmStatic
    @JvmOverloads
    fun <E : Enum<E>> addConstant(declaredClass: Class<E>, name: String, ordinal: Int = values(declaredClass).size): E {
        val newEnumConstant = UnsafeUtil.allocateInstance(declaredClass)

        ENUM_CLASS_ACCESSOR.getFieldAccessor<String>("name").setValue(newEnumConstant, name)
        ENUM_CLASS_ACCESSOR.getFieldAccessor<Int>("ordinal").setValue(newEnumConstant, ordinal)

        val oldValues = values(declaredClass).toMutableList()
        if (ordinal >= oldValues.size) oldValues.add(newEnumConstant)
        else oldValues[ordinal] = newEnumConstant

        val newValues = (oldValues as java.util.Collection<E>).toArray(JavaInternals.newArray(declaredClass, oldValues.size))
        valuesFieldAccessor(declaredClass).setValue(null, newValues)

        return newEnumConstant
    }




}