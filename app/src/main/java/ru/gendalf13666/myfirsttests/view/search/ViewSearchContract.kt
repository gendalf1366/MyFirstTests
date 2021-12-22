package ru.gendalf13666.myfirsttests.view.search

import ru.gendalf13666.myfirsttests.view.ViewContract
import ru.gendalf13666.myfirsttests.model.SearchResult

internal interface ViewSearchContract : ViewContract {
    fun displaySearchResults(
        searchResults: List<SearchResult>,
        totalCount: Int
    )

    fun displayError()
    fun displayError(error: String)
    fun displayLoading(show: Boolean)
}
