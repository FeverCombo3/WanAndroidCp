package com.yjq.net.paging

import androidx.annotation.Keep

/**
 * Author : YJQ
 * Time : 2022/12/29 5:45 PM
 * Description : 文件描述
 */
@Keep
data class CommonPageModel<T>(val data: Data<T>) {

    @Keep
    data class Data<T>(
        val curPage: Int,
        val datas: List<T>,
        val offset: Int,
        val over: Boolean,
        val pageCount: Int,
        val size: Int,
        val total: Int,
    )

}