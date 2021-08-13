package com.example.academypractice.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.academypractice.R
import com.example.academypractice.model.Characters

class CharacterListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var viewType: Int = 0
    lateinit var characterList: ArrayList<Characters>

    fun setList(list: ArrayList<Characters>, viewType: Int) {

        this.viewType = viewType
        characterList = list
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == 0) {
            return GridViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.grid_item_character, parent, false))
        }

        return LinearViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.linear_item_character, parent, false))

    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (viewType == 0){
            (holder as GridViewHolder).bind(characterList[position])
        } else {
            (holder as LinearViewHolder).bind(characterList[position])
        }

    }

    override fun getItemCount(): Int {
        return characterList.size
    }

}