package com.tootiyesolutions.tpmazembe.model

import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("rendered")
    val articleContent: String?
)