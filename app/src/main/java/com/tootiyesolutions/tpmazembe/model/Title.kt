package com.tootiyesolutions.tpmazembe.model


import com.google.gson.annotations.SerializedName

data class Title(
    @SerializedName("rendered")
    val articleTitle: String?
)