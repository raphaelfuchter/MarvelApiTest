package com.rf.marvelapitest.network

import com.rf.marvelapitest.models.character.CharactersResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelAPI {

    @GET("characters")
    fun getAllCharacters(
        @Query("limit") limit: Int? = 0,
        @Query("ts") ts: String?,
        @Query("hash") hash: String?,
        @Query("apikey") apikey: String?,
        @Query("offset") offset: Int? = 0
    ): Observable<CharactersResponse?>?

}

