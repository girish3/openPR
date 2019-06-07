package com.girish.openpr.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.girish.openpr.model.data.PullRequest
import com.girish.openpr.model.repository.ProjectRepository
import io.reactivex.Observable

class PRViewModel : ViewModel() {

    private lateinit var repository : ProjectRepository
    private val uiState : MutableLiveData<UIState> = MutableLiveData()

    init {
        injectDependencies()
    }

    private fun injectDependencies() {
        repository = ProjectRepository
    }

    // TODO: should you rather be returning LiveData?
    fun fetchPullRequest(author: String, repo: String) {
        // TODO: RxJava use disposable?
        repository.getPullRequests(author, repo)
            .subscribe(this@PRViewModel::handleResults, this@PRViewModel::handleError)
        uiState.value = UIState.LOADING()
    }

    private fun handleResults(pullRequests: List<PullRequest>) {
        if (pullRequests.size > 0) {
            uiState.value = UIState.SUCCESS(pullRequests)
        } else {
            uiState.value = UIState.EMPTY()
        }
    }

    private fun handleError(throwable: Throwable) {
        uiState.value = UIState.ERROR(throwable.message)
    }

    fun getUIState() : LiveData<UIState> {
        return uiState
    }

    sealed class UIState {
        data class SUCCESS(val pullRequests : List<PullRequest>) : UIState()
        class LOADING : UIState()
        class EMPTY : UIState()
        data class ERROR(val message : String?) : UIState()
    }
}