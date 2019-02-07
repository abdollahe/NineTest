package com.boundlesssystems.ninetest.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.boundlesssystems.ninetest.di.OBSERVER_ON
import com.boundlesssystems.ninetest.di.SUBSCRIBER_ON
import com.boundlesssystems.ninetest.model.CellData
import com.boundlesssystems.ninetest.model.api.NewStories
import com.boundlesssystems.ninetest.repository.NewsRepository
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named


/** News Viewmodel class that manages the main activity and feeds the the requested data */

class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository,
                                        @param:Named(SUBSCRIBER_ON) private val subscriberOn: Scheduler,
                                        @param:Named(OBSERVER_ON) private val observerOn: Scheduler): BaseViewModel() {

    // FOR DATA
    // LiveData to hold the data that goes to the recyclerview rows
    private val newsStories: MutableLiveData<NewStories?> = MutableLiveData()
    val newsToShow : LiveData<List<CellData>>
        get() = Transformations.map(newsStories) { newData -> createCellDataList(newData) }

    // LiveData Holding the loading state of the data - (for showing progress on the main screen)
    private val isLoading: MutableLiveData<Boolean?> = MutableLiveData()
    val loadingState : LiveData<Boolean?> = isLoading

    //LiveData to show the error messages that may occur while downloading the data
    private val errorMessage: MutableLiveData<String?> = MutableLiveData()
    val error : LiveData<String?> = errorMessage


    // INIT
    init { this.isLoading.value = false }

    // STREAM

    /**
     * Fetch Stories from the provided API
     *
     */
    fun getStories(){
        this.disposable.addAll(this.newsRepository.getNewsStories()
                .subscribeOn(subscriberOn)
                .observeOn(observerOn)
                .doOnSubscribe { this.isLoading.value = true }
                .doOnComplete { this.isLoading.value = false }
                .doOnError { this.isLoading.value = false }
                .subscribe ({ this.newsStories.value = it }, { this.errorMessage.value = it.message }))
    }


}


 // This function is responsible for extracting the data that is going to be shown in the recyclerview from the
 // data that is downloaded from the network
 fun createCellDataList(newStories : NewStories?) : List<CellData> {

    val myList : MutableList<CellData> = arrayListOf()

     //Loop in the available news items and extract the required data
     for(item in newStories?.assets!!) {
        var iHeight: Int
        var iWidth: Int
        var minImageUrl = ""

         // Check for the smallest image if a news item has different resolution images
        item?.relatedImages?.let {
            iHeight = item.relatedImages[0]?.height!!
            iWidth = item.relatedImages[0]?.width!!
            for(i in item.relatedImages.indices) {
                if( item.relatedImages[i]?.height!! < iHeight  && item.relatedImages[i]?.width!! < iWidth) {
                    iHeight = item.relatedImages[i]?.height!!
                    iWidth = item.relatedImages[i]?.width!!
                    minImageUrl = item.relatedImages[i]?.url!!
                }
            }
        }

         // Add the extracted data to the list to return to the recyclerview
         myList.add(CellData(item?.headline, item?.theAbstract , item?.byLine , minImageUrl , item?.timeStamp , item?.url))
    }

     // News Stories should be sorted and shown based on date and time (using timestamp of the data)
    return  myList.sortedWith(compareBy { it.timeStamp })
}