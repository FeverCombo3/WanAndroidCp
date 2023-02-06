package com.yjq.cp.bean

import androidx.annotation.Keep

/**
 * 公众号列表数据
 */
@Keep
data class PublicNumChapterData(
    val children: List<Any?>?,
    val courseId: Int,
    val id: Int,
    val name: String?,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)

/**
 * 公众号历史文章列表
 */
@Keep
data class PublicNumListData(
    val apkLink: String?,
    val audit: Int,
    val author: String?,
    val canEdit: Boolean,
    val chapterId: Int,
    val chapterName: String?,
    val collect: Boolean,
    val courseId: Int,
    val desc: String?,
    val descMd: String?,
    val envelopePic: String?,
    val fresh: Boolean,
    val host: String?,
    val id: Int,
    val link: String?,
    val niceDate: String?,
    val niceShareDate: String?,
    val origin: String?,
    val prefix: String?,
    val projectLink: String?,
    val publishTime: Long,
    val realSuperChapterId: Int,
    val selfVisible: Int,
    val shareDate: Long,
    val shareUser: String?,
    val superChapterId: Int,
    val superChapterName: String?,
    val tags: List<Tag?>?,
    val title: String?,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
) {
    @Keep
    data class Tag(
        val name: String?,
        val url: String?
    )
}