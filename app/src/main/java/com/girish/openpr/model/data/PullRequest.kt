package com.girish.openpr.model.data

import com.google.gson.annotations.SerializedName

// TODO: add other relevant fields.
data class PullRequest(
    @SerializedName("user") val author: Author,
    @SerializedName("body") val body: String,
    @SerializedName("title") val title: String,
    // TODO: time format?
    @SerializedName("created_at") val createdOn : String
)