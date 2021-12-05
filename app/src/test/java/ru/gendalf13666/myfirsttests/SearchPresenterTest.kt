package ru.gendalf13666.myfirsttests

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response
import ru.gendalf13666.myfirsttests.model.SearchResponse
import ru.gendalf13666.myfirsttests.model.SearchResult
import ru.gendalf13666.myfirsttests.presenter.SearchPresenter
import ru.gendalf13666.myfirsttests.repository.GitHubRepository
import ru.gendalf13666.myfirsttests.view.ViewContract

class SearchPresenterTest {

    private lateinit var presenter: SearchPresenter

    @Mock
    private lateinit var repository: GitHubRepository

    @Mock
    private lateinit var viewContract: ViewContract

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SearchPresenter(viewContract, repository)
    }

    @Test
    fun searchGitHub_Test() {
        val searchQuery = "some query"
        presenter.searchGitHub("some query")
        Mockito.verify(repository, Mockito.times(1)).searchGithub(searchQuery, presenter)
    }

    @Test
    fun handleGitHubError_Test() {
        presenter.handleGitHubError()
        Mockito.verify(viewContract, Mockito.times(1)).displayError()
    }

    @Test
    fun handleGitHubResponse_ResponseUnsuccessful() {
        val response = Mockito.mock(Response::class.java) as Response<SearchResponse?>
        Mockito.`when`(response.isSuccessful).thenReturn(false)
        Assert.assertFalse(response.isSuccessful)
    }

    @Test
    fun handleGitHubResponse_Failure() {
        val response = Mockito.mock(Response::class.java) as Response<SearchResponse?>
        Mockito.`when`(response.isSuccessful).thenReturn(false)

        presenter.handleGitHubResponse(response)

        Mockito.verify(viewContract, Mockito.times(1))
            .displayError("Response is null or unsuccessful")
    }

    @Test
    fun handleGitHubResponse_ResponseFailure_ViewContractMethodOrder() {
        val response = Mockito.mock(Response::class.java) as Response<SearchResponse?>
        Mockito.`when`(response.isSuccessful).thenReturn(false)
        presenter.handleGitHubResponse(response)

        val inOrder = Mockito.inOrder(viewContract)
        inOrder.verify(viewContract).displayLoading(false)
        inOrder.verify(viewContract).displayError("Response is null or unsuccessful")
    }

    @Test
    fun handleGitHubResponse_ResponseIsEmpty() {
        val response = Mockito.mock(Response::class.java) as Response<SearchResponse?>
        Mockito.`when`(response.body()).thenReturn(null)

        presenter.handleGitHubResponse(response)

        Assert.assertNull(response.body())
    }

    @Test
    fun handleGitHubResponse_ResponseIsNotEmpty() {
        val response = Mockito.mock(Response::class.java) as Response<SearchResponse?>
        Mockito.`when`(response.body()).thenReturn(Mockito.mock(SearchResponse::class.java))

        presenter.handleGitHubResponse(response)

        Assert.assertNotNull(response.body())
    }

    @Test
    fun handleGitHubResponse_EmptyResponse() {
        val response = Mockito.mock(Response::class.java) as Response<SearchResponse?>
        Mockito.`when`(response.isSuccessful).thenReturn(true)
        Mockito.`when`(response.body()).thenReturn(null)
        presenter.handleGitHubResponse(response)

        Mockito.verify(viewContract, Mockito.times(1))
            .displayError("Search results or total count are null")
    }

    @Test
    fun handleGitHubResponse_Success() {
        val response = Mockito.mock(Response::class.java) as Response<SearchResponse?>
        val searchResponse = Mockito.mock(SearchResponse::class.java)
        val searchResults = listOf(Mockito.mock(SearchResult::class.java))

        Mockito.`when`(response.isSuccessful).thenReturn(true)
        Mockito.`when`(response.body()).thenReturn(searchResponse)
        Mockito.`when`(searchResponse.searchResults).thenReturn(searchResults)
        Mockito.`when`(searchResponse.totalCount).thenReturn(101)

        presenter.handleGitHubResponse(response)

        Mockito.verify(viewContract, Mockito.times(1)).displaySearchResults(searchResults, 101)
    }
}
