package net.plantix.demo.data.retrofit

import net.plantix.demo.data.model.ModelResponseNamingAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/name/categories.json")
    fun getUserNamesFromAPI(@Query("start") Start: Int, @Query("limit") Limit: Int) : Call<ModelResponseNamingAPI>
}