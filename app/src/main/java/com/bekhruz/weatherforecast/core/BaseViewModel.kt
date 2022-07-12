package com.bekhruz.weatherforecast.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

abstract class BaseViewModel : ViewModel() {
    val errorOther: MutableLiveData<Throwable> = MutableLiveData()
    var loading = MutableLiveData<Boolean>()

    val handler = CoroutineExceptionHandler { _, exception ->
        errorProcess(exception)
    }

    fun <T> Flow<T>.handleErrors(): Flow<T> =
        catch { exception ->
            errorProcess(exception)
        }

    val vmScope = viewModelScope + handler + Dispatchers.IO

    fun errorProcess(throwable: Throwable, f: ((t: Throwable) -> Unit)? = null) {
        viewModelScope.launch {
            loading.postValue(false)
            errorOther.postValue(throwable)
            throwable.printStackTrace()
        }
    }

    fun CoroutineScope.loadingLaunch(suspendCall: suspend () -> Unit) {
        loading.postValue(true)
        launch {
            suspendCall.invoke()
            loading.postValue(false)
        }
    }
}