package com.sanitcode.footballclubapp.ui.base

import org.junit.ClassRule

open class BasePresenterTest {
    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxScheduleTest()
    }
}