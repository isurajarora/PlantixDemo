package net.plantix.demo.data.Repository

import androidx.lifecycle.MutableLiveData
import net.plantix.demo.data.model.APIresult
import net.plantix.demo.data.model.ModelResponseContent
import net.plantix.demo.data.model.ModelResponseNamingAPI
import net.plantix.demo.data.model.ModelUserName
import net.plantix.demo.data.retrofit.ApiHelper
import net.plantix.demo.util.application
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryUsername(private val apiHelper: ApiHelper) {
    suspend fun getUsernameFromDB() : List<ModelUserName>
    {
        return application.database!!.usernameDao().getAllUserName()
    }

    suspend fun getUsercountDB() : Int
    {
        return application.database!!.usernameDao().getUserCount()
    }

    suspend fun insertUsernames(list: ArrayList<ModelUserName>)
    {
        application.database!!.usernameDao().insertAllUserName(list)
    }

    suspend fun getUsernameFromAPI(callback:(APIresult<Any?>) -> Unit)  {
        val _names = MutableLiveData<List<ModelUserName>>()
        val locallist: ArrayList<ModelUserName> = arrayListOf()
        apiHelper.getUserNamesFromAPI()
            .enqueue(object : Callback<ModelResponseNamingAPI> {
                override fun onResponse(
                    call: Call<ModelResponseNamingAPI>?,
                    response: Response<ModelResponseNamingAPI>
                ) {
                    if (response.isSuccessful) {
                        val list: List<ModelResponseContent>? =
                            response.body()!!.contents.get(0)
                        for (i in list!!.indices) {
                            val name: String = list!![i].name
                            val username = ModelUserName(UserName = name)
                            locallist.add(username)
                        }
                        _names.value = locallist
                        callback.invoke(APIresult.Success(locallist))
                    } else {
                        callback.invoke(APIresult.failure(response.message() ?: "Error occured!"))
                    }
                }
                override fun onFailure(call: Call<ModelResponseNamingAPI>?, error: Throwable?) {
                    callback.invoke(APIresult.failure<String>(error?.message.toString()))
                }
            })
    }
}