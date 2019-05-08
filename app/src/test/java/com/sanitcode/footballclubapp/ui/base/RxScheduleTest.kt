package com.sanitcode.footballclubapp.ui.base

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.Callable

class RxScheduleTest : TestRule {
    private val immediate: io.reactivex.functions.Function<Scheduler, Scheduler> = io.reactivex.functions.Function { Schedulers.trampoline() }

    private val mRxAndroidSchedulersHook: io.reactivex.functions.Function<Callable<Scheduler>, Scheduler> =
            io.reactivex.functions.Function { Schedulers.trampoline() }

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.reset()
                RxJavaPlugins.setIoSchedulerHandler(immediate)
                RxJavaPlugins.setComputationSchedulerHandler(immediate)
                RxJavaPlugins.setNewThreadSchedulerHandler(immediate)
                RxAndroidPlugins.reset()
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(mRxAndroidSchedulersHook)

                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }

}