package com.yjq.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Author : YJQ
 * Time : 2022/12/30 5:26 PM
 * Description : 文件描述
 */
abstract class BaseSwipeViewModel : BaseViewModel(){

    var isRefreshing =  mutableStateOf(false)

    fun showRefreshingUI(){
        //显示刷新头
        isRefreshing.value = true
    }

    fun hideRefreshingUI(){
        sleepTime(3000) {
            isRefreshing.value = false
        }
    }
}