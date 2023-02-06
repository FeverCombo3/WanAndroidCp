package com.yjq.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Author : YJQ
 * Time : 2022/12/28 5:05 PM
 * Description : 文件描述
 */
abstract class BaseViewModel : ViewModel() {

    private val jobs : MutableList<Job> = mutableListOf()

    val isLoading : MutableLiveData<Boolean> = MutableLiveData(false)

    protected fun serverAwait(block : suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        isLoading.value = true
        block.invoke(this)
        isLoading.value = false
    }.addList(jobs)

    override fun onCleared() {
        jobs.forEach{
            it.cancel()
        }
        super.onCleared()
    }
}

fun Job.addList(jobs : MutableList<Job>){
    jobs.add(this)
}
