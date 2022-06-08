package ru.dargen.accessors

import ru.dargen.accessors.member.MemberModifier
import ru.dargen.accessors.member.clazz.ClassAccessor
import ru.dargen.accessors.member.clazz.SimpleClassAccessor
import ru.dargen.accessors.member.constructor.ConstructorAccessor
import ru.dargen.accessors.member.constructor.DescriptorConstructorAccessor
import ru.dargen.accessors.member.constructor.ReflectConstructorAccessor
import ru.dargen.accessors.member.constructor.UnsafeNoArgConstructorAccessor
import ru.dargen.accessors.member.enum.EnumAccessor
import ru.dargen.accessors.member.enum.SimpleEnumAccessor
import ru.dargen.accessors.member.field.*
import ru.dargen.accessors.member.method.DescriptorMethodAccessor
import ru.dargen.accessors.member.method.MethodAccessor
import ru.dargen.accessors.member.method.ReflectMethodAccessor
import ru.dargen.accessors.util.DescriptorUtil
import ru.dargen.accessors.util.ReflectionUtil
import ru.dargen.accessors.util.unsafe.UnsafeFieldValueAccessor
import ru.dargen.accessors.util.unsafe.UnsafeUtil
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method

object Accessors {

    //For strategy

    @JvmStatic
    fun <T> fieldAccessor(strategy: AccessorStrategy, declaredClass: Class<*>, fieldName: String): FieldAccessor<T> {
        return strategy.fieldAccessor(declaredClass, fieldName)
    }

    @JvmStatic
    fun <T> fieldAccessor(strategy: AccessorStrategy, field: Field): FieldAccessor<T> {
        return strategy.fieldAccessor(field)
    }

    @JvmStatic
    fun <T> methodAccessor(strategy: AccessorStrategy, declaredClass: Class<*>, methodName: String, vararg methodArguments: Class<*>): MethodAccessor<T> {
        return strategy.methodAccessor(declaredClass, methodName, *methodArguments)
    }

    @JvmStatic
    fun <T> methodAccessor(strategy: AccessorStrategy, method: Method): MethodAccessor<T> {
        return strategy.methodAccessor(method)
    }

    @JvmStatic
    fun <T> constructorAccessor(strategy: AccessorStrategy, declaredClass: Class<T>, vararg constructorArguments: Class<*>): ConstructorAccessor<T> {
        return strategy.constructorAccessor(declaredClass, *constructorArguments)
    }

    @JvmStatic
    fun <T> constructorAccessor(strategy: AccessorStrategy, constructor: Constructor<T>): ConstructorAccessor<T> {
        return strategy.constructorAccessor(constructor)
    }

    @JvmStatic
    fun <T> classAccessor(strategy: AccessorStrategy, declaredClass: Class<T>): ClassAccessor<T> {
        return strategy.classAccessor(declaredClass)
    }

    @JvmStatic
    fun <E : Enum<E>> classAccessor(strategy: AccessorStrategy, declaredClass: Class<E>): EnumAccessor<E> {
        return strategy.enumAccessor(declaredClass)
    }

    //Field accessors

    @JvmStatic
    fun <T> reflectFieldAccessor(declaredClass: Class<*>, fieldName: String): FieldAccessor<T> {
        return reflectFieldAccessor(ReflectionUtil.getField(declaredClass, fieldName))
    }

    @JvmStatic
    fun <T> reflectFieldAccessor(field: Field): FieldAccessor<T> {
        return ReflectFieldAccessor(field)
    }

    @JvmStatic
    fun <T> descriptorFieldAccessor(declaredClass: Class<*>, fieldName: String): FieldAccessor<T> {
        return descriptorFieldAccessor(ReflectionUtil.getField(declaredClass, fieldName))
    }

    @JvmStatic
    fun <T> descriptorFieldAccessor(field: Field): FieldAccessor<T> {
        return DescriptorFieldAccessor(field, DescriptorUtil.unreflectGetter(field), DescriptorUtil.unreflectSetter(field))
    }

    @JvmStatic
    fun <T> unsafeFieldAccessor(declaredClass: Class<*>, fieldName: String): FieldAccessor<T> {
        return unsafeFieldAccessor(ReflectionUtil.getField(declaredClass, fieldName))
    }

    @JvmStatic
    fun <T> unsafeFieldAccessor(field: Field): FieldAccessor<T> {
        val fieldValueAccessor = UnsafeUtil.getFieldValueAccessor(field) as UnsafeFieldValueAccessor<T>
        val fieldOffset = UnsafeUtil.getFieldOffset(field)

        return if (MemberModifier.STATIC.isActiveOnMember(field))
            UnsafeStaticFieldAccessor(field, fieldOffset, fieldValueAccessor, UnsafeUtil.getFieldBase(field))
        else UnsafeFieldAccessor(field, fieldOffset, fieldValueAccessor)
    }

