package com.rf.marvelapitest.ui.interfaces

import com.rf.marvelapitest.models.character.CharactersResult

interface OnClickDetails {
    fun onclick(character: CharactersResult?)
}