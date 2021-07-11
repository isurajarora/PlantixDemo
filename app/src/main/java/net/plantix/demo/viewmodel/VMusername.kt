package net.plantix.demo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.plantix.demo.data.Repository.RepositoryUsername
import net.plantix.demo.data.model.APIresult
import net.plantix.demo.data.model.ModelUserName
import net.plantix.demo.util.Resource

class VMusername() : ViewModel() {
    private lateinit var localrepository: RepositoryUsername
    val mutableStateFlow = MutableStateFlow<Resource<Any?>>(Resource.empty())
    val stateFlow: StateFlow<Resource<Any?>> = mutableStateFlow
    fun setRepository(repository: RepositoryUsername) {
        localrepository = repository
    }

    fun insertUsernames(list: ArrayList<ModelUserName>) = viewModelScope.launch{
        localrepository.insertUsernames(list)
    }

    fun getUserNames(isOnline: Boolean) = viewModelScope.launch{
        mutableStateFlow.value = Resource.loading(data = null)
        val usercountDB: Int = localrepository.getUsercountDB()
            if (usercountDB == 0) {
                if (isOnline) {
                    try {
                        localrepository.getUsernameFromAPI() { result ->
                            when (result) {
                                is APIresult.Success<*> -> {
                                    mutableStateFlow.value = Resource.success(data = result.value)
                                        insertUsernames(result.value as ArrayList<ModelUserName>)
                                }
                                is APIresult.Failure<*> -> {
                                    mutableStateFlow.value =  Resource.error(
                                            data = null,
                                            message = result.message ?: "Error Occurred!"
                                        )
                                }
                            }
                        }
                    } catch (exc: Exception) {
                        mutableStateFlow.value =
                            Resource.error(
                                data = null,
                                message = exc.message ?: "Error Occurred!"
                            )
                    }
                } else {
                    mutableStateFlow.value =Resource.error(data = null, message = "Network not available!")
                }
            } else {
                mutableStateFlow.value = Resource.success(data = localrepository.getUsernameFromDB())
            }
        }
}