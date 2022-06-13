package ru.dargen.accessors.member

interface Accessor {

    val declaredClass: Class<*>

    val modifiers: Set<MemberModifier>
    val accessModifier: MemberModifier

    val annotations: Set<Annotation>

    fun <A : Annotation> getAnnotation(type: Class<out Annotation>): A?

    fun <A : Annotation> isAnnotationPresent(type: Class<out Annotation>): Boolean

    fun hasModifier(modifier: MemberModifier): Boolean

}

inline fun <reified A : Annotation> Accessor.getAnnotation(): A? = getAnnotation(A::class.java)

inline fun <reified A : Annotation> Accessor.isAnnotationPresent(): Boolean = isAnnotationPresent<A>(A::class.java)