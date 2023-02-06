package com.yjq.cp.viewModel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.yjq.common.BaseSwipeViewModel
import com.yjq.cp.bean.ArticleListData
import com.yjq.cp.bean.BannerData
import com.yjq.cp.service.HomeService
import com.yjq.net.ServiceCreators
import com.yjq.net.model.DataResult
import com.yjq.net.paging.CommonPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest

/**
 * Author : YJQ
 * Time : 2022/12/28 5:40 PM
 * Description : 文件描述
 */
class HomeViewModel : BaseSwipeViewModel() {

    val bannerUiState = MutableStateFlow<DataResult<List<BannerData>>>(DataResult.Loading())
    val bannerUiState2 = MutableStateFlow<List<BannerData>>(emptyList())

    //val articleUiState = MutableStateFlow<DataResult<List<ArticleData>>>(DataResult.Loading())

    private val service = ServiceCreators.create(HomeService::class.java)

    fun banners() = serverAwait {
        service.getBanners()
            .catch {
                bannerUiState.value = DataResult.Error(it)
            }
            .collectLatest {
                bannerUiState2.value = it.data
                bannerUiState.value = DataResult.Success(it.data)
            }

    }

    val homeListData : Flow<PagingData<ArticleListData>>
        get() = _homeListData

    private val _homeListData = Pager(PagingConfig(pageSize = 20)){
        CommonPagingSource{ nextPage: Int ->
            service.getArticleList(nextPage)
        }
    }.flow.cachedIn(viewModelScope)
}