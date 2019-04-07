package com.bernovia.mylestone.ui.milestonesList

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.bernovia.mylestone.R
import com.bernovia.mylestone.base.BaseViewModel
import com.bernovia.mylestone.network.ApiEndPoint.ACCEPT
import com.bernovia.mylestone.network.ApiEndPoint.CONTENT_TYPE
import com.bernovia.mylestone.network.MilestoneApi
import com.bernovia.mylestone.ui.milestonesList.model.Milestone
import com.bernovia.mylestone.utils.PreferenceManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MilestoneListViewModel : BaseViewModel(), MilestoneListAdapter.DeleteClickListner {

    @Inject
    lateinit var milestoneApi: MilestoneApi
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    private var preferenceManager: PreferenceManager = PreferenceManager.instance

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadMilestones() }
    val milestoneListAdapter: MilestoneListAdapter =
        MilestoneListAdapter()

    private lateinit var subscription: Disposable

    init {
        loadMilestones()
    }


    fun deletelMilestones(id: Int) {

        subscription = milestoneApi.deleteMilestone(
            CONTENT_TYPE, ACCEPT, preferenceManager.accessToken!!
            , preferenceManager.client!!, preferenceManager.tokenType!!
            , preferenceManager.expiry!!
            , preferenceManager.uid!!
            , id
        )

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {},{}
//                { result -> onRetrieveMilestonesListSuccess(result) },
//                { onRetrieveMilestonesListError() }
            )
    }


    fun loadPersonalMilestones() {

        subscription = milestoneApi.getPersonalMilestones(
            CONTENT_TYPE,
            ACCEPT,
            preferenceManager.accessToken!!
            ,
            preferenceManager.client!!,
            preferenceManager.tokenType!!,
            preferenceManager.expiry!!,
            preferenceManager.uid!!
        )

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveMilestonesListStart() }
            .doOnTerminate { onRetrieveMilestonesListFinish() }
            .subscribe(
                { result -> onRetrieveMilestonesListSuccess(result.milestones) },
                { onRetrieveMilestonesListError() }
            )
    }

    fun loadMilestones() {

        subscription = milestoneApi.getMilestones(CONTENT_TYPE, ACCEPT)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveMilestonesListStart() }
            .doOnTerminate { onRetrieveMilestonesListFinish() }
            .subscribe(
                { result -> onRetrieveMilestonesListSuccess(result.milestones) },
                { onRetrieveMilestonesListError() }
            )
    }

    private fun onRetrieveMilestonesListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveMilestonesListFinish() {
        loadingVisibility.value = View.GONE
    }


    private fun onRetrieveMilestonesListSuccess(postList: ArrayList<Milestone>) {
        milestoneListAdapter.updatePostList(postList, this)
    }

    private fun onRetrieveMilestonesListError() {
        errorMessage.value = R.string.load_error
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    override fun deleteOnClick(v: View, id: Int, position: Int) {
        deletelMilestones(id)

        milestoneListAdapter.removeItem(position)
//        milestoneListAdapter.notifyDataSetChanged()
    }

}