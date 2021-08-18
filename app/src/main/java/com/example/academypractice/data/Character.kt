package com.example.academypractice.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "character_table")
data class Character(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("vision") val vision: String,
    @SerializedName("weapon") val weapon: String,
    @SerializedName("rarity") val rarity: Int
)