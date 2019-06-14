package com.example.firebase_easy_demo.event

//@AnalyticsEvent
sealed class MyEvent {
    data class ShareImage(val imageName: String, val fullString: String) : MyEvent()
    data class Eric(val a: String, val some: String) : MyEvent()
    object ButtonTapped : MyEvent()
}