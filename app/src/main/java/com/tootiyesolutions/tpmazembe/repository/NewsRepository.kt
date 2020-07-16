package com.tootiyesolutions.tpmazembe.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tootiyesolutions.tpmazembe.api.ApiService
import com.tootiyesolutions.tpmazembe.model.Article
import com.tootiyesolutions.tpmazembe.util.Constants.Companion.NEWS_ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow

/**
 * Repository class that works with local and remote data sources.
 */
class NewsRepository(private val apiService: ApiService) {

    /**
     * Search repositories whose names match the query, exposed as a stream of data that will emit
     * every time we get more data from the network.
     */
    fun getSearchResultStream(searchQuery: String): Flow<PagingData<Article>> {
        Log.d("GithubRepository", "New query: $searchQuery")
        return Pager(
            config = PagingConfig(pageSize = NEWS_ITEMS_PER_PAGE, prefetchDistance = 2, enablePlaceholders = false),
            pagingSourceFactory = { NewsPagingSource(apiService, searchQuery) }
        ).flow
    }
}