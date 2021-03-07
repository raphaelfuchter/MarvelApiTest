package com.rf.marvelapitest.ui.interfaces

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val _failure: MutableLiveData<Failure?> = MutableLiveData()
    val failure: LiveData<Failure?> get() = _failure

    fun handleError(failure: Failure?) {
        _failure.postValue(failure)
       //_failure.postValue(null)
    }
}
