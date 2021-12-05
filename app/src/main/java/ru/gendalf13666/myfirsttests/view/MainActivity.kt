package ru.gendalf13666.myfirsttests.view

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.gendalf13666.myfirsttests.R
import ru.gendalf13666.myfirsttests.databinding.ActivityMainBinding
import ru.gendalf13666.myfirsttests.model.SearchResult
import ru.gendalf13666.myfirsttests.presenter.PresenterContract
import ru.gendalf13666.myfirsttests.presenter.SearchPresenter
import ru.gendalf13666.myfirsttests.repository.GitHubApi
import ru.gendalf13666.myfirsttests.repository.GitHubRepository
import java.util.*

class MainActivity : AppCompatActivity(), ViewContract {

    private val adapter = SearchResultAdapter()
    private val presenter: PresenterContract = SearchPresenter(this, createRepository())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUI()
    }

    private fun setUI() {
        setQueryListener()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    private fun setQueryListener() {
        searchEditText.setOnEditorActionListener(
            OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val query = searchEditText.text.toString()
                    if (query.isNotBlank()) {
                        presenter.searchGitHub(query)
                        return@OnEditorActionListener true
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            getString(R.string.enter_search_word),
                            Toast.LENGTH_SHORT
                        ).show()
                        return@OnEditorActionListener false
                    }
                }
                false
            }
        )
    }

    private fun createRepository(): GitHubRepository {
        return GitHubRepository(createRetrofit().create(GitHubApi::class.java))
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun displaySearchResults(
        searchResults: List<SearchResult>,
        totalCount: Int
    ) {
        adapter.updateResults(searchResults)
        resultsCountTextView.text =
            String.format(Locale.getDefault(), getString(R.string.results_count), totalCount)
    }

    override fun displayError() {
        Toast.makeText(this, getString(R.string.undefined_error), Toast.LENGTH_SHORT).show()
    }

    override fun displayError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun displayLoading(show: Boolean) {
        if (show) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val BASE_URL = "https://api.github.com"
    }
}
