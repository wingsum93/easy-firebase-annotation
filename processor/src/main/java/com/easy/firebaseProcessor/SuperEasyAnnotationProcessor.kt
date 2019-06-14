package com.easy.firebaseProcessor

import com.easy.firebase.SuperEasyEvent
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.asTypeName
import me.eugeniomarletti.kotlin.metadata.KotlinMetadataUtils
import me.eugeniomarletti.kotlin.metadata.jvm.internalName
import me.eugeniomarletti.kotlin.processing.KotlinAbstractProcessor
import java.io.File
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.lang.model.util.ElementFilter

@AutoService(Processor::class)
class SuperEasyAnnotationProcessor : KotlinAbstractProcessor(), KotlinMetadataUtils {

    override fun getSupportedAnnotationTypes(): Set<String> {
        return setOf(SuperEasyEvent::class.java.canonicalName)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        return try {

            roundEnv.getElementsAnnotatedWith(SuperEasyEvent::class.java).forEach { eachElement ->
                checkAnnotationClassType(eachElement)
                val outputDir: File = checkOutputDir()
                // do waht?
                val variableAsElement = typeUtils.asElement(eachElement.asType())
                val fieldsInArgument = ElementFilter.fieldsIn(variableAsElement.enclosedElements)

                val annotationArgs = variableAsElement.getAnnotation(SuperEasyEvent::class.java)


                //generate code
                generateFunction()

            }
            true
        } catch (e: Exception) {
            error(e.message!!)
            false
        }

    }

    private fun generateFunction() {
        val funBuilder = FunSpec.builder("bactest")
            .addModifiers(KModifier.PUBLIC)
            .addParameter("v", Int::class.asTypeName())

        FileSpec.builder("oo.eric.easy", "EricEasyUtil")
            .addFunction(funBuilder.build()).build()
            .writeTo(checkOutputDir())
    }

    @Throws(IllegalStateException::class)
    private fun checkAnnotationClassType(element: Element) {
        when (element.kind) {
            ElementKind.CLASS -> {

            }
            else -> {
                val name = element.internalName
                val packages = element.enclosedElements
                val erms = "Can only be applied to functions,  element: $element , name: $name}"
                throwException(erms)
            }
        }
    }

    @Throws(IllegalStateException::class)
    fun throwException(errorMessage: String) {
        throw IllegalStateException(errorMessage)
    }

    //check output directory
    @Throws(IllegalStateException::class)
    private fun checkOutputDir(): File {
        return generatedDir
            ?: throw IllegalStateException("Can't find the target directory for generated Kotlin files.")
    }
}