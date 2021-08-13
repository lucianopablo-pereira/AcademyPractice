package com.example.academypractice.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.academypractice.data.RetrofitInstance
import com.example.academypractice.model.ApiService
import com.example.academypractice.model.Characters
import retrofit2.*
import java.util.*
import kotlin.collections.ArrayList

class CharacterListViewModel() : ViewModel() {

    val service: ApiService =
        RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)
    val characterDataList = MutableLiveData<ArrayList<Characters>>(ArrayList())

    fun getCharacterList() {
        val call = service.getCharacterList()
        call.enqueue(object : Callback<List<String>> {
            override fun onResponse(
                call: Call<List<String>>,
                response: Response<List<String>>
            ) {
                response.body()?.let { list ->
                    for (item in list) {
                        getCharacter(RetrofitInstance.baseUrl + "characters/" + item)

                    }
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                call.cancel()
            }

        })
    }

    fun getCharacter(name: String) {

        val call = service.getCharacterData(name)
        call.enqueue(object : Callback<Characters> {
            override fun onResponse(call: Call<Characters>, response: Response<Characters>) {
                response.body()?.let { character ->
                    characterDataList.value?.add(character)
                    characterDataList.notifyObserver()

                }
            }

            override fun onFailure(call: Call<Characters>, t: Throwable) {
                call.cancel()
            }
        })

    }

    fun <T> MutableLiveData<T>.notifyObserver() {
        postValue(value)
    }

}