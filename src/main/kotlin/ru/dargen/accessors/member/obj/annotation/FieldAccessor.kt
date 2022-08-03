package ru.dargen.accessors.member.obj.annotation

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class FieldAccessor(val fieldName: String)
