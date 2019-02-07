package com.boundlesssystems.ninetest

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.annotation.Nullable
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


/** This helper class is used to view the LiveData in a viewmodel when testing. In test mode the LiveData variables cannot be accessed
 * directly thus we need this helper method to be able to access and test their values correctly **/
class LiveDataTestUtil {
    companion object {
        @Throws(InterruptedException::class)
        fun <T> getValue(liveData: LiveData<T>): T {
            val data = arrayOfNulls<Any>(1)
            val latch = CountDownLatch(1)
            val observer = object : Observer<T> {
                override fun onChanged(@Nullable o: T) {
                    data[0] = o
                    latch.countDown()
                    liveData.removeObserver(this)
                }
            }
            liveData.observeForever(observer)
            latch.await(2, TimeUnit.SECONDS)

            return data[0] as T
        }
    }

}