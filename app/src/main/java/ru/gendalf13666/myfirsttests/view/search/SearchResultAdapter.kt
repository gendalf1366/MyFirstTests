package ru.gendalf13666.myfirsttests.view.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.gendalf13666.myfirsttests.view.search.SearchResultAdapter.SearchResultViewHolder
import kotlinx.android.synthetic.main.list_item.view.*
import ru.gendalf13666.myfirsttests.R
import ru.gendalf13666.myfirsttests.model.SearchResult

internal class SearchResultAdapter : RecyclerView.Adapter<SearchResultViewHolder>() {

    private var results: List<SearchResult> = listOf()

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchResultViewHolder {
        return SearchResultViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, null)
        )
    }

    override fun onBindViewHolder(
        holder: SearchResultViewHolder,
        position: Int
    ) {
        holder.bind(results[position])
    }

    override fun getItemCount(): Int {
        return results.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateResults(results: List<SearchResult>) {
        this.results = results
        notifyDataSetChanged()
    }

    internal class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(searchResult: SearchResult) {
            itemView.repositoryName.text = searchResult.fullName
            itemView.repositoryName.setOnClickListener {
                Toast.makeText(itemView.context, searchResult.fullName, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
