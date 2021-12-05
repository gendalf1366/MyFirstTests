package ru.gendalf13666.myfirsttests.presenter

import retrofit2.Response
import ru.gendalf13666.myfirsttests.model.SearchResponse
import ru.gendalf13666.myfirsttests.repository.GitHubRepository
import ru.gendalf13666.myfirsttests.repository.GitHubRepository.GitHubRepositoryCallback
import ru.gendalf13666.myfirsttests.view.ViewContract

internal class SearchPresenter internal constructor(
    private val viewContract: ViewContract,
    private val repository: GitHubRepository
) : PresenterContract, GitHubRepositoryCallback {

    override fun searchGitHub(searchQuery: String) {
        viewContract.displayLoading(true)
        repository.searchGithub(searchQuery, this)
    }

    override fun handleGitHubResponse(response: Response<SearchResponse?>?) {
        viewContract.displayLoading(false)
        if (response != null && response.isSuccessful) {
            val searchResponse = response.body()
            val searchResults = searchResponse?.searchResults
            val totalCount = searchResponse?.totalCount
            if (searchResults != null && totalCount != null) {
                viewContract.displaySearchResults(
                    searchResults,
                    totalCount
                )
            } else {
                viewContract.displayError("Search results or total count are null")
            }
        } else {
            viewContract.displayError("Response is null or unsuccessful")
        }
    }

    override fun handleGitHubError() {
        viewContract.displayLoading(false)
        viewContract.displayError()
    }
}
