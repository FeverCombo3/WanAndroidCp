package com.yjq.cp.service

import com.yjq.cp.bean.PublicNumChapterData
import com.yjq.cp.bean.PublicNumListData
import com.yjq.net.model.BaseResponse
import com.yjq.net.paging.CommonPageModel
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Author : YJQ
 * Time : 2023/1/5 5:54 PM
 * Description : 文件描述
 */
interface PubNumService {

    @GET("wxarticle/chapters/json")
    fun getPublicNumChapter(): Flow<BaseResponse<List<PublicNumChapterData>>>

    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getPublicNumList(@Path("id") id: Int, @Path("page") page: Int): CommonPageModel<PublicNumListData>
}