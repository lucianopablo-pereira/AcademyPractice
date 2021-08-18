package com.example.academypractice.model

import com.google.gson.annotations.SerializedName

data class CharacterResult(

    @SerializedName("name") val name: String,
    @SerializedName("vision") val vision: String,
    @SerializedName("weapon") val weapon: String,
    @SerializedName("rarity") val rarity: Int

)