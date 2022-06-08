package ru.dargen.accessors

import ru.dargen.accessors.member.clazz.ClassAccessor
import ru.dargen.accessors.member.constructor.ConstructorAccessor
import ru.dargen.accessors.member.enum.EnumAccessor
import ru.dargen.accessors.member.field.FieldAccessor
import ru.dargen.accessors.member.method.MethodAccessor
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method

enum class AccessorStrategy {

    REFLECTION {
        override fun <T> fieldAccessor(declaredClass: Class<*>, fieldName: String): FieldAccessor<T> {
            return Accessors.reflectFieldAccessor(declaredClass, fieldName)
        }

        override fun <T> fieldAccessor(field: Field): FieldAccessor<T> {
            return Accessors.reflectFieldAccessor(field)
        }

        override fun <T> methodAccessor(declaredClass: Class<*>, methodName: String, vararg methodArguments: Class<*>): MethodAccessor<T> {
            return Accessors.reflectMethodAccessor(declaredClass, methodName, *methodArguments)
        }

        override fun <T> methodAccessor(method: Method): MethodAccessor<T> {
            return Accessors.reflectMethodAccessor(method)
        }

        override fun <T> constructorAccessor(declaredClass: Class<T>, vararg constructorArguments: Class<*>): ConstructorAccessor<T> {
            return Accessors.reflectConstructorAccessor(declaredClass, *constructorArguments)
        }

        override fun <T> constructorAccessor(constructor: Constructor<T>): ConstructorAccessor<T> {
            return Accessors.reflectConstructorAccessor(constructor)
        }

        override fun <T> classAccessor(declaredClass: Class<T>): ClassAccessor<T> {
            return Accessors.reflectClassAccessor(declaredClass)
        }

        override fun <E : Enum<E>> enumAccessor(declaredClass: Class<E>): EnumAccessor<E> {
            return Accessors.reflectEnumAccessor(declaredClass)
        }

    },
    DESCRIPTOR {
        override fun <T> fieldAccessor(declaredClass: Class<*>, fieldName: String): FieldAccessor<T> {
            return Accessors.descriptorFieldAccessor(declaredClass, fieldName)
        }

        override fun <T> fieldAccessor(field: Field): FieldAccessor<T> {
            return Accessors.descriptorFieldAccessor(field)
        }

        override fun <T> methodAccessor(declaredClass: Class<*>, methodName: String, vararg methodArguments: Class<*>): MethodAccessor<T> {
            return Accessors.descriptorMethodAccessor(declaredClass, methodName, *methodArguments)
        }

        override fun <T> methodAccessor(method: Method): MethodAccessor<T> {
            return Accessors.descriptorMethodAccessor(method)
        }

        override fun <T> constructorAccessor(declaredClass: Class<T>, vararg constructorArguments: Class<*>): ConstructorAccessor<T> {
            return Accessors.descriptorConstructorAccessor(declaredClass, *constructorArguments)
        }

        override fun <T> constructorAccessor(constructor: Constructor<T>): ConstructorAccessor<T> {
            return Accessors.descriptorConstructorAccessor(constructor)
        }

        override fun <T> classAccessor(declaredClass: Class<T>): ClassAccessor<T> {
            return Accessors.descriptorClassAccessor(declaredClass)
        }

        override fun <E : Enum<E>> enumAccessor(declaredClass: Class<E>): EnumAccessor<E> {
            return Accessors.descriptorEnumAccessor(declaredClass)
        }
    },
    UNSAFE {
        override fun <T> fieldAccessor(declaredClass: Class<*>, fieldName: String): FieldAccessor<T> {
            return Accessors.unsafeFieldAccessor(declaredClass, fieldName)
        }

        override fun <T> fieldAccessor(field: Field): FieldAccessor<T> {
            return Accessors.unsafeFieldAccessor(field)
        }

        override fun <T> methodAccessor(declaredClass: Class<*>, methodName: String, vararg methodArguments: Class<*>): MethodAccessor<T> {
            return Accessors.descriptorMethodAccessor(declaredClass, methodName, *methodArguments)
        }

        override fun <T> methodAccessor(method: Method): MethodAccessor<T> {
            return Accessors.descriptorMethodAccessor(method)
        }

        override fun <T> constructorAccessor(declaredClass: Class<T>, vararg constructorArguments: Class<*>): ConstructorAccessor<T> {
            return Accessors.unsafeNoArgsConstructorAccessor(declaredClass)
        }

        override fun <T> constructorAccessor(constructor: Constructor<T>): ConstructorAccessor<T> {
            return Accessors.unsafeNoArgsConstructorAccessor(constructor)
        }

        override fun <T> classAccessor(declaredClass: Class<T>): ClassAccessor<T> {
            return Accessors.unsafeClassAccessor(declaredClass)
        }

        override fun <E : Enum<E>> enumAccessor(declaredClass: Class<E>): EnumAccessor<E> {
            return Accessors.unsafeEnumAccessor(declaredClass)
        }
    }
    ;

    abstract fun <T> fieldAccessor(declaredClass: Class<*>, fieldName: String): FieldAccessor<T>

    abstract fun <T> fieldAccessor(field: Field): FieldAccessor<T>

    abstract fun <T> methodAccessor(declaredClass: Class<*>, methodName: String, vararg methodArguments: Class<*>): MethodAccessor<T>

    abstract fun <T> methodAccessor(method: Method): MethodAccessor<T>

    abstract fun <T> constructorAccessor(declaredClass: Class<T>, vararg constructorArguments: Class<*>): ConstructorAccessor<T>

    abstract fun <T> constructorAccessor(constructor: Constructor<T>): ConstructorAccessor<T>

    abstract fun <T> classAccessor(declaredClass: Class<T>): ClassAccessor<T>

    abstract fun <E : Enum<E>> enumAccessor(declaredClass: Class<E>): EnumAccessor<E>

}