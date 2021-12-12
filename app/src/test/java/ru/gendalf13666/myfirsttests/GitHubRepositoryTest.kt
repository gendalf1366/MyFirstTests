package ru.gendalf13666.myfirsttests

import okhttp3.Request
import okio.Timeout
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gendalf13666.myfirsttests.model.SearchResponse
import ru.gendalf13666.myfirsttests.repository.GitHubApi
import ru.gendalf13666.myfirsttests.repository.GitHubRepository

class GitHubRepositoryTest {

    private lateinit var repository: GitHubRepository

    @Mock
    private lateinit var gitHubApi: GitHubApi

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = GitHubRepository(gitHubApi)
    }

    @Test
    fun searchGithub_Test() {
        val searchQuery = "some query"
        val call = mock(Call::class.java) as Call<SearchResponse?>

        `when`(gitHubApi.searchGithub(searchQuery)).thenReturn(call)

        verify(gitHubApi, times(1)).searchGithub(searchQuery)
    }

    @Test
    fun searchGithub_TestCallback() {
        val searchQuery = "some query"
        val response = mock(Response::class.java) as Response<SearchResponse?>
//        val gitHubRepositoryCallBack = mock(GitHubRepositoryCallback::class.java)

        val call = object : Call<SearchResponse?> {
            override fun enqueue(callback: Callback<SearchResponse?>) {
                callback.onResponse(this, response)
                callback.onFailure(this, Throwable())
            }

            override fun clone(): Call<SearchResponse?> {
                TODO("Not yet implemented")
            }

            override fun execute(): Response<SearchResponse?> {
                TODO("Not yet implemented")
            }

            override fun isExecuted(): Boolean {
                TODO("Not yet implemented")
            }

            override fun cancel() {
            }

            override fun isCanceled(): Boolean {
                TODO("Not yet implemented")
            }

            override fun request(): Request {
                TODO("Not yet implemented")
            }

            override fun timeout(): Timeout {
                TODO("Not yet implemented")
            }
        }

        `when`(gitHubApi.searchGithub(searchQuery)).thenReturn(call)
    }

    @Test
    fun searchGithub_TestCallback_WithMock() {
        val searchQuery = "some query"
        val call = mock(Call::class.java) as Call<SearchResponse?>
        val callBack = mock(Callback::class.java) as Callback<SearchResponse?>
        val response = mock(Response::class.java) as Response<SearchResponse?>

        `when`(gitHubApi.searchGithub(searchQuery)).thenReturn(call)
        `when`(call.enqueue(callBack)).then {
            callBack.onResponse(any(), any())
        }
    }
}
