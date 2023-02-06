package com.yjq.cp.viewModel

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yjq.common.BaseSwipeViewModel
import com.yjq.common.BaseViewModel
import com.yjq.cp.StoreData
import com.yjq.cp.bean.ProjectListData
import com.yjq.cp.bean.ProjectTreeData
import com.yjq.cp.service.HomeService
import com.yjq.cp.service.ProjectService
import com.yjq.cp.ui.theme.Nav
import com.yjq.net.ServiceCreators
import com.yjq.net.paging.CommonPageModel
import com.yjq.net.paging.CommonPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest

/**
 * Author : YJQ
 * Time : 2023/1/3 5:20 PM
 * Description : 文件描述
 */
class ProjectViewModel : BaseSwipeViewModel(){

    private val service = ServiceCreators.create(ProjectService::class.java)

    val projectLazyListState: LazyListState = LazyListState()

    var saveTabBarIndex = 0

    private val indexCid
        get() = StoreData.projectTabBarStoreData.value?.get(Nav.projectTabBarIndex.value)?.id ?: 0

    val projectTreeStateFlow = MutableStateFlow<List<ProjectTreeData>>(emptyList())

    fun getProjectTreeData(){
        serverAwait {
            service.getProjectTree()
                .catch {

                }
                .collectLatest {
                    projectTreeStateFlow.value = it.data
                    StoreData.projectTabBarStoreData.postValue(it.data)
                }
        }
    }

    val projectListData : Flow<PagingData<ProjectListData>>
        get() = _projectListData

    private val _projectListData = Pager(PagingConfig(pageSize = 20)) {
        CommonPagingSource { nextPage: Int ->
            service.getProjectList(nextPage, indexCid)
        }
    }.flow.cachedIn(viewModelScope)
}