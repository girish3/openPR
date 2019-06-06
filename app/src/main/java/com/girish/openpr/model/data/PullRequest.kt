package com.girish.openpr.model.data

import com.google.gson.annotations.SerializedName

// TODO: add other relevant fields.
data class PullRequest(
    @SerializedName("author") val author: String,
    @SerializedName("body") val body: String
)