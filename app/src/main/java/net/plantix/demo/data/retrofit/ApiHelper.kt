package net.plantix.demo.data.retrofit

class ApiHelper (private val apiInterface: ApiInterface,private val start:Int, private val Limit: Int) {
    suspend fun getUserNamesFromAPI() = apiInterface.getUserNamesFromAPI(start,Limit)
}