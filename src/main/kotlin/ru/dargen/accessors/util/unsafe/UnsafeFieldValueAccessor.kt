package ru.dargen.accessors.util.unsafe

import ru.dargen.accessors.util.function.*

data class UnsafeFieldValueAccessor<V>(
    val setter: ThrowThreeConsumer<Any, Long, V?>,
    val getter: ThrowBiFunction<Any, Long, V?>
) {

    fun setValue(instance: Any, fieldOffset: Long, value: V?) {
        setter.accept(instance, fieldOffset, value)
    }

    fun getValue(instance: Any, fieldOffset: Long): V? {
        return getter.apply(instance, fieldOffset)
    }

}

inline fun <V> unsafeFieldValueAccessor(crossinline setter: (Any, Long, V) -> Unit, crossinline getter: (Any, Long) -> V?) =
    UnsafeFieldValueAccessor(throwThreeConsumer { instance, fieldOffset, value -> value?.let { setter(instance, fieldOffset, it) } }, throwBiFunction(getter))

inline fun <V> unsafeNullableFieldValueAccessor(crossinline setter: (Any, Long, V?) -> Unit, crossinline getter: (Any, Long) -> V?) =
    UnsafeFieldValueAccessor(throwThreeConsumer(setter), throwBiFunction(getter))

