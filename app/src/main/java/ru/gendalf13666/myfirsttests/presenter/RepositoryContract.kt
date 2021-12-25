package ru.gendalf13666.myfirsttests.presenter

import io.reactivex.Observable
import ru.gendalf13666.myfirsttests.model.SearchResponse
import ru.gendalf13666.myfirsttests.repository.RepositoryCallback

interface RepositoryContract {

    fun searchGithub(
        query: String,
        callback: RepositoryCallback
    )

    fun searchGithub(
        query: String
    ): Observable<SearchResponse>
}
