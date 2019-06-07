package com.girish.openpr.model.repository

import com.girish.openpr.model.data.PullRequest
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

object ProjectRepository {

    private lateinit var retrofit: Retrofit
    private lateinit var githubService : GithubApiService

    init {
        injectDependencies();
    }

    private fun injectDependencies() {
        retrofit = RetrofitClient.getInstance()
        githubService = retrofit.create(GithubApiService::class.java)
    }

    /*--------------------------- USE CASES ------------------------------*/

    fun getPullRequests(author : String, repo: String) : Observable<List<PullRequest>> {

        // TODO: is there a scheduler for network requests?
        val observable = githubService
            .getPullRequests(author, repo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        // TODO: limit the responsibility of thread management to this class, one way to do is to use LiveData
        return observable
    }

}