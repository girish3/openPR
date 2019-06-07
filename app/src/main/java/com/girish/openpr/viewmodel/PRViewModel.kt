package com.girish.openpr.viewmodel

import androidx.lifecycle.ViewModel
import com.girish.openpr.model.data.PullRequest
import com.girish.openpr.model.repository.ProjectRepository
import io.reactivex.Observable

class PRViewModel : ViewModel() {

    private lateinit var repository : ProjectRepository

    init {
        injectDependencies()
    }

    private fun injectDependencies() {
        repository = ProjectRepository
    }

    // TODO: should you rather be returning LiveData?
    fun getPullRequests(author: String, repo: String) : Observable<List<PullRequest>> {
        return repository.getPullRequests(author, repo)
    }
}