package com.rf.marvelapitest.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rf.marvelapitest.R
import com.rf.marvelapitest.models.character.CharactersResult
import com.rf.marvelapitest.ui.interfaces.OnClickDetails
import com.squareup.picasso.Picasso

class CharactersAdapter(private var listresult: List<CharactersResult>, private val listener: OnClickDetails)
    : RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resultlist = listresult[position]
        holder.onBind(resultlist)
        holder.itemView.setOnClickListener { listener.onclick(resultlist) }
    }

    override fun getItemCount(): Int {
        return listresult.size
    }

    fun updateList(resultList: List<CharactersResult>) {
        listresult = resultList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView
        private val textViewName: TextView

        init {
            itemView.apply {
                imageView = findViewById(R.id.imageCharacter)
                textViewName = findViewById(R.id.nameCharacter)
            }

        }

        fun onBind(result: CharactersResult) {
            Picasso.get()
                    .load(result.thumbnail.getCompletePath())
                    .placeholder(R.drawable.thumb)
                    .error(R.drawable.thumb)
                    .fit()
                    .centerInside()
                    .into(imageView)
            textViewName.text = result.name
        }

    }
}