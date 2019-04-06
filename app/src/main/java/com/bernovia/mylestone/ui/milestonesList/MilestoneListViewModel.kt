package com.bernovia.mylestone.ui.milestonesList

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.bernovia.mylestone.R
import com.bernovia.mylestone.base.BaseViewModel
import com.bernovia.mylestone.ui.milestonesList.model.Milestone
import com.bernovia.mylestone.network.ApiEndPoint.ACCEPT
import com.bernovia.mylestone.network.ApiEndPoint.CONTENT_TYPE
import com.bernovia.mylestone.network.MilestoneApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MilestoneListViewModel : BaseViewModel(){
    @Inject
    lateinit var milestoneApi: MilestoneApi
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadMilestones() }
    val milestoneListAdapter: MilestoneListAdapter =
        MilestoneListAdapter()

    private lateinit var subscription: Disposable

    init{
        loadMilestones()
    }

    private fun loadMilestones(){

//        subscription = Observable.fromCallable { milestoneDao.all }
//            .concatMap {
//                    dbPostList ->
//                if(dbPostList.isEmpty())
//                    milestoneApi.getMilestones(CONTENT_TYPE,ACCEPT).concatMap {
//                            apiPostList -> milestoneDao.insertAll(*apiPostList.milestones.toTypedArray())
//                        Observable.just(apiPostList)
//                    }
//                else
//                    Observable.just(dbPostList)
//            }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSubscribe { onRetrieveMilestonesListStart() }
//            .doOnTerminate { onRetrieveMilestonesListFinish() }
//            .subscribe(
//                { result -> onRetrieveMilestonesListSuccess(result as List<Milestone>) },
//                { onRetrieveMilestonesListError() }
//            )


        subscription = milestoneApi.getMilestones(CONTENT_TYPE,ACCEPT)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveMilestonesListStart() }
            .doOnTerminate { onRetrieveMilestonesListFinish() }
            .subscribe(
                { result -> onRetrieveMilestonesListSuccess(result.milestones) },
                { onRetrieveMilestonesListError() }
            )
    }

    private fun onRetrieveMilestonesListStart(){
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveMilestonesListFinish(){
        loadingVisibility.value = View.GONE
    }


    private fun onRetrieveMilestonesListSuccess(postList:List<Milestone>){
        milestoneListAdapter.updatePostList(postList)
    }

    private fun onRetrieveMilestonesListError(){
        errorMessage.value= R.string.load_error
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}