package com.girish.openpr.model.repository

import androidx.annotation.VisibleForTesting
import com.girish.openpr.model.data.PullRequest
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class ProjectRepository(var githubService : GithubApiService) {

    private val PAGE_SIZE = 10
    // query dependent values
    private var lastPageNo = 1
    private var author = ""
    private var repo = ""

    /*--------------------------- USE CASES ------------------------------*/

    @VisibleForTesting
    open fun getPullRequests(author: String, repo: String): Observable<List<PullRequest>> {

        // setting new values
        lastPageNo = 1
        this.author = author
        this.repo = repo

        return getPullRequestsInternal()
    }

    fun getMorePullRequests(): Observable<List<PullRequest>> {
        lastPageNo += 1
        return getPullRequestsInternal()
    }

    private fun getPullRequestsInternal(): Observable<List<PullRequest>> {

        // TODO: is there a scheduler for network requests?
        val observable = githubService
            .getPullRequests(author, repo, lastPageNo, PAGE_SIZE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        // TODO: limit the responsibility of thread management to this class, one way to do is to use LiveData
        return observable
    }

}