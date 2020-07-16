package com.tootiyesolutions.tpmazembe

import androidx.lifecycle.ViewModelProvider
import com.tootiyesolutions.tpmazembe.api.ApiService
import com.tootiyesolutions.tpmazembe.repository.NewsRepository
import com.tootiyesolutions.tpmazembe.ui.ViewModelFactory

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object Injection {

    /**
     * Creates an instance of [NewsRepository] based on the [NewsService] and a
     * [NewsLocalCache]
     */
    private fun provideNewsRepository(): NewsRepository {
        return NewsRepository(ApiService.create())
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideNewsRepository())
    }
}
