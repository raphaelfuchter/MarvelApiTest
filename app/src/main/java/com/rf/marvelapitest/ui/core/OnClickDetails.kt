package com.rf.marvelapitest.ui.core

import com.rf.marvelapitest.models.character.CharactersResult

interface OnClickDetails {
    fun onclick(character: CharactersResult?)
}