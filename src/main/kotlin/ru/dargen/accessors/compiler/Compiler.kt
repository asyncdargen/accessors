package ru.dargen.accessors.compiler

import javax.tools.JavaCompiler
import javax.tools.ToolProvider

object Compiler {

    val handle: JavaCompiler = ToolProvider.getSystemJavaCompiler()
    val classLoader: DynamicCl

}
