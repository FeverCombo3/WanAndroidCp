package com.yjq.cp.viewModel

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yjq.common.BaseSwipeViewModel
import com.yjq.common.BaseViewModel
import com.yjq.cp.bean.ArticleListData
import com.yjq.cp.bean.NaviData
import com.yjq.cp.bean.SystemData
import com.yjq.cp.bean.UserArticleListData
import com.yjq.cp.service.SquareService
import com.yjq.net.ServiceCreators
import com.yjq.net.paging.CommonPagingSource
import kotlinx.coroutines.flow.*

/**
 * Author : YJQ
 * Time : 2023/1/4 5:16 PM
 * Description : 文件描述
 */
class SquareViewModel : BaseSwipeViewModel(){

    private val service = ServiceCreators.create(SquareService :: class.java)

    //广场
    val squareIndexState: LazyListState = LazyListState()

    //每日一问
    val questionIndexState: LazyListState = LazyListState()

    //体系
    val systemIndexState: LazyListState = LazyListState()

    //导航
    val naviIndexState: LazyListState = LazyListState()

    //广场数据
    val userArticleListData : Flow<PagingData<UserArticleListData>>
        get() = _userArticleListData

    private val _userArticleListData = Pager(PagingConfig(pageSize = 20)){
        CommonPagingSource{ nextPage: Int ->
            service.getUserArticleList(nextPage)
        }
    }.flow.cachedIn(viewModelScope)

    //问答数据
    val questionAnswerData : Flow<PagingData<UserArticleListData>>
        get() = _questionAnswerData

    private val _questionAnswerData = Pager(PagingConfig(pageSize = 20)){
        CommonPagingSource{ nextPage: Int ->
            service.getQuestionAnswers(nextPage)
        }
    }.flow.cachedIn(viewModelScope)

    //体系数据
    val systemData : StateFlow<List<SystemData>>
        get() = _systemData

    private val _systemData = MutableStateFlow<List<SystemData>>(emptyList())

    fun getSystemData(){
        serverAwait {
            service.getSystem()
                .catch {

                }
                .collectLatest {
                    if(it.isSuccess()){
                        _systemData.value = it.data
                    }else{
                        _systemData.value = emptyList()
                    }
                }
        }
    }

    //导航数据
    val naviData : StateFlow<List<NaviData>>
        get() = _naviData

    private val _naviData = MutableStateFlow<List<NaviData>>(emptyList())

    fun getNaviData(){
        serverAwait {
            service.getNavi()
                .catch {

                }
                .collectLatest {
                    if(it.isSuccess()){
                        _naviData.value = it.data
                    }else{
                        _naviData.value = emptyList()
                    }
                }
        }
    }
}