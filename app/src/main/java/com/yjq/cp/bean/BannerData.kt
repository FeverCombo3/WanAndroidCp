package com.yjq.cp.bean

import androidx.annotation.Keep

/**
 * Author : YJQ
 * Time : 2022/12/27 10:36 PM
 * Description : 文件描述
 */
@Keep
data class BannerData (
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)