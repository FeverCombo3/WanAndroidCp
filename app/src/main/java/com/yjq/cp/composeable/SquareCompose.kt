package com.yjq.cp.composeable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.yjq.cp.bean.NaviData
import com.yjq.cp.bean.SystemData
import com.yjq.cp.bean.UserArticleListData
import com.yjq.cp.custom.FlowBoxGap
import com.yjq.cp.custom.LabelCustom
import com.yjq.cp.custom.SwipeRefreshContent
import com.yjq.cp.ui.theme.HomeCardItemContent
import com.yjq.cp.ui.theme.KeyNavigationRoute
import com.yjq.cp.ui.theme.Nav
import com.yjq.cp.ui.theme.getAuthor
import com.yjq.cp.viewModel.SquareViewModel

/**
 * Author : YJQ
 * Time : 2023/1/4 5:11 PM
 * Description : 文件描述
 */
@Composable
fun SquareCompose(navHostController: NavHostController) {

    val squareViewModel : SquareViewModel = viewModel()

    val userArticleListData  = squareViewModel.userArticleListData.collectAsLazyPagingItems()
    
    val questionAnswerData = squareViewModel.questionAnswerData.collectAsLazyPagingItems()
    
    val systemData = squareViewModel.systemData.collectAsState()
    
    val naviData = squareViewModel.naviData.collectAsState()
    
    when(Nav.squareTabBarIndex.value){
        0 ->{
            SquareAQComposable(
                navHostController = navHostController,
                listdata = userArticleListData,
                squareViewModel = squareViewModel,
                state = squareViewModel.squareIndexState)
        }
        1 ->{
            SquareAQComposable(
                navHostController = navHostController,
                listdata = questionAnswerData,
                squareViewModel = squareViewModel,
                state = squareViewModel.questionIndexState)
        }
        2 ->{
            if(systemData.value.isEmpty()) squareViewModel.getSystemData()

            SwipeRefreshContent(
                squareViewModel,
                systemData.value,
                state = squareViewModel.systemIndexState,
                noData = { squareViewModel.getSystemData() },
            ) { data ->
                SystemCardItemContent(data.name ?: "", data.children)
            }
        }

        else ->{
            if(naviData.value.isEmpty()) squareViewModel.getNaviData()

            SwipeRefreshContent(
                squareViewModel,
                naviData.value,
                state = squareViewModel.naviIndexState,
                noData = { squareViewModel.getNaviData()})
            { data ->
                NaviCardItemContent(data.name ?: "", data.articles)
            }
        }

    }
}

@Composable
private fun SquareAQComposable(
    navHostController: NavHostController,
    listdata: LazyPagingItems<UserArticleListData>,
    squareViewModel: SquareViewModel,
    state: LazyListState = rememberLazyListState()
){
    SwipeRefreshContent(
        viewModel = squareViewModel,
        lazyPagingListData = listdata,
        state = state) { index, data ->
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
               // navHostController.navigate("${KeyNavigationRoute.WEBVIEW.route}?url=$link")
            }
        }
    }
}

/**
 * 体系页面卡片的内容
 */
@Composable
private fun SystemCardItemContent(title: String, list: List<SystemData.Children?>?) {

    Column(
        //内边距
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 6.dp, bottom = 6.dp, start = 10.dp, end = 10.dp)
    ) {

        Text(
            title,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            color = MaterialTheme.colors.secondaryVariant
        )

        //标签
        LabelCustom(itemGap = FlowBoxGap(6.dp)) {
            list?.forEach { data ->
                Button(onClick = {}) { Text(data?.name ?: "") }
            }
        }


    }

}

/**
 * 体系页面卡片的内容
 */
@Composable
private fun NaviCardItemContent(title: String, list: List<NaviData.Article?>?) {

    Column(
        //内边距
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 6.dp, bottom = 6.dp, start = 10.dp, end = 10.dp)
    ) {

        Text(
            title,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            color = MaterialTheme.colors.secondaryVariant
        )

        //标签
        LabelCustom(itemGap = FlowBoxGap(start = 0.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)) {

            //没有标签的时候
            if (list == null || list.isEmpty()) {
                Text("暂无", fontWeight = FontWeight.Light)
                return@LabelCustom
            }

            list.forEachIndexed { index, article ->
                Button(onClick = {}) { Text(article?.title ?: "") }
            }
        }
    }
}



