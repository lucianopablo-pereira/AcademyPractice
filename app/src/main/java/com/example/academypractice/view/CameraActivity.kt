package com.example.academypractice.view

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.academypractice.R
import com.example.academypractice.databinding.ActivityCameraBinding
import com.example.academypractice.settings.Constants
import com.example.academypractice.settings.Snackbars
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCapture.setOnClickListener { photoCapture() }

        outputDirectory = getOutputDirectory()

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, Constants.REQUIRED_PERMISSIONS, Constants.REQUEST_CODE_PERMISSIONS
            )
        }

    }

    private fun getOutputDirectory(): File {
        val mediaSrc = externalMediaDirs.firstOrNull()?.let { mFile ->
            File(mFile, resources.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }

        return if (mediaSrc != null && mediaSrc.exists()) mediaSrc else filesDir
    }

    private fun photoCapture() {

        val image = imageCapture ?: return
        val file = File(
            outputDirectory,
            SimpleDateFormat(
                Constants.FILE_NAME_FORMAT, Locale.getDefault()
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        val outputOption = ImageCapture.OutputFileOptions.Builder(file).build()

        image.takePicture(outputOption, ContextCompat.getMainExecutor(this), object :ImageCapture.OnImageSavedCallback{
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {

                val savedUri = Uri.fromFile(file)
                Snackbars.indefinite(binding.root, savedUri.toString())

            }

            override fun onError(exception: ImageCaptureException) {
                Snackbars.short(binding.root, "Couldn't save Photo")
            }
        })


    }

    private fun startCamera() {

        val cameraProviderInstance = ProcessCameraProvider.getInstance(this)

        cameraProviderInstance.addListener({
            val cameraProvider = cameraProviderInstance.get()
            val preview = Preview.Builder()
                .build()
                .also { mPreview ->
                    mPreview.setSurfaceProvider(
                        binding.viewFinder.surfaceProvider
                    )
                }

            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )

            } catch (e: Exception) {
                Snackbars.short(binding.root, "Camera Failed")
            }

        }, ContextCompat.getMainExecutor(this))


    }

    private fun allPermissionsGranted() = Constants.REQUIRED_PERMISSIONS.all {

        Constants.REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                baseContext, it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == Constants.REQUEST_CODE_PERMISSIONS) {

            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Snackbars.short(binding.root, "Permissions not granted by the user.")
                finish()
            }
        }

    }


}