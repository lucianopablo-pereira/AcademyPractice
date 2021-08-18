package com.example.academypractice.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.academypractice.data.Character
import com.example.academypractice.data.CharacterDatabase
import com.example.academypractice.data.CharacterRepository
import com.example.academypractice.data.RetrofitInstance
import com.example.academypractice.model.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class CharacterViewModel(application: Application) : AndroidViewModel(application) {

    private val readAllData: LiveData<List<Character>>
    private val repository: CharacterRepository
    val service: ApiService =
        RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)

    init {
        val characterDAO = CharacterDatabase.getDatabase(application).characterDAO()
        repository = CharacterRepository(characterDAO)
        readAllData = repository.readAllData
    }

    fun getCharacter(): LiveData<List<Character>>{
        return repository.readAllData
    }

    fun addCharacter(name: String) {

        val call = service.getCharacterData(name)
        call.enqueue(object : Callback<Character> {
            override fun onResponse(call: Call<Character>, response: Response<Character>) {
                response.body()?.let { character ->

                    viewModelScope.launch(Dispatchers.IO) {
                        if(repository.checkByNameVision(character.name,character.vision).isEmpty()){
                            repository.addCharacter(character)
                        }
                    }

                }
            }

            override fun onFailure(call: Call<Character>, t: Throwable) {
                call.cancel()
                Toast.makeText(getApplication(), "Offline List", Toast.LENGTH_SHORT)
            }
        })


    }

    fun getCharacterList() {
        val call = service.getCharacterList()
        call.enqueue(object : Callback<List<String>> {
            override fun onResponse(
                call: Call<List<String>>,
                response: Response<List<String>>
            ) {
                response.body()?.let { list ->
                    for (item in list) {
                        addCharacter(RetrofitInstance.baseUrl + "characters/" + item)
                    }
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                call.cancel()


                Toast.makeText(getApplication(), "Offline List", Toast.LENGTH_SHORT)
            }

        })
    }

    fun <T> MutableLiveData<T>.notifyObserver() {
        postValue(value)
    }

}