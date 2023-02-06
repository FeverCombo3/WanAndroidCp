package com.yjq.cp.service

import com.yjq.cp.bean.ProjectListData
import com.yjq.cp.bean.ProjectTreeData
import com.yjq.net.model.BaseResponse
import com.yjq.net.paging.CommonPageModel
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Author : YJQ
 * Time : 2023/1/3 5:12 PM
 * Description : 文件描述
 */
interface ProjectService {


    /**
     * 项目数据
     * http://www.wanandroid.com/project/tree/json
     */
    @GET("project/tree/json")
    fun getProjectTree(): Flow<BaseResponse<List<ProjectTreeData>>>

    /**
     * 项目列表数据
     * http://www.wanandroid.com/project/list/1/json?cid=294
     * @param page
     * @param cid
     */
    @GET("project/list/{page}/json")
    suspend fun getProjectList(@Path("page") page: Int, @Query("cid") cid: Int): CommonPageModel<ProjectListData>
}