package ru.gendalf13666.myfirsttests.repository

import retrofit2.Response
import ru.gendalf13666.myfirsttests.model.SearchResponse
import ru.gendalf13666.myfirsttests.presenter.RepositoryContract

internal class FakeGitHubRepository : RepositoryContract {

    override fun searchGithub(
        query: String,
        callback: RepositoryCallback
    ) {
        callback.handleGitHubResponse(Response.success(SearchResponse(42, listOf())))
    }
}
