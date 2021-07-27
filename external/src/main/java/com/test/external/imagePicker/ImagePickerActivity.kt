package com.test.external.imagePicker

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.test.base.utils.PermissionUtils
import com.test.external.BuildConfig
import com.test.external.imagePicker.constant.ImageConstant
import java.io.File
import java.io.IOException

/**
 * Image Picker class
 *
 * @author Pinkal Mistry
 * @version 1.0
 * @since 27 March 2021
 */
class ImagePickerActivity : AppCompatActivity() {


    companion object {

        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_GALLERY_IMAGE = 2

        private const val CAMERA_PERMISSION_CODE = 1001
        private const val GALLERY_PERMISSION_CODE = 1002

        /**
         * Permission Require for Image Capture using Camera
         */
        private val REQUIRED_PERMISSION_CAMERA = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )

        /**
         * Permission Require for Image from Gallery
         */
        private val REQUIRED_PERMISSION_GALLERY = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

    }

    private var capturedImagePath: String? = null

    private var provider: ImageConstant.Provider? = null
    private var isCameraPermissionRequired: Boolean = true


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> if (resultCode == RESULT_OK) {
                capturedImagePath?.let { setResultSuccess(it) }

            }
            REQUEST_GALLERY_IMAGE -> if (resultCode == RESULT_OK) {
                data.let {
                    if (it != null) {
                        getRealImagePath(it.data)?.let { it1 -> setResultSuccess(it1) }
                    }
                }
            }
            else {
                setError("")
            }
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_PERMISSION_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                takeCameraImage() else setError("Permission denied")

            GALLERY_PERMISSION_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                chooseImageFromGallery() else setError("Permission denied")
            else -> {
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        provider =
            intent.getSerializableExtra(ImageConstant.EXTRA_PROVIDER) as? ImageConstant.Provider
        isCameraPermissionRequired = PermissionUtils.checkSDKVersionForPermission()

        assignProvider()
    }

    private fun assignProvider() {
        when (provider) {
            ImageConstant.Provider.CAMERA -> {
                checkForCameraPermission()
            }
            ImageConstant.Provider.GALLERY -> {
                checkForGalleryPermission()
            }
        }
    }

    private fun checkForCameraPermission() {
        if (isPermissionGranted(this, REQUIRED_PERMISSION_CAMERA)) {
            takeCameraImage()
        } else {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSION_CAMERA, CAMERA_PERMISSION_CODE
            )
        }
    }

    private fun checkForGalleryPermission() {
        if (isPermissionGranted(this, REQUIRED_PERMISSION_GALLERY)) {
            chooseImageFromGallery()
        } else {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSION_GALLERY, GALLERY_PERMISSION_CODE
            )
        }
    }

    /** @param context Application Context
     * @return boolean true if all required permission granted else false.
     */
    private fun isPermissionGranted(context: Context, permissionArray: Array<String>): Boolean {
        return if (isCameraPermissionRequired) {
            (PermissionUtils.isPermissionGranted(
                this,
                permissionArray
            ))
        } else true
    }

    private fun takeCameraImage() {
        var photoFile: File? = null
        try {
            photoFile = createImageFile()
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

        if (photoFile != null) {
            var takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val authority = BuildConfig.LIBRARY_PACKAGE_NAME + ImageConstant.FILE_PROVIDER
                val photoURI =
                    FileProvider.getUriForFile(applicationContext, authority, photoFile)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            } else {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile))
            }
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)

        } else {
            setError("")
        }
    }


    private fun chooseImageFromGallery() {
        val pickPhoto = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(pickPhoto, REQUEST_GALLERY_IMAGE)
    }


    private fun createImageFile(): File? {
        val imageFileName: String = System.currentTimeMillis().toString()
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(imageFileName, ".jpeg", storageDir)
        capturedImagePath = image.absolutePath
        return image
    }

    private fun getRealImagePath(imageUri: Uri?): String? {
        val cursor: Cursor? = imageUri?.let { contentResolver.query(it, null, null, null, null) }
        if (cursor != null) {
            cursor.moveToFirst()
            val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            val path = cursor.getString(index)
            cursor.close()
            return path
        }
        return imageUri?.path
    }

    /**
     * Return image capture or from gallery path
     *
     * @param imagePath path of storage device
     */
    private fun setResultSuccess(imagePath: String) {
        val intent = Intent()
        intent.putExtra(ImageConstant.EXTRA_FILE_PATH, imagePath)
        setResult(RESULT_OK, intent)
        finish()
    }

    /**
     * Error occurred while processing image
     *
     * @param message Error Message
     */
    private fun setError(message: String) {
        val intent = Intent()
        intent.putExtra(ImageConstant.EXTRA_ERROR, message)
        setResult(RESULT_CANCELED, intent)
        finish()
    }

}