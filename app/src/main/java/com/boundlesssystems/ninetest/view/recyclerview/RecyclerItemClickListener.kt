package com.boundlesssystems.ninetest.view.recyclerview

import android.content.Context
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent


/** class to implement the different gesture and touch controls available to the recyclerview*/

class RecyclerItemClickListener(context: Context, recyclerView: RecyclerView, private val listener: OnRecyclerClickListener) : RecyclerView.SimpleOnItemTouchListener() {

    // Interface that the view containing  the recyclerview needs to implement
    interface OnRecyclerClickListener {
        fun onRowItemClicked(url:String)
    }


    // Implementation of the gesture control to detect tapping on the rows of the recyclerview
    private val gestureDetector = GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener() {

        // function to detect normal clicks on the rows of the RecyclerView
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            val childView = recyclerView.findChildViewUnder(e.x, e.y)
            val position = recyclerView.getChildAdapterPosition(childView!!)
            val url = (recyclerView.adapter as RecyclerViewAdapter).getItemSelected(position)?.newsUrl


            url?.let { listener.onRowItemClicked(url) }


            return super.onSingleTapUp(e)
        }

    })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(e)
    }



}