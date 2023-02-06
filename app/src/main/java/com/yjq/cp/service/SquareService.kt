package com.yjq.cp.service

import com.yjq.cp.bean.NaviData
import com.yjq.cp.bean.SystemData
import com.yjq.cp.bean.UserArticleListData
import com.yjq.net.model.BaseResponse
import com.yjq.net.paging.CommonPageModel
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Author : YJQ
 * Time : 2023/1/4 5:29 PM
 * Description : 文件描述
 */
interface SquareService {

    //广场数据
    @GET("user_article/list/{page}/json")
    suspend fun getUserArticleList(@Path("page") page: Int): CommonPageModel<UserArticleListData>

    //问答数据
    @GET("wenda/list/{page}/json")
    suspend fun getQuestionAnswers(@Path("page") page: Int): CommonPageModel<UserArticleListData>

    //体系数据
    @GET("tree/json")
    fun getSystem() : Flow<BaseResponse<List<SystemData>>>

    //导航数据
    @GET("navi/json")
    fun getNavi() : Flow<BaseResponse<List<NaviData>>>
}