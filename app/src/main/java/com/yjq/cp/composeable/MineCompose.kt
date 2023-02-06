package com.yjq.cp.composeable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.yjq.cp.R
import com.yjq.cp.viewModel.MineViewModel

/**
 * Author : YJQ
 * Time : 2023/1/4 5:43 PM
 * Description : 文件描述
 */
@Composable
fun MineCompose (navHostController: NavHostController){

    val mineViewModel : MineViewModel = viewModel()

    MineScreen(navHostController,mineViewModel)
}

@Composable
private fun MineScreen(
    navHostController: NavHostController,
    mineViewModel : MineViewModel
){
    Column(modifier = Modifier
        .background(MaterialTheme.colors.primary)
        .fillMaxSize()) {

        HeadAndName()
    }
}

@Composable
private fun HeadAndName(){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .padding(start = 20.dp)) {

        Surface(shape = CircleShape, modifier = Modifier.size(80.dp)) {
            Image(
                painter = painterResource(R.mipmap.ic_account),
                contentDescription = null)
        }

        Column(modifier = Modifier
            .padding(start = 20.dp)
            .fillMaxHeight()
            .weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly) {

            Text("Name", fontWeight = FontWeight.Bold, fontSize = 20.sp)

            Row {
                Text("id: 1234", fontSize = 14.sp)
                Text("排名: 333", modifier = Modifier.padding(start = 20.dp), fontSize = 14.sp)
            }
        }
    }
}