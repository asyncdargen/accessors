package ru.dargen.accessors.proxy

import ru.dargen.accessors.AccessorStrategy
import ru.dargen.accessors.member.method.MethodAccessor
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class SimpleProxyAccessor(
    override val accessStrategy: AccessorStrategy,
    override val classLoader: ClassLoader,
    override val interfaces: List<Class<*>>,
    override val handlers: Map<Method, MethodHandler<*>>
) : ProxyAccessor, InvocationHandler {

    companion object {

        @JvmStatic
        val objectMethods: Map<AccessorStrategy, Map<Method, MethodAccessor<Any?>>> =
            AccessorStrategy.values().associateWith { strategy ->
                Any::class.java.declaredMethods
                    .map { strategy.methodAccessor<Any?>(it) }
                    .associateBy(MethodAccessor<*>::member)
            }

    }

    val methods: Map<Method, MethodAccessor<Any?>> = interfaces.map(Class<*>::getDeclaredMethods)
        .map { it.map { accessStrategy.methodAccessor<Any?>(it) } }
        .flatten()
        .associateBy(MethodAccessor<*>::member)
    override val proxyObject: Any = Proxy.newProxyInstance(classLoader, interfaces.toTypedArray(), this)

    override fun invoke(proxy: Any, method: Method, args: Array<Any?>?): Any? {
        require(proxy === proxyObject) { "Unknown proxy invoking" }
        val methodAccessor: MethodAccessor<Any?> = methods[method]
            ?: objectMethods[accessStrategy]!![method]
            ?: throw NoSuchElementException("Not find accessor for method $method")
        val handler: MethodHandler<Any?>? = handlers[method] as MethodHandler<Any?>?
        return handler?.apply(methodAccessor, args ?: emptyArray(), this)
    }

}