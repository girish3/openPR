package com.girish.openpr.model.repository

import com.girish.openpr.model.data.PullRequest
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {

    @GET("repos/{owner}/{repo}/pulls")
    fun getPullRequests(@Path("owner") owner: String,
                        @Path("repo") repo: String,
                        @Query("page") pageNo: Int,
                        @Query("per_page") pageSize: Int): Observable<List<PullRequest>>
}