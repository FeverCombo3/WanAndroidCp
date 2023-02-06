package com.yjq.cp

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Author : YJQ
 * Time : 2023/1/6 4:03 PM
 * Description : 文件描述
 */


val coldFLow : Flow<Int> = flow<Int> {

    while (true){
        emit(10)
        delay(100)
    }

}

fun iii(context : CoroutineContext){
    CoroutineScope(context).launch {
        coldFLow.collect {

        }
    }
}

//
//class Outer{
//    val name : String = ""
//    final val b : String
//    final val c : Int = 0
//
//    constructor(b : String){
//        this.b = b
//    }
//
//    inner class Inner {
//        final val a : Int
//            get() = 1
//
//        val outer = Outer()
//
//        fun aa(){
//            outer.name
//        }
//    }
//
//}

