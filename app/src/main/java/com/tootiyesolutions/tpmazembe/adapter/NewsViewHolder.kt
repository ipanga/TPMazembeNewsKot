package com.tootiyesolutions.tpmazembe.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tootiyesolutions.tpmazembe.R
import com.tootiyesolutions.tpmazembe.model.Article

/**
 * View Holder for a [Article] RecyclerView list item.
 */
class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.tvNewsTitle)
    private val content: TextView = view.findViewById(R.id.tvNewsContent)
    private val stars: TextView = view.findViewById(R.id.repo_stars)
    private val language: TextView = view.findViewById(R.id.repo_language)
    private val forks: TextView = view.findViewById(R.id.repo_forks)

    private var article: Article? = null

    init {
        view.setOnClickListener {
            article?.link?.let { link ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                view.context.startActivity(intent)
            }
        }
    }

    fun bind(article: Article?) {
        if (article == null) {
            val resources = itemView.resources
            title.text = resources.getString(R.string.loading)
            content.visibility = View.GONE
            language.visibility = View.GONE
            stars.text = resources.getString(R.string.unknown)
            forks.text = resources.getString(R.string.unknown)
        } else {
            showRepoData(article)
        }
    }

    private fun showRepoData(article: Article) {
        this.article = article
        title.text = article.title?.articleTitle

        // if the description is missing, hide the TextView
        var contentVisibility = View.GONE
        if (article.content?.articleContent != null) {
            content.text = article.content.articleContent
            contentVisibility = View.VISIBLE
        }
        content.visibility = contentVisibility

//        stars.text = article.stars.toString()
//        forks.text = article.forks.toString()

        // if the language is missing, hide the label and the value
        var languageVisibility = View.GONE
//        if (!article.language.isNullOrEmpty()) {
//            val resources = this.itemView.context.resources
//            language.text = resources.getString(R.string.language, article.language)
//            languageVisibility = View.VISIBLE
//        }
        language.visibility = languageVisibility
    }

    companion object {
        fun create(parent: ViewGroup): NewsViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_news_view, parent, false)
            return NewsViewHolder(view)
        }
    }
}