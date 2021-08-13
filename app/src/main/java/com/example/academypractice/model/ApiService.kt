package com.example.academypractice.model


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService{

    @GET("characters")
    fun getCharacterList(): Call<List<String>>

    @GET
    fun getCharacterData(@Url url: String): Call<Characters>

//    @GET("characters")
//    fun getCharacterList(): Call<List<Character>>



}
