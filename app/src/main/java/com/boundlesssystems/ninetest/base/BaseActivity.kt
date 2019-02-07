package com.boundlesssystems.ninetest.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.boundlesssystems.ninetest.viewmodel.ViewModelFactory
import com.squareup.picasso.Picasso

import javax.inject.Inject

/** This class is the base class that the main activity is derived from. The dependencies of the main activity are injected in to this base class (This will help to
 * inject some common objects between views (maybe such as fragments) only once) */

abstract class BaseActivity: AppCompatActivity() {

    // FOR DATA
    @Inject lateinit var viewModelFactory: ViewModelFactory

    @Inject lateinit var picasso : Picasso

    // --- LIFECYCLE METHODS ---
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutById())
        this.configureDagger()
        this.configureDesign()
    }

    // --- DEPENDENCIES INJECTION ---
    private fun configureDagger() = (this.application as BaseApplication).appComponent.inject(this)

    // --- ABSTRACT METHODS ---
    // Methods to configure the view that derives this base class
    abstract fun getLayoutById(): Int
    abstract fun configureDesign()
}