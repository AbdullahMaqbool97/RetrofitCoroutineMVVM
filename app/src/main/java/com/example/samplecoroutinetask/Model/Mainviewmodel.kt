package com.example.samplecoroutinetask.Model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.samplecoroutinetask.Service.retrofitService
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class Mainviewmodel: ViewModel() {
    val retrofitService = retrofitService().getItemService()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val Items = MutableLiveData<List<Items>>()

    fun refresh() {
        fetchUsers()
    }

    private fun fetchUsers() {
        job = CoroutineScope(IO + exceptionHandler).launch {
            val response = retrofitService.getItems("abdullah")
            withContext(Main) {
                if (response.isSuccessful) {
                    Items.value = response.body()?.items
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }
    private fun onError(message: String) {
        Log.d("ErrorMeg", "onError: " + message)
    }
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}