package com.yjq.cp.composeable

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.yjq.cp.custom.SwipeRefreshContent
import com.yjq.cp.ui.theme.Banner
import com.yjq.cp.ui.theme.HomeCardItemContent
import com.yjq.cp.ui.theme.KeyNavigationRoute
import com.yjq.cp.ui.theme.getAuthor
import com.yjq.cp.viewModel.HomeViewModel

/**
 * Author : YJQ
 * Time : 2022/12/22 4:13 PM
 * Description : 文件描述
 */

@OptIn(ExperimentalPagerApi::class, ExperimentalCoilApi::class)
@Composable
fun HomeCompose(navHostController: NavHostController) {

    val homeViewModel: HomeViewModel = viewModel()

    // 列表数据
    val homeListData = homeViewModel.homeListData.collectAsLazyPagingItems()

    // Banner
    homeViewModel.banners()


    val bannerState = homeViewModel.bannerUiState2.collectAsState()

    SwipeRefreshContent(
        viewModel = homeViewModel,
        lazyPagingListData = homeListData,
        itemContent = {
            item {
                Banner(bannerState.value){ link ->
                    navHostController.navigate("${KeyNavigationRoute.WEBVIEW.route}?url=$link")
                }
            }
        }
    ) { index, data ->
        data.apply {
            HomeCardItemContent(
                getAuthor(author, shareUser),
                fresh,
                false,
                niceDate ?: "刚刚",
                title ?: "",
                superChapterName ?: "未知",
                collect
            ) {
                navHostController.navigate("${KeyNavigationRoute.WEBVIEW.route}?url=$link")
            }
        }
    }
}