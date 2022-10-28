package ru.dargen.accessors.compiler

import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.net.URI
import javax.tools.JavaFileObject.Kind
import javax.tools.SimpleJavaFileObject

class CompiledClass(val className: String, val bytes: ByteArray) : SimpleJavaFileObject(URI(className), Kind.CLASS) {

    override fun openOutputStream(): OutputStream {
        return ByteArrayOutputStream()
    }

}