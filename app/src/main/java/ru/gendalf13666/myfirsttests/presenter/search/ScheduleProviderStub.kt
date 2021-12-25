package ru.gendalf13666.myfirsttests.presenter.search

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import ru.gendalf13666.myfirsttests.presenter.SchedulerProvider

class ScheduleProviderStub : SchedulerProvider {
    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }
}
