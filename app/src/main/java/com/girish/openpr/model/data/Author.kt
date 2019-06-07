package com.girish.openpr.model.data

import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("login") val username : String,
    @SerializedName("html_url") val pageUrl : String,
    @SerializedName("avatar_url") val avatarUrl : String
)