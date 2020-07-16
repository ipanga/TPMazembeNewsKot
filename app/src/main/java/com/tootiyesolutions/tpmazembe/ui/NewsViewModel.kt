package com.tootiyesolutions.tpmazembe.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tootiyesolutions.tpmazembe.model.Article
import com.tootiyesolutions.tpmazembe.repository.NewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

/**
 * ViewModel for the [SearchNewsFragment] screen.
 * The ViewModel works with the [NewsRepository] to get the data.
 */
@ExperimentalCoroutinesApi
class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<Article>>? = null

    fun searchNews(queryString: String): Flow<PagingData<Article>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<Article>> = repository.getSearchResultStream(queryString)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

}
