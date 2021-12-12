package ru.gendalf13666.myfirsttests.repository

import retrofit2.Response
import ru.gendalf13666.myfirsttests.model.SearchResponse

interface RepositoryCallback {
    fun handleGitHubResponse(response: Response<SearchResponse?>?)
    fun handleGitHubError()
}
