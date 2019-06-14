package com.easy.firebaseProcessor

import com.easy.firebase.EasyField
import com.easy.firebase.SuperEasyEvent
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
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
import javax.lang.model.element.VariableElement
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
            val outputDir: File = checkOutputDir()
            val targetClassElements = roundEnv.getElementsAnnotatedWith(SuperEasyEvent::class.java)
            targetClassElements.forEach { eachElement ->
                checkAnnotationClassType(eachElement)

            }

            //generate code
            outputDir.mkdir()
            takeNote { "outputDir err: $outputDir" }
            generateFile(targetClassElements, outputDir)
            true
        } catch (e: Exception) {
            error { e.message ?: "UnKnown" }
            false
        }

    }

    private fun generateFile(classElements: Set<Element>, outputDir: File) {
        val builder = FileSpec.builder(generatedPackageName, generatedFileName)
        takeNote { "classElements in: $classElements" }

        classElements.forEach { classElement ->
            generateFuncForEachClass(classElement).forEach { funcSpec ->
                builder.addFunction(funcSpec)
                takeNote { "funcSpec +1: $funcSpec" }
            }
        }

        builder.addImport("com.google.firebase.analytics", "FirebaseAnalytics")
        val fileSpec = builder.build()
        fileSpec.writeTo(outputDir)


    }

    fun generateFuncForEachClass(classElement: Element): List<FunSpec> {
        takeNote { "class name: ${classElement.asType().asTypeName()}" }
        println("123456")
        val paramNameOfEvent = classElement.getAnnotation(SuperEasyEvent::class.java).name

        val list = mutableListOf<FunSpec>()

        val fieldsInArgument = ElementFilter.fieldsIn(classElement.enclosedElements)
        val fieldList = mutableListOf<Pair<VariableElement, EasyField>>()
        fieldsInArgument.forEach { variableAsElement ->
            val fieldName = variableAsElement.simpleName
            val b = variableAsElement.asType().asTypeName()

            val easyField = variableAsElement.getAnnotation(EasyField::class.java)
            easyField ?: return@forEach
            takeNote {
                "This field name:$fieldName, type: $b  easyField:$easyField"
            }
            fieldList += variableAsElement to easyField
        }


        val funBuilder = FunSpec.builder("logEvent")
            .addModifiers(KModifier.PUBLIC)
            .addParameter(paramNameOfEvent, classElement.asType().asTypeName())
            .receiver(CLASS_NAME_FIREBASE_ANALAYTICS)
            .addStatement("val b = %T()", CLASS_NAME_BUNDLE)
        takeNote { "before do field #001" }
        fieldList.forEach { pair ->
            val tagName = pair.second.name
            val c = pair.first.simpleName
//            when (pair.first.getFieldType()) {
//                FieldType.Boolean -> {
//                    funBuilder.addStatement("b.putBoolean($tagName,%T.%T)", paramNameOfEvent, c)
//                }
//                FieldType.Interger -> {
//                    funBuilder.addStatement("b.putInt($tagName,%T.%T)", paramNameOfEvent, c)
//                }
//                FieldType.String -> {
//                    funBuilder.addStatement("b.putString($tagName,%T.%T)", paramNameOfEvent, c)
//                }
//            }
//            funBuilder.addStatement("b.putString(")
        }
        takeNote { "fieldList: $fieldList" }

        funBuilder.addStatement(
            "logEvent(%L,b)",
            "FirebaseAnalytics.Event.SELECT_CONTENT" //,"FirebaseAnalytics.Event.SELECT_CONTENT"
        )


        val f2B = FunSpec.builder("doYourBest")
            .receiver(ClassName("android.view", "View"))
            .addKdoc(
                CodeBlock.builder()
                    .addStatement(
                        "This shit is dog. [%T], %L",
                        ClassName("android.os", "Bundle"),
                        ClassName("android.os", "Bundle")

                    )
                    .addStatement(
                        """
                        This function is generated by iii, no to change it.
                    """.trimIndent()
                    )
                    .build()
            )
            .addTypeVariable(TypeVariableName.Companion.invoke("Pop"))
            .addParameter("bundle", ClassName("android.os", "Bundle"))
            .addParameter("popClass", ClassName("", "Pop"))
            .beginControlFlow("if(bundle != null)")
            .addStatement("val a = 0")
            .addStatement("val b = 0")
            .addStatement("val c = a + b")
            .endControlFlow()
            .addStatement("val abc =\"999\"")



        list += funBuilder.build()
//        list += f2B.build()
        return list
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

    companion object {

        const val generatedFileName = "Eric"
        const val generatedPackageName = "com.eric.easy"

        val CLASS_NAME_BUNDLE = ClassName("android.os", "Bundle")
        val CLASS_NAME_VIEW = ClassName("android.view", "View")
        val CLASS_NAME_FIREBASE_ANALAYTICS = ClassName("com.google.firebase.analytics", "FirebaseAnalytics")
    }
}