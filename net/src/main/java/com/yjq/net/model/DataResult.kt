package com.yjq.net.model

import androidx.annotation.Keep

/**
 * Author : YJQ
 * Time : 2022/12/27 7:09 PM
 * Description : 文件描述
 */
@Keep
sealed class DataResult <out T : Any>{

    data class Success<out T: Any>(val data: T) : DataResult<T>()
    data class Loading(val nothing: Nothing? = null) : DataResult<Nothing>()
    data class Error(val exception: Throwable) : DataResult<Nothing>()

}

//val DataResult<*>.succeeded : Boolean
//    get() = this is DataResult.Success && data != null