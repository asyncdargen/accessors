package ru.dargen.accessors.proxy

import ru.dargen.accessors.AccessorStrategy
import ru.dargen.accessors.member.method.MethodAccessor
import ru.dargen.accessors.util.function.ThreeFunction
import java.lang.reflect.Method

typealias MethodHandler<T> = ThreeFunction<MethodAccessor<T>, Array<Any?>, ProxyAccessor, T?>

interface ProxyAccessor {

    val accessStrategy: AccessorStrategy
    val classLoader: ClassLoader
    val interfaces: List<Class<out Any>>
    val handlers: Map<Method, MethodHandler<*>>
    val proxyObject: Any

    fun <T> getProxyObjectAs() = proxyObject as T

}