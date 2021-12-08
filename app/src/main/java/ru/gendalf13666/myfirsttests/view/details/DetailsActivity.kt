package ru.gendalf13666.myfirsttests.view.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_details.*
import ru.gendalf13666.myfirsttests.R
import ru.gendalf13666.myfirsttests.databinding.ActivityDetailsBinding
import ru.gendalf13666.myfirsttests.presenter.details.DetailsPresenter
import ru.gendalf13666.myfirsttests.presenter.details.PresenterDetailsContract
import java.util.*

class DetailsActivity : AppCompatActivity(), ViewDetailsContract {

    private val presenter: PresenterDetailsContract = DetailsPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUI()
        presenter.onAttach()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

    private fun setUI() {
        val count = intent.getIntExtra(TOTAL_COUNT_EXTRA, 0)
        presenter.setCounter(count)
        setCountText(count)
        decrementButton.setOnClickListener { presenter.onDecrement() }
        incrementButton.setOnClickListener { presenter.onIncrement() }
    }

    override fun setCount(count: Int) {
        setCountText(count)
    }

    private fun setCountText(count: Int) {
        totalCountTextView.text =
            String.format(Locale.getDefault(), getString(R.string.results_count), count)
    }

    companion object {

        const val TOTAL_COUNT_EXTRA = "TOTAL_COUNT_EXTRA"

        fun getIntent(context: Context, totalCount: Int): Intent {
            return Intent(context, DetailsActivity::class.java).apply {
                putExtra(TOTAL_COUNT_EXTRA, totalCount)
            }
        }
    }
}
