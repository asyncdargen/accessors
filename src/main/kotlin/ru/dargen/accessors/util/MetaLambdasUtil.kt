package ru.dargen.accessors.util

import ru.dargen.accessors.member.MemberModifier
import java.lang.invoke.*
import java.lang.invoke.MethodHandles.Lookup

object MetaLambdasUtil {

    @JvmStatic
    fun <I> asInterface(declaredClass: Class<*>, methodHandle: MethodHandle): I {
        try {
            return MethodHandleProxies.asInterfaceInstance(declaredClass, methodHandle) as I
        } catch (t: IllegalArgumentException) {
            throw AccessException(t)
        } catch (t: WrongMethodTypeException) {
            throw AccessException(t)
        }
    }

    @JvmStatic
    fun <L> asInterfaceLambda(declaredClass: Class<*>, methodHandle: MethodHandle): L {
        try {
            val callSite = LambdaMetafactory.metafactory(
                DescriptorUtil.LOOKUP,
                declaredClass.declaredMethods.first { MemberModifier.ABSTRACT.isActiveOnMember(it) }.name,
                MethodType.methodType(declaredClass),
                methodHandle.type().generic(),
                methodHandle,
                methodHandle.type()
            )

            return callSite.target.invoke() as L
        } catch (e: LambdaConversionException) {
            throw AccessException(e)
        }
    }

}