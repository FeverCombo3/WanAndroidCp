package com.yjq.cp

import androidx.lifecycle.MutableLiveData
import com.yjq.cp.bean.ProjectTreeData
import com.yjq.cp.bean.PublicNumChapterData

/**
 * Author : YJQ
 * Time : 2023/1/4 3:35 PM
 * Description : 文件描述
 */
object StoreData {

    val projectTabBarStoreData = MutableLiveData<List<ProjectTreeData>>()

    val publicNumTabBarStoreData = MutableLiveData<List<PublicNumChapterData>>()
}