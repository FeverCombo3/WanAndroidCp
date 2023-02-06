package com.yjq.cp.composeable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.yjq.cp.bean.ProjectTreeData
import com.yjq.cp.bean.PublicNumChapterData
import com.yjq.cp.ui.theme.KeyNavigationRoute
import com.yjq.cp.ui.theme.Nav
import com.yjq.cp.viewModel.ProjectViewModel
import com.yjq.cp.viewModel.PublicNumViewModel

/**
 * Author : YJQ
 * Time : 2022/12/14 7:54 PM
 * Description : 文件描述
 */

@Composable
fun MainCompose(
    navHostC: NavHostController = rememberNavController(),
    onFinished: () -> Unit,
) {

    Scaffold(
        contentColor = MaterialTheme.colors.background,
        topBar = { TopToolBar(Nav.bottomNavRoute.value, navHostC) },
        bottomBar = { BottomNavBar(Nav.bottomNavRoute.value, navHostC) }
    ) {
        NavHost(navController = navHostC, modifier = Modifier.padding(it),
            startDestination = KeyNavigationRoute.MAIN.route, builder = {
                navigation(
                    route = KeyNavigationRoute.MAIN.route,
                    startDestination = Nav.BottomNavScreen.HomeScreen.name
                ) {
                    composable(Nav.BottomNavScreen.HomeScreen.name) {
                        HomeCompose(navHostController = navHostC)
                    }

                    composable(Nav.BottomNavScreen.ProjectScreen.name) {
                        ProjectCompose(navHostController = navHostC)
                    }

                    composable(Nav.BottomNavScreen.SquareScreen.name) {
                        SquareCompose(navHostController = navHostC)
                    }

                    composable(Nav.BottomNavScreen.PublicNumScreen.name) {
                        PublicNumCompose(navHostController = navHostC)
                    }   

                    composable(Nav.BottomNavScreen.MineScreen.name) {
                        MineCompose(navHostController = navHostC)
                    }
                }

                composable(KeyNavigationRoute.WEBVIEW.route){

                }
            })
    }
}

@Composable
fun TopToolBar(bottomNavScreen: Nav.BottomNavScreen, navHostController: NavHostController) {
    when (bottomNavScreen) {
        Nav.BottomNavScreen.HomeScreen -> {
            com.yjq.cp.ui.theme.AppBar(
                title = bottomNavScreen.name,
                rightIcon = Icons.Default.Search
            )
        }
        Nav.BottomNavScreen.ProjectScreen -> {

            val projectViewModel: ProjectViewModel = viewModel()

            projectViewModel.getProjectTreeData()

            val treeState = projectViewModel.projectTreeStateFlow.collectAsState()

            ProjectTab(projectBarIndex = Nav.projectTabBarIndex, projectTreeData = treeState)

        }
        Nav.BottomNavScreen.SquareScreen ->{
            
            SquareTab(squareTabBarIndex = Nav.squareTabBarIndex)
        }
        Nav.BottomNavScreen.PublicNumScreen ->{

            val publicNumViewModel : PublicNumViewModel = viewModel()

            publicNumViewModel.getPubNumChapterNum()

            val publicNumState = publicNumViewModel.publicNumChapter.collectAsState()

            PublicNumTab(Nav.publicNumTabBarIndex,publicNumState)
        }
        Nav.BottomNavScreen.MineScreen ->{
            com.yjq.cp.ui.theme.AppBar(elevation = 0.dp)
        }
    }
}

//底部导航栏列表
val items = listOf(
    Nav.BottomNavScreen.HomeScreen,
    Nav.BottomNavScreen.ProjectScreen,
    Nav.BottomNavScreen.SquareScreen,
    Nav.BottomNavScreen.PublicNumScreen,
    Nav.BottomNavScreen.MineScreen
)

@Composable
fun BottomNavBar(bottomNavScreen: Nav.BottomNavScreen, navHostController: NavHostController) {
    BottomNavigation(backgroundColor = MaterialTheme.colors.primary) {
        items.forEach {
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(it.id),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                    )
                },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.secondaryVariant,
                label = { Text(text = stringResource(id = it.resId)) },
                selected = it == bottomNavScreen,
                onClick = {
                    //判断是否是当前的route,如果是就不做处理
                    if (it == bottomNavScreen) {
                        return@BottomNavigationItem
                    }
                    //记录当前的Item
                    Nav.bottomNavRoute.value = it
                    navHostController.navigate(it.name) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier.background(MaterialTheme.colors.background)
            )
        }
    }
}

@Composable
private fun ProjectTab(
    projectBarIndex: MutableState<Int>,
    projectTreeData: State<List<ProjectTreeData>?>,
) {
    if (projectTreeData.value == null || projectTreeData.value!!.isEmpty()) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.primary)
                .fillMaxWidth()
                .height(54.dp)
        )
        return
    }

    ScrollableTabRow(
        selectedTabIndex = projectBarIndex.value,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        backgroundColor = MaterialTheme.colors.primary,
        edgePadding = 0.dp) {
        projectTreeData.value!!.forEachIndexed { index, projectTreeData ->
            Tab(
                selected = index == projectBarIndex.value,
                onClick = { projectBarIndex.value = index }) {

                Text(projectTreeData.name ?: "",
                    modifier = Modifier.height(50.dp),
                    textAlign = TextAlign.Center)
            }
        }
    }
}

val squareTopBarList = listOf("广场", "每日一问", "体系", "导航")

@Composable
private fun SquareTab(squareTabBarIndex: MutableState<Int>) {

    ScrollableTabRow(
        selectedTabIndex = squareTabBarIndex.value,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        backgroundColor = MaterialTheme.colors.primary) {

        squareTopBarList.forEachIndexed { index, data ->
            Tab(selected = index == squareTabBarIndex.value,
                onClick = {
                    squareTabBarIndex.value = index
                }) {
                Text(text = data)
            }
        }
    }
}

/**
 * 公众号页面顶部的指示器
 */
@Composable
private fun PublicNumTab(
    publicNumIndex: MutableState<Int>,
    publicNumChapterData: State<List<PublicNumChapterData>?>
) {

    if (publicNumChapterData.value == null || publicNumChapterData.value!!.isEmpty()) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.primary)
                .fillMaxWidth()
                .height(54.dp)
        )
        return
    }

    ScrollableTabRow(
        selectedTabIndex = publicNumIndex.value,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        //边缘padding
        edgePadding = 0.dp,
        backgroundColor = MaterialTheme.colors.primary
    ) {
        publicNumChapterData.value!!.forEachIndexed { index, item ->
            Tab(
                text = { Text(item.name ?: "") },
                selected = publicNumIndex.value == index,
                onClick = {
                    publicNumIndex.value = index
                }
            )
        }
    }
}