package net.plantix.demo.data.model

import com.google.gson.annotations.SerializedName

data class ModelResponseContent (
    @SerializedName("name") val name : String,
    @SerializedName("title") val title : String
)