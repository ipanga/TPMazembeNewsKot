package com.tootiyesolutions.tpmazembe.repository

import androidx.paging.PagingSource
import com.tootiyesolutions.tpmazembe.api.ApiService
import com.tootiyesolutions.tpmazembe.model.Article
import com.tootiyesolutions.tpmazembe.util.Constants.Companion.NEWS_ITEMS_PER_PAGE
import com.tootiyesolutions.tpmazembe.util.Constants.Companion.NEWS_STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class NewsPagingSource(
    val apiService: ApiService,
    val searchQuery: String
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: NEWS_STARTING_PAGE_INDEX
            val response = apiService.searchForNews(searchQuery, nextPageNumber, NEWS_ITEMS_PER_PAGE)
            return LoadResult.Page(
                data = response,
                prevKey = if (nextPageNumber == NEWS_STARTING_PAGE_INDEX) null else nextPageNumber - 1,
                nextKey = if (response.isEmpty()) null else nextPageNumber + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}