    //Method accessors

    @JvmStatic
    fun <T> reflectMethodAccessor(declaredClass: Class<*>, methodName: String, vararg methodArguments: Class<*>): MethodAccessor<T> {
        return reflectMethodAccessor(ReflectionUtil.getMethod(declaredClass, methodName, *methodArguments))
    }

    @JvmStatic
    fun <T> reflectMethodAccessor(method: Method): MethodAccessor<T> {
        return ReflectMethodAccessor(method)
    }

    @JvmStatic
    fun <T> descriptorMethodAccessor(declaredClass: Class<*>, methodName: String, vararg methodArguments: Class<*>): MethodAccessor<T> {
        return descriptorMethodAccessor(ReflectionUtil.getMethod(declaredClass, methodName, *methodArguments))
    }

    @JvmStatic
    fun <T> descriptorMethodAccessor(method: Method): MethodAccessor<T> {
        return DescriptorMethodAccessor(method, DescriptorUtil.unreflectMethod(method))
    }

    //Constructor accessors

    @JvmStatic
    fun <T> reflectConstructorAccessor(declaredClass: Class<*>, vararg constructorArguments: Class<*>): ConstructorAccessor<T> {
        return reflectConstructorAccessor(ReflectionUtil.getConstructor(declaredClass, *constructorArguments))
    }

    @JvmStatic
    fun <T> reflectConstructorAccessor(constructor: Constructor<T>): ConstructorAccessor<T> {
        return ReflectConstructorAccessor(constructor)
    }

    @JvmStatic
    fun <T> descriptorConstructorAccessor(declaredClass: Class<*>, vararg constructorArguments: Class<*>): ConstructorAccessor<T> {
        return descriptorConstructorAccessor(ReflectionUtil.getConstructor(declaredClass, *constructorArguments))
    }

    @JvmStatic
    fun <T> descriptorConstructorAccessor(constructor: Constructor<T>): ConstructorAccessor<T> {
        return DescriptorConstructorAccessor(constructor, DescriptorUtil.unreflectConstructor(constructor))
    }

    //Classes accessors

    @JvmStatic
    fun <T> reflectClassAccessor(declaredClass: Class<T>): ClassAccessor<T> {
        return SimpleClassAccessor(declaredClass, AccessorStrategy.REFLECTION)
    }

    @JvmStatic
    fun <T> descriptorClassAccessor(declaredClass: Class<T>): ClassAccessor<T> {
        return SimpleClassAccessor(declaredClass, AccessorStrategy.DESCRIPTOR)
    }

    @JvmStatic
    fun <T> unsafeClassAccessor(declaredClass: Class<T>): ClassAccessor<T> {
        return SimpleClassAccessor(declaredClass, AccessorStrategy.UNSAFE)
    }

    //Enum classes accessors

    @JvmStatic
    fun <E : Enum<E>> reflectEnumAccessor(declaredClass: Class<E>): EnumAccessor<E> {
        return SimpleEnumAccessor(declaredClass, AccessorStrategy.REFLECTION)
    }

    @JvmStatic
    fun <E : Enum<E>> descriptorEnumAccessor(declaredClass: Class<E>): EnumAccessor<E> {
        return SimpleEnumAccessor(declaredClass, AccessorStrategy.DESCRIPTOR)
    }

    @JvmStatic
    fun <E : Enum<E>> unsafeEnumAccessor(declaredClass: Class<E>): EnumAccessor<E> {
        return SimpleEnumAccessor(declaredClass, AccessorStrategy.UNSAFE)
    }

    private val CONSTRUCTOR_DECLARED_CLASS_ACCESSOR = unsafeFieldAccessor<Class<*>>(Constructor::class.java, "clazz")
    private val CONSTRUCTOR_MODIFIER_ACCESSOR = unsafeFieldAccessor<Int>(Constructor::class.java, "modifiers")

    private fun <T> createEmptyConstructor(declaredClass: Class<T>): Constructor<T> {
        val constructor = UnsafeUtil.allocateInstance(Constructor::class.java)

        CONSTRUCTOR_DECLARED_CLASS_ACCESSOR.setValue(constructor, declaredClass)
        CONSTRUCTOR_MODIFIER_ACCESSOR.setValue(constructor, 1)

        return constructor as Constructor<T>
    }

    @JvmStatic
    fun <T> unsafeNoArgsConstructorAccessor(declaredClass: Class<T>): ConstructorAccessor<T> {
        return UnsafeNoArgConstructorAccessor(createEmptyConstructor(declaredClass))
    }

    @JvmStatic
    fun <T> unsafeNoArgsConstructorAccessor(constructor: Constructor<T>): ConstructorAccessor<T> {
        return UnsafeNoArgConstructorAccessor(constructor)
    }

}