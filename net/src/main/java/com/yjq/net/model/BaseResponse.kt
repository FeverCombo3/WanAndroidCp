package com.yjq.net.model

import androidx.annotation.Keep

/**
 * Author : YJQ
 * Time : 2022/12/27 7:04 PM
 * Description : 文件描述
 */
@Keep
data class BaseResponse<T>(
    val data: T
) {
    var errorCode: Int = 0
    var errorMsg: String = ""

    fun isSuccess() : Boolean = errorCode == SERVER_CODE_SUCCESS

    companion object {
        const val SERVER_CODE_SUCCESS = 0 //接口请求响应业务处理 成功
    }
}