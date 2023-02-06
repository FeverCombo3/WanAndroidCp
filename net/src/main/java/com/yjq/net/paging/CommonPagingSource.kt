package com.yjq.net.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

/**
 * Author : YJQ
 * Time : 2022/12/29 5:41 PM
 * Description : 文件描述
 */
class CommonPagingSource<T : Any>(private val block : suspend (nextPage : Int) -> CommonPageModel<T>) : PagingSource<Int,T>(){

    override fun getRefreshKey(state: PagingState<Int, T>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val nextPage = params.key ?: 0
            val response = block.invoke(nextPage)
            LoadResult.Page(
                data = response.data.datas,
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = if (nextPage < response.data.pageCount) nextPage + 1 else null
            )
        }catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}