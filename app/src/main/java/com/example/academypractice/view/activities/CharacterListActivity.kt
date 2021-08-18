package com.example.academypractice.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.academypractice.databinding.ActivityCharacterListBinding
import com.example.academypractice.view.CharacterListAdapter
import com.example.academypractice.viewmodel.CharacterViewModel

class CharacterListActivity : AppCompatActivity() {

    lateinit var binding: ActivityCharacterListBinding
    private val viewModel: CharacterViewModel by lazy {
        ViewModelProvider(this).get(
            CharacterViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecycler()
    }

    fun initRecycler() {
        binding.teamRecyclerview.layoutManager = GridLayoutManager(this, 3)
        binding.teamRecyclerview.adapter = CharacterListAdapter()

        viewModel.getCharacterList()
        viewModel.getCharacter().observe(this, Observer { list ->

            (binding.teamRecyclerview.adapter as CharacterListAdapter)
                .setList(list, 0)

        })

    }

}