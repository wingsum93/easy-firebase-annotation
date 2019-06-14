package com.easy.firebaseProcessor

import me.eugeniomarletti.kotlin.processing.KotlinAbstractProcessor
import javax.tools.Diagnostic


fun KotlinAbstractProcessor.takeNote(string: () -> String) {
    messager.printMessage(Diagnostic.Kind.NOTE, string.invoke())
}

fun KotlinAbstractProcessor.warning(string: () -> String) {
    messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, string.invoke())
}

fun KotlinAbstractProcessor.error(string: () -> String) {
    messager.printMessage(Diagnostic.Kind.ERROR, string.invoke())
}

fun KotlinAbstractProcessor.printMessage(
    string: () -> String,
    kind: Diagnostic.Kind = Diagnostic.Kind.MANDATORY_WARNING
) {
    messager.printMessage(kind, string.invoke())
}