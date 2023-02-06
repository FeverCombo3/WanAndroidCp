package com.yjq.cp.custom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.yjq.common.BaseSwipeViewModel
import com.yjq.cp.ui.theme.ErrorComposable
import com.yjq.cp.ui.theme.PagingStateUtil
import com.yjq.cp.ui.theme.SimpleCard

/**
 * Author : YJQ
 * Time : 2022/12/29 7:00 PM
 * Description : 文件描述
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T : Any> SwipeRefreshContent(
    viewModel: BaseSwipeViewModel,
    cardHeight: Dp = 120.dp,
    state: LazyListState = rememberLazyListState(),
    lazyPagingListData : LazyPagingItems<T>,
    itemContent: LazyListScope.() -> Unit = {},
    content: @Composable (index: Int, data: T) -> Unit
){
    Column(Modifier.fillMaxSize()) {

        val pullRefreshState = rememberPullRefreshState(viewModel.isRefreshing.value, {
            viewModel.showRefreshingUI()
            lazyPagingListData.refresh()
            viewModel.hideRefreshingUI()
        })
        
        Box(Modifier.pullRefresh(pullRefreshState)){

            PagingStateUtil().pagingStateUtil(lazyPagingListData,viewModel.isRefreshing,viewModel){
                LazyColumn(Modifier.fillMaxSize(),state = state){
                    itemContent()
                    itemsIndexed(lazyPagingListData){ index, value ->
                        SimpleCard(cardHeight = cardHeight) {
                            content(index,value!!)
                        }
                    }
                }
            }

            PullRefreshIndicator(viewModel.isRefreshing.value, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}

/**
 * 带刷新头的Card布局
 * LazyPagingItems<T>
 * Card高度自适应
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T : Any> SwipeRefreshContent(
    viewModel: BaseSwipeViewModel,
    listData: List<T>?,
    state: LazyListState = rememberLazyListState(),
    noData: () -> Unit,
    content: @Composable (data: T) -> Unit
) {

    if (listData == null) return

    if (listData.isEmpty()) {
        ErrorComposable("暂无数据，请点击重试") {
            noData()
        }
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {

        val pullRefreshState = rememberPullRefreshState(viewModel.isRefreshing.value, {
            viewModel.showRefreshingUI()
            noData()
            viewModel.hideRefreshingUI()
        })

        Box(Modifier.pullRefresh(pullRefreshState)){

            LazyColumn(modifier = Modifier.fillMaxSize(), state = state) {
                itemsIndexed(listData) { index, data ->
                    SimpleCard {
                        content(data)
                    }
                }
            }

            PullRefreshIndicator(viewModel.isRefreshing.value, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}