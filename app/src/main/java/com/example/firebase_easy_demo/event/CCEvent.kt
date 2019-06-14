package com.example.firebase_easy_demo.event

import com.easy.firebase.EasyField
import com.easy.firebase.SuperEasyEvent

@SuperEasyEvent
class CCEvent {

    @EasyField("qq")
    val first: String = "A"
    @EasyField("second_q")
    val second: String = "B"
    @EasyField("second_q_11")
    val third: Int = 55
    @EasyField("aad_q_11")
    val zza: Int? = null
    @EasyField("second_q_11")
    val cs: CharSequence = "aaa"
    @EasyField("bbb")
    val bbb: Boolean = false
}