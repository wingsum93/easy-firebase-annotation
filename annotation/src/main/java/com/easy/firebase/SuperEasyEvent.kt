package com.easy.firebase

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class SuperEasyEvent(val name: String = "event")
