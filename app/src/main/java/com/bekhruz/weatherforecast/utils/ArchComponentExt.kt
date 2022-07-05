package com.bekhruz.weatherforecast.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, Observer { it?.let { t -> action(t) } })
}

fun <T> LifecycleOwner.observe(liveData: MutableLiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, Observer { it?.let { t -> action(t) } })
}

//fun <T> LifecycleOwner.singleObservable(
//    liveData: MutableLiveData<Event<T>>,
//    action: (t: T) -> Unit
//) {
//    liveData.observe(this, Observer {
//        it.getContentIfNotHandled()?.let { t ->
//            action(t)
//        }
//    })
//}
//
//
//fun <T> LifecycleOwner.observe(liveData: LiveEvent<T>, action: (t: T) -> Unit) {
//    liveData.observe(this, Observer { it?.let { t -> action(t) } })
//}
//
//fun <T> LifecycleOwner.observeEvent(liveData: LiveData<Event<T>>, action: (t: Event<T>) -> Unit) {
//    liveData.observe(this, Observer { it?.let { t -> action(t) } })
//}
