package com.example.academypractice.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academypractice.databinding.CustomDialogBinding
import com.example.academypractice.databinding.FragmentProfileBinding
import com.example.academypractice.model.ImageSetter
import com.example.academypractice.view.CharacterListAdapter
import com.example.academypractice.view.activities.CameraActivity
import com.example.academypractice.viewmodel.CharacterViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    private var customDialog: AlertDialog? = null
    private val viewModel: CharacterViewModel by lazy {
        ViewModelProvider(this).get(
            CharacterViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val img = registerForActivityResult(
//            ActivityResultContracts.GetContent(),
//            ActivityResultCallback {
//                ImageSetter.setAvatarImage(it, binding.mainCharAvatar)
//            })

        initRecycler()



    }

    private fun openDialogPersonalized(img: ActivityResultLauncher<String>) {

        val customDialogBinding = CustomDialogBinding.inflate(layoutInflater)


        customDialogBinding.fromStorage.setOnClickListener {
            img.launch("image/*")
            customDialog?.dismiss()
            customDialog = null
        }

        customDialogBinding.fromCamera.setOnClickListener {
            startActivity(Intent(activity, CameraActivity::class.java))

        }

        customDialogBinding.btnCancel.setOnClickListener {
            customDialog?.dismiss()
            customDialog = null
        }

//        customDialogBinding.btnAccept.setOnClickListener {
//
//        }

        customDialog = MaterialAlertDialogBuilder(requireContext())
            .setView(customDialogBinding.root)
            .setCancelable(false)
            .show()

    }

    private fun initRecycler() {
        binding.teamRecyclerview.layoutManager = LinearLayoutManager(activity)
        binding.teamRecyclerview.adapter = CharacterListAdapter()

        viewModel.getCharacterList()
        viewModel.getCharacter().observe(requireActivity(), Observer { list ->

            (binding.teamRecyclerview.adapter as CharacterListAdapter)
                .setList(list, 1)

        })

    }

}