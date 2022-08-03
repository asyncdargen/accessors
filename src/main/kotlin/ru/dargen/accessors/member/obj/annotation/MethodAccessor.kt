package ru.dargen.accessors.member.obj.annotation

import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class MethodAccessor(val methodName: String, vararg val methodParameters: KClass<*> = [])
