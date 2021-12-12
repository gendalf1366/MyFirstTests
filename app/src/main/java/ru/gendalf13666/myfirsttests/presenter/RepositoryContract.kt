package ru.gendalf13666.myfirsttests.presenter

import ru.gendalf13666.myfirsttests.repository.RepositoryCallback

internal interface RepositoryContract {
    fun searchGithub(
        query: String,
        callback: RepositoryCallback
    )
}
