package com.easy.firebaseProcessor

import com.easy.firebase.AnalyticsEvent
import com.google.auto.service.AutoService
import me.eugeniomarletti.kotlin.metadata.KotlinClassMetadata
import me.eugeniomarletti.kotlin.metadata.KotlinMetadataUtils
import me.eugeniomarletti.kotlin.metadata.kotlinMetadata
import me.eugeniomarletti.kotlin.metadata.modality
import me.eugeniomarletti.kotlin.metadata.shadow.metadata.ProtoBuf
import me.eugeniomarletti.kotlin.processing.KotlinAbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
class AnalyticsEventProcessor : KotlinAbstractProcessor(), KotlinMetadataUtils {
    companion object {
        private val ANNOTATION = AnalyticsEvent::class.java
    }

    override fun getSupportedAnnotationTypes() = setOf(ANNOTATION.canonicalName)

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

    override fun process(
        annotations: Set<TypeElement>,
        roundEnv: RoundEnvironment
    ): Boolean {
        val annotatedElements =
            roundEnv.getElementsAnnotatedWith(ANNOTATION)

        for (annotatedElement in annotatedElements) {

            // Check if the annotatedElement is a Kotlin sealed class
            val analyticsElement =
                getAnalyticsElement(annotatedElement) ?: continue

        }
        return true
    }
    fun getAnalyticsElement(element: Element): TypeElement? {
        val kotlinMetadata = element.kotlinMetadata
        if (kotlinMetadata !is KotlinClassMetadata ||
            element !is TypeElement
        ) {
            // Not a Kotlin class
            return null
        }
        val proto = kotlinMetadata.data.classProto
        if (proto.modality != ProtoBuf.Modality.SEALED) {
            // Is not a sealed class
            return null
        }
        return element
    }
}