package com.example.academypractice.model

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.academypractice.R

class ImageSetter {

    companion object {

        fun setAvatarImage(image: Uri?, imageView: ImageView) {
            val circularProgressDrawable = CircularProgressDrawable(imageView.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            Glide.with(imageView)
                .load(image)
                .circleCrop()
                .placeholder(circularProgressDrawable)
                .into(imageView)
        }


        fun setCharacterThumbnail(image: String, imageView: ImageView, view: View) {
            val circularProgressDrawable = CircularProgressDrawable(view.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            Glide.with(view)
                .load(
                    "https://res.cloudinary.com/dq1i9isdi/image/upload/v1628290762/genshin_thumbs/Character_${
                        spaceRemover(image)
                    }_Thumb.png"
                )
                .centerCrop()
                .placeholder(circularProgressDrawable)
                .into(imageView)
        }

        fun setStars(rarity: Int, imageView: ImageView, view: View) {

            Glide.with(view)
                .load(if (rarity == 4) R.drawable.ic_stars_four else R.drawable.ic_stars_five)
                .into(imageView)

        }

        fun setElement(image: String, imageView: ImageView, view: View) {

            var element = when (image) {
                "Anemo" -> R.drawable.ic_anemo
                "Geo" -> R.drawable.ic_geo
                "Electro" -> R.drawable.ic_electro
                "Dendro" -> R.drawable.ic_dendro
                "Hydro" -> R.drawable.ic_hydro
                "Pyro" -> R.drawable.ic_pyro
                "Cryo" -> R.drawable.ic_cryo
                else -> R.drawable.ic_unknown
            }

            Glide.with(view)
                .load(element)
                .into(imageView)

        }


        fun spaceRemover(rawKeyWord: String): String {
            return if (rawKeyWord.contains(" ", false)) {
                rawKeyWord.replace(" ", "_")
            } else rawKeyWord
        }
    }
}