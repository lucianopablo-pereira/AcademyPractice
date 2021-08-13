package com.example.academypractice.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.academypractice.data.RetrofitInstance
import com.example.academypractice.model.ApiService
import com.example.academypractice.model.Characters

class CharacterDataViewModel {

    private val service: ApiService = RetrofitInstance.getRetrofitInstance()
        .create(ApiService::class.java)
    val pokemonDataList = MutableLiveData<ArrayList<Characters>>(ArrayList())

    fun getCharacter(name: String) {

//        val call = service.getCharacterData(name)
//        call.enqueue(object : Callback<Character> {
//            override fun onResponse(call: Call<Character>, response: Response<Character>) {
//                response.body()?.let { character ->
//
//                    pokemonDataList.value?.add(character)
//                    pokemonDataList.value = ArrayList(
//                        pokemonDataList.value?.sortedBy { pokemon -> pokemon.id })
//                    pokemonDataList.notifyObserver()
//                    Log.d("Pokemon", pokemon.sprites.front_default)
//
//                }
//            }
//
//            override fun onFailure(call: Call<List<String>>, t: Throwable) {
//                call.cancel()
//            }
//
//        })

    }

}