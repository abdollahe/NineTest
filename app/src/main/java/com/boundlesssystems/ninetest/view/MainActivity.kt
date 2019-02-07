package com.boundlesssystems.ninetest.view

import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.boundlesssystems.ninetest.R
import com.boundlesssystems.ninetest.base.BaseActivity
import com.boundlesssystems.ninetest.model.CellData
import com.boundlesssystems.ninetest.view.recyclerview.RecyclerItemClickListener
import com.boundlesssystems.ninetest.view.recyclerview.RecyclerViewAdapter

import com.boundlesssystems.ninetest.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.activity_main.*


/** The application follows Android Jetpack Architectural pattern, thus it uses Google ViewModel and LiveData to manage
 * business logic and the update of the ui respectively. It also **/


class MainActivity : BaseActivity() , RecyclerItemClickListener.OnRecyclerClickListener {

    // FOR DATA
    private lateinit var viewModel: NewsViewModel
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    // OVERRIDING
    override fun getLayoutById(): Int = R.layout.activity_main

    override fun configureDesign() {
        this.configureRefreshLayout()
        this.configureViewModel()
        this.configureRecyclerView()
        this.observeData()
        this.fetchNewsStoriesFromApi()
    }

    // Function to configure the recyclerview - is called once during the lifecycle in onCreate
    private fun configureRecyclerView() {
        recyclerViewAdapter = RecyclerViewAdapter(picasso)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = recyclerViewAdapter
        recycler_view.addOnItemTouchListener(RecyclerItemClickListener(this , recycler_view , this))

    }

    //Function to setup the viewModel of thr application
    private fun configureViewModel(){
        this.viewModel = ViewModelProviders.of(this, viewModelFactory)[NewsViewModel::class.java]
    }

    //Function for setting up the observers of the LiveData
    private fun observeData(){
        //Observe the news data to show on the recyclerview
        viewModel.newsToShow.observe(this, Observer {
            updateUI(it)
        })
        //Observe the state of the data being loaded - to control the refresh loading circle of the swipeRefreshLayout
        viewModel.loadingState
            .observe(this, Observer { it?.let { state -> this.updateRefreshLayout(state) } })
        //Observe error message to log (or show to the user) if an error occurs while downloading the data
        viewModel.error
            .observe(this, Observer { it?.let { msg -> Log.e("TAG", "Throw an error : $msg") } })

    }

    //Function to configure the swipeRefreshLayout
    private fun configureRefreshLayout(){
        swipeRefresh.setOnRefreshListener { this.fetchNewsStoriesFromApi() }
    }

    // Function to request the backend to download the news stories from the view model
    private fun fetchNewsStoriesFromApi(){
        this.viewModel.getStories()
    }

    // Main function to update the UI (Recyclerview) when the data is ready
    private fun updateUI(cellDataList : List<CellData>){
        recyclerViewAdapter.loadCellDataList(cellDataList)

    }


    //Listener function for recyclerview row items
    override fun onRowItemClicked(url: String) {
        val intent = Intent(this , WebViewActivity::class.java)
        intent.putExtra("url" , url)
        startActivity(intent)
    }


    // function to toggle the state of the swipeRefreshLayout based on the loading state of the data
    private fun updateRefreshLayout(isRefreshing: Boolean){
        swipeRefresh.isRefreshing = isRefreshing
    }
}

