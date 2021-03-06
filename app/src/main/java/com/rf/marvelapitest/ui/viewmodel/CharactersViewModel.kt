package com.rf.marvelapitest.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rf.marvelapitest.models.character.CharactersResponse
import com.rf.marvelapitest.models.character.CharactersResult

import com.rf.marvelapitest.repository.MarvelRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CharactersViewModel(application: Application) : AndroidViewModel(application) {
    private val listResult = MutableLiveData<List<CharactersResult>>()
    private val loading = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()
    private val repository = MarvelRepository()
    var listCharacters: LiveData<List<CharactersResult>> = listResult

    fun loading(): MutableLiveData<Boolean> {
        return loading
    }

    fun getCharactersViewModel() {
        disposable.add(
            repository.charactersResponse()
            !!.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loading.setValue(true) }
                .doOnTerminate { loading.setValue(false) }
                .flatMap { comicsResponse: CharactersResponse ->
                    Observable.just(comicsResponse.data.results)
                }
                .subscribe(listResult::setValue
                ) { throwable: Throwable ->
                    Log.i("LOG", "Error: " + throwable.message)
                }
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}