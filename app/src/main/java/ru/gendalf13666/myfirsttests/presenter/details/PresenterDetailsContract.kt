package ru.gendalf13666.myfirsttests.presenter.details

import ru.gendalf13666.myfirsttests.presenter.PresenterContract

internal interface PresenterDetailsContract : PresenterContract {
    fun setCounter(count: Int)
    fun onIncrement()
    fun onDecrement()
}
