package com.example.academypractice.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.academypractice.model.Characters
import com.example.academypractice.model.ImageSetter
import kotlinx.android.synthetic.main.grid_item_character.view.*

class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    fun bind(character: Characters){
        itemView.char_name.text = character.name
        ImageSetter.setCharacterThumbnail(
            character.name,
            itemView.thumbnail,
            itemView
        )
        ImageSetter.setStars(character.rarity, itemView.stars_type, itemView)
        ImageSetter.setElement(character.vision, itemView.element_type, itemView)
    }

}
