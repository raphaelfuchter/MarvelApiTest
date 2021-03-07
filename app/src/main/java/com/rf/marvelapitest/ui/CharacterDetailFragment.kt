package com.rf.marvelapitest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rf.marvelapitest.R
import com.rf.marvelapitest.models.EndPoints.RESULT_KEY
import com.rf.marvelapitest.models.character.CharactersResult
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_characters_detail.*

class CharacterDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_characters_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val result: CharactersResult? = arguments?.getParcelable(RESULT_KEY)
        loadImages(result)
        setAsViews(result)
    }

    private fun setAsViews(result: CharactersResult?) {
        nameCharacter!!.text = result!!.name
        if (result.description.isNullOrEmpty()) {
            descriptionCharacter!!.visibility = View.GONE
        } else {
            descriptionCharacter!!.visibility = View.VISIBLE
            descriptionCharacter!!.text = result.description
        }
    }

    private fun loadImages(result: CharactersResult?) {
        Picasso.get()
            .load(result!!.thumbnail.getCompletePath())
            .placeholder(R.drawable.thumb)
            .error(R.drawable.thumb)
            .fit()
            .centerInside()
            .into(imageCharacterBackground)
    }

}