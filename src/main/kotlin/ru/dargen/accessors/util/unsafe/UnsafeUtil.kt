@file:Suppress("UNCHECKED_CAST")

package ru.dargen.accessors.util.unsafe

import ru.dargen.accessors.member.MemberModifier
import ru.dargen.accessors.util.AccessException
import sun.misc.Unsafe
import java.lang.reflect.Field

object UnsafeUtil {

    @JvmField
    val INT_FIELD_ACCESSOR = unsafeFieldValueAccessor(UnsafeUtil::putInt, UnsafeUtil::getInt)
    @JvmField
    val BYTE_FIELD_ACCESSOR = unsafeFieldValueAccessor(UnsafeUtil::putByte, UnsafeUtil::getByte)
    @JvmField
    val DOUBLE_FIELD_ACCESSOR = unsafeFieldValueAccessor(UnsafeUtil::putDouble, UnsafeUtil::getDouble)
    @JvmField
    val FLOAT_FIELD_ACCESSOR = unsafeFieldValueAccessor(UnsafeUtil::putFloat, UnsafeUtil::getFloat)
    @JvmField
    val LONG_FIELD_ACCESSOR = unsafeFieldValueAccessor(UnsafeUtil::putLong, UnsafeUtil::getLong)
    @JvmField
    val CHAR_FIELD_ACCESSOR = unsafeFieldValueAccessor(UnsafeUtil::putChar, UnsafeUtil::getChar)
    @JvmField
    val BOOLEAN_FIELD_ACCESSOR = unsafeFieldValueAccessor(UnsafeUtil::putBoolean, UnsafeUtil::getBoolean)
    @JvmField
    val SHORT_FIELD_ACCESSOR = unsafeFieldValueAccessor(UnsafeUtil::putShort, UnsafeUtil::getShort)
    @JvmField
    val OBJECT_FIELD_ACCESSOR = unsafeNullableFieldValueAccessor(UnsafeUtil::putObject, UnsafeUtil::getObject)

    @JvmField
    val UNSAFE = Unsafe::class.java.getDeclaredField("theUnsafe").run {
        isAccessible = true
        this[null] as Unsafe
    }

    @JvmStatic
    @Throws(AccessException::class)
    fun <T> allocateInstance(declaredClass: Class<T>): T {
        try {
            return UNSAFE.allocateInstance(declaredClass) as T
        } catch (e: InstantiationException) {
            throw AccessException(e)
        }
    }

    @JvmStatic
    @Throws(AccessException::class)
    fun getFieldOffset(field: Field): Long {
        try {
            return if (MemberModifier.STATIC.isActiveOnMember(field)) UNSAFE.staticFieldOffset(field)
            else UNSAFE.objectFieldOffset(field)
        } catch (e: IllegalStateException) {
            throw AccessException(e)
        }
    }

    @JvmStatic
    @Throws(AccessException::class)
    fun getFieldBase(field: Field): Any {
        try {
            return UNSAFE.staticFieldBase(field)
        } catch (e: IllegalStateException) {
            throw AccessException(e)
        }
    }

    @JvmStatic
    fun getFieldValueAccessor(field: Field): UnsafeFieldValueAccessor<*> {
        return when (field.type) {
            java.lang.Integer::class.java, Int::class.java -> INT_FIELD_ACCESSOR
            java.lang.Byte::class.java, Byte::class.java -> BYTE_FIELD_ACCESSOR
            java.lang.Short::class.java, Short::class.java -> SHORT_FIELD_ACCESSOR
            java.lang.Long::class.java, Long::class.java -> LONG_FIELD_ACCESSOR
            java.lang.Double::class.java, Double::class.java -> DOUBLE_FIELD_ACCESSOR
            java.lang.Float::class.java, Float::class.java -> FLOAT_FIELD_ACCESSOR
            java.lang.Character::class.java, Char::class.java -> CHAR_FIELD_ACCESSOR
            java.lang.Boolean::class.java, Boolean::class.java-> BOOLEAN_FIELD_ACCESSOR
            else -> OBJECT_FIELD_ACCESSOR
        }
    }

    @JvmStatic
    fun putAddress(address: Long, value: Long) {
        UNSAFE.putAddress(address, value)
    }

    //Integer

    @JvmStatic
    fun putInt(instance: Any, fieldOffset: Long, value: Int) {
        UNSAFE.putInt(instance, fieldOffset, value)
    }

    @JvmStatic
    fun getInt(instance: Any, fieldOffset: Long): Int {
        return UNSAFE.getInt(instance, fieldOffset)
    }

   //Long

    @JvmStatic
    fun putLong(instance: Any, fieldOffset: Long, value: Long) {
        UNSAFE.putLong(instance, fieldOffset, value)
    }

    @JvmStatic
    fun getLong(instance: Any, fieldOffset: Long): Long {
        return UNSAFE.getLong(instance, fieldOffset)
    }

    //Byte

    @JvmStatic
    fun putByte(instance: Any, fieldOffset: Long, value: Byte) {
        UNSAFE.putByte(instance, fieldOffset, value)
    }

    @JvmStatic
    fun getByte(instance: Any, fieldOffset: Long): Byte {
        return UNSAFE.getByte(instance, fieldOffset)
    }

    //Double

    @JvmStatic
    fun putDouble(instance: Any, fieldOffset: Long, value: Double) {
        UNSAFE.putDouble(instance, fieldOffset, value)
    }

    @JvmStatic
    fun getDouble(instance: Any, fieldOffset: Long): Double {
        return UNSAFE.getDouble(instance, fieldOffset)
    }

    //Float

    @JvmStatic
    fun putFloat(instance: Any, fieldOffset: Long, value: Float) {
        UNSAFE.putFloat(instance, fieldOffset, value)
    }

    @JvmStatic
    fun getFloat(instance: Any, fieldOffset: Long): Float {
        return UNSAFE.getFloat(instance, fieldOffset)
    }

    //Short

    @JvmStatic
    fun putShort(instance: Any, fieldOffset: Long, value: Short) {
        UNSAFE.putShort(instance, fieldOffset, value)
    }

    @JvmStatic
    fun getShort(instance: Any, fieldOffset: Long): Short {
        return UNSAFE.getShort(instance, fieldOffset)
    }

    //Char

    @JvmStatic
    fun putChar(instance: Any, fieldOffset: Long, value: Char) {
        UNSAFE.putChar(instance, fieldOffset, value)
    }

    @JvmStatic
    fun getChar(instance: Any, fieldOffset: Long): Char {
        return UNSAFE.getChar(instance, fieldOffset)
    }

    //Boolean

    @JvmStatic
    fun putBoolean(instance: Any, fieldOffset: Long, value: Boolean) {
        UNSAFE.putBoolean(instance, fieldOffset, value)
    }

    @JvmStatic
    fun getBoolean(instance: Any, fieldOffset: Long): Boolean {
        return UNSAFE.getBoolean(instance, fieldOffset)
    }

    //Object

    @JvmStatic
    fun putObject(instance: Any, fieldOffset: Long, value: Any?) {
        UNSAFE.putObject(instance, fieldOffset, value)
    }

    @JvmStatic
    fun getObject(instance: Any, fieldOffset: Long): Any? {
        return UNSAFE.getObject(instance, fieldOffset)
    }

}