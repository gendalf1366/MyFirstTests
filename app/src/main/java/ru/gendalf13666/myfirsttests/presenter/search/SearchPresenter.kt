package ru.gendalf13666.myfirsttests.presenter.search

import ru.gendalf13666.myfirsttests.view.search.ViewSearchContract
import retrofit2.Response
import ru.gendalf13666.myfirsttests.model.SearchResponse
import ru.gendalf13666.myfirsttests.repository.GitHubRepository

internal class SearchPresenter internal constructor(
    var viewContract: ViewSearchContract?,
    private val repository: GitHubRepository
) : PresenterSearchContract, GitHubRepository.GitHubRepositoryCallback {

    override fun searchGitHub(searchQuery: String) {
        viewContract?.displayLoading(true)
        repository.searchGithub(searchQuery, this)
    }

    override fun onAttach() {
    }

    override fun onDetach() {
        viewContract = null
    }

    override fun handleGitHubResponse(response: Response<SearchResponse?>?) {
        viewContract?.displayLoading(false)
        if (response != null && response.isSuccessful) {
            val searchResponse = response.body()
            val searchResults = searchResponse?.searchResults
            val totalCount = searchResponse?.totalCount
            if (searchResults != null && totalCount != null) {
                viewContract?.displaySearchResults(
                    searchResults,
                    totalCount
                )
            } else {
                viewContract?.displayError("Search results or total count are null")
            }
        } else {
            viewContract?.displayError("Response is null or unsuccessful")
        }
    }

    override fun handleGitHubError() {
        viewContract?.displayLoading(false)
        viewContract?.displayError()
    }
}
