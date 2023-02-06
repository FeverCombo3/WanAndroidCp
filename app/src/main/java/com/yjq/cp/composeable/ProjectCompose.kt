package com.yjq.cp.composeable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.yjq.common.ext.transitionDate
import com.yjq.cp.bean.ProjectListData
import com.yjq.cp.custom.BottomCard
import com.yjq.cp.custom.CenterCard
import com.yjq.cp.custom.SwipeRefreshContent
import com.yjq.cp.custom.TopCard
import com.yjq.cp.ui.theme.Nav
import com.yjq.cp.viewModel.ProjectViewModel

/**
 * Author : YJQ
 * Time : 2022/12/22 4:13 PM
 * Description : 文件描述
 */

@Composable
fun ProjectCompose (navHostController: NavHostController){

    val projectViewModel : ProjectViewModel = viewModel()

    val projectListData = projectViewModel.projectListData.collectAsLazyPagingItems()

    LaunchedEffect(Nav.projectTabBarIndex.value){

        if(Nav.projectTabBarIndex.value == projectViewModel.saveTabBarIndex )return@LaunchedEffect

        projectViewModel.apply {
            saveTabBarIndex = Nav.projectTabBarIndex.value
            projectLazyListState.scrollToItem(0,0)
        }

        projectListData.refresh()
    }

    SwipeRefreshContent(
        viewModel = projectViewModel,
        lazyPagingListData = projectListData,
        state = projectViewModel.projectLazyListState,
        cardHeight = 190.dp) { index, data ->

        projectItemContent(data){

        }
    }
}

@Composable
private fun projectItemContent(item : ProjectListData , onClick: () -> Unit){

    Column(
        modifier = Modifier.clickable(onClick = onClick).padding(bottom = 6.dp, top = 6.dp)
            .padding(start = 8.dp, end = 8.dp)
    ) {

        TopCard(item.author ?: "", item.publishTime.transitionDate())

        Row(
            modifier = Modifier.padding(3.dp).weight(1f)
        ) {
            CenterCard(item.envelopePic ?: "", item.title ?: "", item.desc ?: "")
        }

        BottomCard(
            "${item.superChapterName}·${item.chapterName}",
            item.collect
        )
    }
}