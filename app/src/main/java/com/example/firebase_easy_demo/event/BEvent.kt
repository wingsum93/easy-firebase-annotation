package com.example.firebase_easy_demo.event

import com.easy.firebase.AnalyticsEvent
import com.easy.firebase.EasyField
import com.easy.firebase.SuperEasyEvent

@SuperEasyEvent
@AnalyticsEvent
class BEvent {
    @EasyField("aaa")
    val a: String = "A"
    @EasyField("bbb")
    val some: String = "B"


}