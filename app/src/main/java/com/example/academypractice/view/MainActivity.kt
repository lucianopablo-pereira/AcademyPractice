package com.example.academypractice.view

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.ImageCapture
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.academypractice.R
import com.example.academypractice.databinding.ActivityMainBinding
import com.example.academypractice.databinding.CustomDialogBinding
import com.example.academypractice.model.ImageSetter
import com.example.academypractice.settings.Snackbars
import com.example.academypractice.viewmodel.CharacterListViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    private val viewModel: CharacterListViewModel by lazy {
        ViewModelProvider(this).get(
            CharacterListViewModel::class.java
        )
    }

    private val characters: Array<String> by lazy {
        arrayOf("Ganyu", "Diluc", "Jean", "Aether")
    }
    lateinit var toggle: ActionBarDrawerToggle

    private var checkedItem = 0
    private var customDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val img = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                ImageSetter.setAvatarImage(it, binding.mainCharAvatar)
            })

        binding.mainCharAvatar.setOnClickListener {
            img.launch("image/*")
        }
        binding.btnAdd.setOnClickListener { openDialogPersonalized(img) }
        binding.btnProfile.setOnClickListener { openDialogNative() }
        binding.btnClean.setOnClickListener {}

        val toolbar = binding.topAppBar
        setSupportActionBar(toolbar)

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_characters -> startActivity(
                    Intent(this, CharacterListActivity::class.java)
                )
                R.id.nav_settings -> Snackbars.short(binding.root, getString(R.string.nav_settings))
                R.id.nav_share -> Snackbars.short(binding.root, getString(R.string.nav_share))
                R.id.nav_rate -> Snackbars.short(binding.root, getString(R.string.nav_rate))
                R.id.nav_close -> finish()

            }
            true
        }

        initRecycler()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun openDialogNative() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.titles_char_select)
            .setNeutralButton(resources.getString(R.string.option_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.option_accept)) { dialog, which ->

                when (checkedItem) {
                    0 -> {
                        binding.userName.text = characters[0]
                    }
                    1 -> {
                        binding.userName.text = characters[1]
                    }
                    2 -> {
                        binding.userName.text = characters[2]
                    }
                    3 -> {
                        binding.userName.text = characters[3]
                    }
                }

                Snackbar.make(
                    binding.root,
                    "now you are ${characters[checkedItem]}",
                    Snackbar.LENGTH_SHORT
                ).show()

            }
            .setSingleChoiceItems(characters, checkedItem) { dialog, chosenItem ->
                checkedItem = chosenItem
            }
            .setIcon(R.drawable.empty_user)
            .setCancelable(false)
            .show()
    }

    private fun openDialogPersonalized(img: ActivityResultLauncher<String>) {

        val customDialogBinding = CustomDialogBinding.inflate(layoutInflater)


        customDialogBinding.fromStorage.setOnClickListener {
            img.launch("image/*")
            customDialog?.dismiss()
            customDialog = null
        }

        customDialogBinding.fromCamera.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))

        }

        customDialogBinding.btnCancel.setOnClickListener {
            customDialog?.dismiss()
            customDialog = null
        }

//        customDialogBinding.btnAccept.setOnClickListener {
//
//        }

        customDialog = MaterialAlertDialogBuilder(this)
            .setView(customDialogBinding.root)
            .setCancelable(false)
            .show()

    }

    private fun initRecycler() {
        binding.teamRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.teamRecyclerview.adapter = CharacterListAdapter()

        viewModel.getCharacterList()
        viewModel.characterDataList.observe(this, Observer { list ->

            (binding.teamRecyclerview.adapter as CharacterListAdapter)
                .setList(list, 1)

        })

    }

}
