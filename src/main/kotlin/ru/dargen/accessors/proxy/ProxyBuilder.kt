package ru.dargen.accessors.proxy

import ru.dargen.accessors.AccessorStrategy
import ru.dargen.accessors.member.method.MethodAccessor
import ru.dargen.accessors.util.ReflectionUtil
import ru.dargen.accessors.util.function.ThreeFunction
import java.lang.reflect.Method

class ProxyBuilder {

    var accessStrategy: AccessorStrategy = AccessorStrategy.REFLECTION
    var classLoader: ClassLoader = Thread.currentThread().contextClassLoader
    val interfaces: MutableList<Class<*>> = ArrayList()
    val handlers: MutableMap<Method, MethodHandler<*>> = HashMap()

    fun accessStrategy(strategy: AccessorStrategy) = apply { accessStrategy = strategy }

    fun classLoader(classLoader: ClassLoader) = apply { this.classLoader = classLoader }

    fun implement(interfaceClass: Class<*>) = apply {
        require(interfaceClass.isInterface) { "Declared class is not interface" }
        interfaces.add(interfaceClass)
    }

    fun <T> handler(method: Method, handler: MethodHandler<T>) = apply {
        require(method.declaringClass == Any::class.java || interfaces.contains(method.declaringClass)) { "Unknown method" }
        handlers[method] = handler as MethodHandler<*>
    }

    fun <T> handler(handler: MethodHandler<T>, name: String, vararg parameters: Class<*>) = apply {
        val method = interfaces.firstNotNullOfOrNull {
            runCatching { ReflectionUtil.getMethod(it, name, *parameters) }.getOrNull()
        } ?: throw NoSuchMethodError(name)
        handler(method, handler)
    }

    fun build(): ProxyAccessor = SimpleProxyAccessor(accessStrategy, classLoader, interfaces, handlers)

}