package ru.dargen.accessors.member.obj

import ru.dargen.accessors.AccessorStrategy
import ru.dargen.accessors.member.clazz.ClassAccessor

open class SimpleObjectAccessor<A>(override var accessedObject: A, override val classAccessor: ClassAccessor<A>) : ObjectAccessor<A> {

    override val strategy: AccessorStrategy
        get() = classAccessor.strategy
    override val declaredClass: Class<A>
        get() = classAccessor.declaredClass

    override fun <T> getFieldValue(fieldName: String): T? {
        return classAccessor.getFieldAccessor<T>(fieldName).getValue(accessedObject)
    }

    override fun setFieldValue(fieldName: String, value: Any?) {
        classAccessor.getFieldAccessor<A>(fieldName).setValue(accessedObject, value as A?)
    }

    override fun <T> invokeMethod(methodName: String, vararg parameters: Any): T? {
        return classAccessor.getMethodAccessor<T>(methodName, *parameters.map(Any::javaClass).toTypedArray())
            .invoke(accessedObject, parameters)
    }

    override fun invokeVoidMethod(methodName: String, vararg parameters: Any) {
        invokeMethod<Void>(methodName, parameters)
    }

}