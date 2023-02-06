package com.yjq.cp.service

import com.yjq.cp.bean.ArticleListData
import com.yjq.cp.bean.BannerData
import com.yjq.net.model.BaseResponse
import com.yjq.net.paging.CommonPageModel
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Author : YJQ
 * Time : 2022/12/27 10:26 PM
 * Description : 文件描述
 */
interface HomeService {

    @GET("banner/json")
    fun getBanners() : Flow<BaseResponse<List<BannerData>>>


//    /**
//     * 获取首页置顶文章列表
//     * http://www.wanandroid.com/article/top/json
//     */
//    @GET("article/top/json")
//    fun getTopArticles(): Flow<BaseResponse<List<ArticleDetail>>>
//
//    /**
//     * 获取文章列表
//     * http://www.wanandroid.com/article/list/0/json
//     * @param pageNum
//     */
//    @GET("article/list/{pageNum}/json")
//    fun getArticles(@Path("pageNum") pageNum: Int): Flow<BaseResponse<ArticleData>>

    @GET("article/list/{pageNum}/json")
    suspend fun getArticleList(@Path("pageNum") pageNum: Int) : CommonPageModel<ArticleListData>

}