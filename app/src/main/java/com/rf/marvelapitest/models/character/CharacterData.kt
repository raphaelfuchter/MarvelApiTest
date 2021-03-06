package com.rf.marvelapitest.models.character

import com.google.gson.annotations.Expose

class CharacterData {
    @Expose
    var count: String? = null
    @Expose
    var limit: String? = null
    @Expose
    var offset: String? = null
    @Expose
    var results: List<CharactersResult>? = null
    @Expose
    var total: String? = null
}



