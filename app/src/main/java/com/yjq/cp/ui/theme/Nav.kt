package com.yjq.cp.ui.theme

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateOf
import com.yjq.cp.R

/**
 * Author : YJQ
 * Time : 2022/12/22 3:23 PM
 * Description : 文件描述
 */
object Nav {

    sealed class BottomNavScreen(val name : String, @StringRes val resId : Int, @DrawableRes val id :Int){

        object HomeScreen : BottomNavScreen("home", R.string.bottom_home,R.mipmap.nav_home)
        object ProjectScreen : BottomNavScreen("project",R.string.bottom_project,R.mipmap.nav_project)
        object SquareScreen: BottomNavScreen("square", R.string.bottom_square, R.mipmap.nav_square)
        object PublicNumScreen: BottomNavScreen("publicNum", R.string.bottom_public_num, R.mipmap.nav_public_num)
        object MineScreen: BottomNavScreen("mine", R.string.bottom_mine, R.mipmap.nav_mine)
    }

    //记录BottomNav当前的Item
    val bottomNavRoute = mutableStateOf<BottomNavScreen>(BottomNavScreen.HomeScreen)

    val projectTabBarIndex = mutableStateOf(0)

    val squareTabBarIndex = mutableStateOf(0)

    val publicNumTabBarIndex = mutableStateOf(0)
}