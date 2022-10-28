package ru.dargen.accessors.compiler

import java.net.URI
import javax.tools.JavaFileObject.Kind
import javax.tools.SimpleJavaFileObject

class ClassSource(name: String, val code: String) :
    SimpleJavaFileObject(URI("string:///${name.replace(".", "/")}.java"), Kind.SOURCE) {

    override fun getCharContent(ignoreEncodingErrors: Boolean) = code

}