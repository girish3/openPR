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
    private val THRESHOLD: Int = 5
    private val _pullRequests = ArrayList<PullRequest>()
    private var isFetching = false
    private var hasMoreData = true

    init {
        injectDependencies()
    }

    private fun injectDependencies() {
        repository = ProjectRepository
    }

    // TODO: should you rather be returning LiveData?
    fun fetchPullRequest(author: String, repo: String) {
        // New Request
        uiState.value = UIState.LOADING()
        _pullRequests.clear()
        hasMoreData = true

        // TODO: RxJava use disposable?
        repository.getPullRequests(author, repo)
            .subscribe(this@PRViewModel::handleResults, this@PRViewModel::handleError)
    }

    fun fetchMore() {
        // TODO: handle error
        repository.getMorePullRequests()
            .subscribe({
                isFetching = false;
                handleResults(it)
            })
    }

    private fun handleResults(pullRequests: List<PullRequest>) {
        if (_pullRequests.isEmpty() && pullRequests.isEmpty()) {
            uiState.value = UIState.EMPTY()
            return
        }

        if (!pullRequests.isEmpty()) {
            _pullRequests.addAll(pullRequests)
            uiState.value = UIState.SUCCESS(_pullRequests)
            return
        }

        hasMoreData = false
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

    fun onListScrolled(lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (!isFetching && hasMoreData
            && (totalItemCount - lastVisibleItemPosition <= THRESHOLD)) {
            isFetching = true;
            fetchMore()
        }
    }
}