package com.example.academypractice.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CharacterDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCharacter(character: Character)

    @Query("SELECT * FROM character_table WHERE name = :charName AND vision = :vision ")
    fun checkByNameVision(charName: String, vision: String): List<Character>


    @Query("SELECT * FROM character_table ORDER BY id ASC")
    fun getAllCharacters(): LiveData<List<Character>>

}