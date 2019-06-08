package com.girish.openpr.model.data

import com.google.gson.annotations.SerializedName

data class PullRequest(
    @SerializedName("user") val author: Author,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String,
    @SerializedName("html_url") val prPageUrl: String,
    @SerializedName("number") val prNumber: String,
)