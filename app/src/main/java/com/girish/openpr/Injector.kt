package com.girish.openpr

import androidx.lifecycle.ViewModelProvider
import com.girish.openpr.model.repository.GithubApiService
import com.girish.openpr.model.repository.ProjectRepository
import com.girish.openpr.model.repository.RetrofitClient
import com.girish.openpr.viewmodel.ViewModelFactory

/**
 * Using Singleton to inject dependencies so that dependencies can be mocked during testing
 */
object Injector {

    private fun provideRepository(): ProjectRepository {
        return ProjectRepository(RetrofitClient.getInstance().create(GithubApiService::class.java))
    }

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideRepository())
    }
}
