package com.example.academypractice.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.academypractice.data.Character
import com.example.academypractice.model.CharacterResult
import com.example.academypractice.model.ImageSetter
import kotlinx.android.synthetic.main.grid_item_character.view.*

class LinearViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    fun bind(character: Character){
        itemView.char_name.text = character.name
        itemView.char_weapon.text = character.weapon
        ImageSetter.setCharacterThumbnail(
            character.name,
            itemView.thumbnail,
            itemView
        )
        ImageSetter.setStars(character.rarity, itemView.stars_type, itemView)
        ImageSetter.setElement(character.vision, itemView.element_type, itemView)
    }

}
