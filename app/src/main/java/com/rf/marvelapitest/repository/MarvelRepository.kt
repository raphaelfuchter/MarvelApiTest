package com.rf.marvelapitest.repository

import com.rf.marvelapitest.models.MarvelEndPoints.PUBLIC_API_KEY
import com.rf.marvelapitest.extensions.getTimeStamp
import com.rf.marvelapitest.network.RetrofitService.Companion.getApiService
import com.rf.marvelapitest.models.character.CharactersResponse
import com.rf.marvelapitest.network.RetrofitService.Companion.getHash
import io.reactivex.Observable

class MarvelRepository {

    fun charactersResponse(): Observable<CharactersResponse?>? {
        return getApiService().getAllCharacters(100, getTimeStamp(), getHash(), PUBLIC_API_KEY, 0)
    }

}