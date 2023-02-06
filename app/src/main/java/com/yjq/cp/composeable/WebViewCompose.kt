package com.yjq.cp.composeable

import android.content.Context
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.yjq.cp.ui.theme.AppBar

/**
 * Author : YJQ
 * Time : 2023/1/10 3:35 PM
 * Description : 文件描述
 */
@Composable
fun WebViewCompose(navHostController: NavHostController,url : String) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        AppBar("玩Android", leftIcon = Icons.Default.ArrowBack, onLeftClick = {
            navHostController.navigateUp()
        })

        AndroidView({ context: Context ->
            WebView(context).apply {
                layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }
        }, update = {
            //update方法是一个callback, inflate之后会执行, 读取的状态state值变化后也会被执行
            it.apply {
                loadUrl(url)
            }
        })
    }

}