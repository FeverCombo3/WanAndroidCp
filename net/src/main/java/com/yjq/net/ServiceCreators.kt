package com.yjq.net

import com.bbgo.common_base.net.FlowCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Author : YJQ
 * Time : 2022/12/27 5:56 PM
 * Description : 文件描述
 */
object ServiceCreators {

    private val BASE_URL = "https://www.wanandroid.com"

    private val mOkHttpClient: OkHttpClient = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)
        //与服务器建立链接的时长，默认10s
        .connectTimeout(10, TimeUnit.SECONDS)
        //读取服务器返回数据的时长
        .readTimeout(10, TimeUnit.SECONDS)
        //向服务器写入数据的时长，默认10s
        .writeTimeout(10, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .addNetworkInterceptor(
            KtHttpLogInterceptor {
                logLevel(KtHttpLogInterceptor.LogLevel.BODY)
                colorLevel(KtHttpLogInterceptor.ColorLevel.DEBUG)
            }
        )
        .build()

    private var retrofitBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(FlowCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(mOkHttpClient)

    private val retrofit: Retrofit = retrofitBuilder.build()

    fun <T> create(clazz: Class<T>): T = retrofit.create(clazz)
}