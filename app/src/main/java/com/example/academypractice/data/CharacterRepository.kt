package com.example.academypractice.data

import androidx.lifecycle.LiveData

class CharacterRepository(private val characterDAO: CharacterDAO) {

    val readAllData: LiveData<List<Character>> = characterDAO.getAllCharacters()

    suspend fun addCharacter(character: Character){
        characterDAO.addCharacter(character)
    }

    suspend fun getCharacter(character: Character){
        characterDAO.addCharacter(character)
    }

    fun checkByNameVision(name: String, vision: String): List<Character>{
        return characterDAO.checkByNameVision(name, vision)
    }

}