package com.example.academypractice.model


import com.example.academypractice.data.Character
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService{

    @GET("characters")
    fun getCharacterList(): Call<List<String>>

    @GET
    fun getCharacterData(@Url url: String): Call<Character>

    @GET
    fun getCharacterResult(@Url url: String): Call<CharacterResult>

}
