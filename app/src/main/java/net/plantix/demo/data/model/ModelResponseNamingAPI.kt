package net.plantix.demo.data.model

import com.google.gson.annotations.SerializedName

data class ModelResponseNamingAPI(@SerializedName("contents")
                             val contents: List<List<ModelResponseContent>>)