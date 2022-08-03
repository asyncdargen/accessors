package ru.dargen.accessors.member.obj.annotation

import kotlin.reflect.KClass

@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
annotation class Accessor(val declaredClass: KClass<*>)
