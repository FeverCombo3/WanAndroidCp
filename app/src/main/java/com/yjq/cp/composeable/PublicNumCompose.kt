package com.yjq.cp.composeable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.yjq.cp.custom.SwipeRefreshContent
import com.yjq.cp.ui.theme.HomeCardItemContent
import com.yjq.cp.ui.theme.KeyNavigationRoute
import com.yjq.cp.ui.theme.Nav
import com.yjq.cp.ui.theme.getAuthor
import com.yjq.cp.viewModel.PublicNumViewModel

/**
 * Author : YJQ
 * Time : 2023/1/4 5:57 PM
 * Description : 文件描述
 */
@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun PublicNumCompose(navHostController: NavHostController) {

    val publicNumViewModel : PublicNumViewModel = viewModel()

    publicNumViewModel.publicNumChapter.collectAsStateWithLifecycle()

    val publicNumListData = publicNumViewModel.pubNumListData.collectAsLazyPagingItems()

    LaunchedEffect(Nav.publicNumTabBarIndex.value){
        if(Nav.publicNumTabBarIndex.value == publicNumViewModel.savePubNumIndex) return@LaunchedEffect

        publicNumViewModel.apply {
            savePubNumIndex = Nav.publicNumTabBarIndex.value
            publicNumLazyListState.scrollToItem(0,0)
        }

        publicNumListData.refresh()
    }


    SwipeRefreshContent(
        viewModel = publicNumViewModel,
        state = publicNumViewModel.publicNumLazyListState,
        lazyPagingListData = publicNumListData) { index, data ->

        data.apply {
            HomeCardItemContent(
                getAuthor(author, shareUser),
                fresh,
                false,
                niceDate ?: "刚刚",
                title ?: "",
                if (superChapterName != null) "$superChapterName" else "未知",
                collect,
                isSpecific = false
            ) {
                navHostController.navigate("${KeyNavigationRoute.WEBVIEW.route}?url=$link")
            }
        }
    }

}