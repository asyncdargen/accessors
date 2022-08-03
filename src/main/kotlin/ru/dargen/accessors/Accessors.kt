package ru.dargen.accessors

import ru.dargen.accessors.member.MemberModifier
import ru.dargen.accessors.member.clazz.ClassAccessor
import ru.dargen.accessors.member.clazz.SimpleClassAccessor
import ru.dargen.accessors.member.constructor.ConstructorAccessor
import ru.dargen.accessors.member.constructor.InvokeConstructorAccessor
import ru.dargen.accessors.member.constructor.ReflectConstructorAccessor
import ru.dargen.accessors.member.constructor.UnsafeNoArgConstructorAccessor
import ru.dargen.accessors.member.enum.EnumAccessor
import ru.dargen.accessors.member.enum.SimpleEnumAccessor
import ru.dargen.accessors.member.field.*
import ru.dargen.accessors.member.method.InvokeMethodAccessor
import ru.dargen.accessors.member.method.MethodAccessor
import ru.dargen.accessors.member.method.ReflectMethodAccessor
import ru.dargen.accessors.member.obj.ObjectAccessor
import ru.dargen.accessors.member.obj.SimpleObjectAccessor
import ru.dargen.accessors.proxy.ProxyBuilder
import ru.dargen.accessors.util.InvokeUtil
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
    fun <T> invokeFieldAccessor(declaredClass: Class<*>, fieldName: String): FieldAccessor<T> {
        return invokeFieldAccessor(ReflectionUtil.getField(declaredClass, fieldName))
    }

    @JvmStatic
    fun <T> invokeFieldAccessor(field: Field): FieldAccessor<T> {
        return InvokeFieldAccessor(field, InvokeUtil.unreflectGetter(field), InvokeUtil.unreflectSetter(field))
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
    fun <T> invokeMethodAccessor(declaredClass: Class<*>, methodName: String, vararg methodArguments: Class<*>): MethodAccessor<T> {
        return invokeMethodAccessor(ReflectionUtil.getMethod(declaredClass, methodName, *methodArguments))
    }

    @JvmStatic
    fun <T> invokeMethodAccessor(method: Method): MethodAccessor<T> {
        return InvokeMethodAccessor(method, InvokeUtil.unreflectMethod(method))
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
    fun <T> invokeConstructorAccessor(declaredClass: Class<*>, vararg constructorArguments: Class<*>): ConstructorAccessor<T> {
        return invokeConstructorAccessor(ReflectionUtil.getConstructor(declaredClass, *constructorArguments))
    }

    @JvmStatic
    fun <T> invokeConstructorAccessor(constructor: Constructor<T>): ConstructorAccessor<T> {
        return InvokeConstructorAccessor(constructor, InvokeUtil.unreflectConstructor(constructor))
    }

    //Classes accessors

    @JvmStatic
    fun <T> reflectClassAccessor(declaredClass: Class<T>): ClassAccessor<T> {
        return SimpleClassAccessor(declaredClass, AccessorStrategy.REFLECTION)
    }

    @JvmStatic
    fun <T> invokeClassAccessor(declaredClass: Class<T>): ClassAccessor<T> {
        return SimpleClassAccessor(declaredClass, AccessorStrategy.INVOKE)
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
    fun <E : Enum<E>> invokeEnumAccessor(declaredClass: Class<E>): EnumAccessor<E> {
        return SimpleEnumAccessor(declaredClass, AccessorStrategy.INVOKE)
    }

    @JvmStatic
    fun <E : Enum<E>> unsafeEnumAccessor(declaredClass: Class<E>): EnumAccessor<E> {
        return SimpleEnumAccessor(declaredClass, AccessorStrategy.UNSAFE)
    }

//    private val ConstructorDeclaredClassAccessor = unsafeFieldAccessor<Class<*>>(Constructor::class.java, "clazz")
//    private val ConstructorModifierAccessor = unsafeFieldAccessor<Int>(Constructor::class.java, "modifiers")

    private fun <T> createEmptyConstructor(declaredClass: Class<T>): Constructor<T> {
        return declaredClass.declaredConstructors.sortedBy(Constructor<*>::getParameterCount).first() as Constructor<T>
    }

    @JvmStatic
    fun <T> unsafeNoArgsConstructorAccessor(declaredClass: Class<T>): ConstructorAccessor<T> {
        return UnsafeNoArgConstructorAccessor(createEmptyConstructor(declaredClass))
    }

    @JvmStatic
    fun <T> unsafeNoArgsConstructorAccessor(constructor: Constructor<T>): ConstructorAccessor<T> {
        return UnsafeNoArgConstructorAccessor(constructor)
    }

    //Object

    @JvmStatic
    fun <T : Any> reflectObjectAccessor(obj: T): ObjectAccessor<T> {
        return SimpleObjectAccessor(obj, reflectClassAccessor(obj.javaClass))
    }

    @JvmStatic
    fun <T : Any> invokeObjectAccessor(obj: T): ObjectAccessor<T> {
        return SimpleObjectAccessor(obj, invokeClassAccessor(obj.javaClass))
    }

    @JvmStatic
    fun <T : Any> unsafeObjectAccessor(obj: T): ObjectAccessor<T> {
        return SimpleObjectAccessor(obj, unsafeClassAccessor(obj.javaClass))
    }

//    @JvmStatic
//    fun <A> newProxiedObjectAccessor(strategy: AccessorStrategy, obj: Any, accessorClass: Class<A>): A {
//        return SimpleObjectAccessor(obj, reflectClassAccessor(obj.javaClass))
//    }


    //Proxy
    @JvmOverloads
    @JvmStatic
    fun newProxyBuilder(strategy: AccessorStrategy = AccessorStrategy.REFLECTION) = ProxyBuilder().accessStrategy(strategy)

}