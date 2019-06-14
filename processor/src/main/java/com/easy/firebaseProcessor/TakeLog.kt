package com.easy.firebaseProcessor

import me.eugeniomarletti.kotlin.processing.KotlinAbstractProcessor
import javax.tools.Diagnostic


fun KotlinAbstractProcessor.takeNote(string: String) {
    messager.printMessage(Diagnostic.Kind.NOTE, string)
}

fun KotlinAbstractProcessor.warning(string: String) {
    messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, string)
}

fun KotlinAbstractProcessor.error(string: String) {
    messager.printMessage(Diagnostic.Kind.ERROR, string)
}

fun KotlinAbstractProcessor.printMessage(string: String, kind: Diagnostic.Kind = Diagnostic.Kind.MANDATORY_WARNING) {
    messager.printMessage(kind, string)
}