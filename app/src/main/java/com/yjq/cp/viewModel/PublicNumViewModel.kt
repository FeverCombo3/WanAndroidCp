package com.yjq.cp.viewModel

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yjq.common.BaseSwipeViewModel
import com.yjq.cp.StoreData
import com.yjq.cp.bean.PublicNumChapterData
import com.yjq.cp.bean.PublicNumListData
import com.yjq.cp.service.PubNumService
import com.yjq.cp.ui.theme.Nav
import com.yjq.net.ServiceCreators
import com.yjq.net.paging.CommonPagingSource
import kotlinx.coroutines.flow.*

/**
 * Author : YJQ
 * Time : 2023/1/5 5:31 PM
 * Description : 文件描述
 */
class PublicNumViewModel : BaseSwipeViewModel(){

    private val service = ServiceCreators.create(PubNumService :: class.java)

    //公众号页面列表状态
    val publicNumLazyListState: LazyListState = LazyListState()

    private val indexId
        get()  = StoreData.publicNumTabBarStoreData.value?.get(Nav.publicNumTabBarIndex.value)?.id ?: 408

    var savePubNumIndex = 0

    private val _publicNumChapter = MutableStateFlow<List<PublicNumChapterData>>(emptyList())
    val publicNumChapter : StateFlow<List<PublicNumChapterData>>
        get() = _publicNumChapter

    fun getPubNumChapterNum(){
        serverAwait {
            service.getPublicNumChapter()
                .catch {

                }
                .collectLatest {
                    _publicNumChapter.value = it.data
                    StoreData.publicNumTabBarStoreData.postValue(it.data)
                }
        }
    }

    val pubNumListData : Flow<PagingData<PublicNumListData>>
        get() = _publicNumListData

    //获取某个公众号历史文章列表数据
    private val _publicNumListData = Pager(PagingConfig(pageSize = 20)) {
        CommonPagingSource { nextPage: Int ->
            service.getPublicNumList(indexId, nextPage)
        }
    }.flow.cachedIn(viewModelScope)
}