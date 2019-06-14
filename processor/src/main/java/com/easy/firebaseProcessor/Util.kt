package com.easy.firebaseProcessor

import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import javax.lang.model.element.Element

fun TypeName.getFieldType(): FieldType {

    return when (this.toString()) {
        "java.lang.Integer" -> {
            FieldType.Interger
        }
        "java.lang.String" -> {
            FieldType.String
        }
        "java.lang.Boolean" -> {
            FieldType.Boolean
        }
        "CharSequence" -> {
            FieldType.Char
        }
        else -> throw Exception()
    }
}

fun Element.getFieldType(): FieldType {
    return asType().asTypeName().getFieldType()
}