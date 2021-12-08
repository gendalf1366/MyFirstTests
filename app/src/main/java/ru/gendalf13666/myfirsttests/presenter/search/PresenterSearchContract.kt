package ru.gendalf13666.myfirsttests.presenter.search

import ru.gendalf13666.myfirsttests.presenter.PresenterContract

internal interface PresenterSearchContract : PresenterContract {
    fun searchGitHub(searchQuery: String)
}
