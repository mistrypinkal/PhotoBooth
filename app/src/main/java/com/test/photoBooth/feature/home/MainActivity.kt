package com.test.photoBooth.feature.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.external.database.PhotoBooth
import com.test.external.imagePicker.ImagePickerActivity
import com.test.external.imagePicker.constant.ImageConstant
import com.test.photoBooth.R
import com.test.photoBooth.databinding.ActivityMainBinding
import com.test.photoBooth.feature.home.adapter.PhotoBoothAdapter
import com.test.photoBooth.structure.BaseViewModelActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import java.util.*

/**
 * Main view
 * Add Photos
 * List photos
 *
 * @author Pinkal Mistry
 * @version 1.0
 * @since 27 March 2021
 */
class MainActivity : BaseViewModelActivity<ActivityMainBinding, MainViewModel>() {

    companion object {
        private const val CAMERA_IMAGE_REQUEST_CODE = 2001
        private const val GALLERY_IMAGE_REQUEST_CODE = 2002
    }

    lateinit var photoBoothAdapter: PhotoBoothAdapter

    override fun getViewModelClass(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_main
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CAMERA_IMAGE_REQUEST_CODE -> if (resultCode == RESULT_OK) {
                insertImageIntoDB(data?.let { getImagePath(it) })
            }
            GALLERY_IMAGE_REQUEST_CODE -> if (resultCode == RESULT_OK) {
                insertImageIntoDB(data?.let { getImagePath(it) })
            }

            else -> {
            }
        }
    }

    /**
     * Insert data into database
     */
    private fun insertImageIntoDB(imagePath: String?) {
        Log.e("TAG", " Image URI: $imagePath")
        val photoBooth = PhotoBooth("Name", imagePath, getTimeStamp(), getTimeStamp())
        getViewModel().insertPhoto(photoBooth)
        getAllPhotos()
    }

    /**
     * Get time stamnp
     */
    private fun getTimeStamp(): Long {
        val date = Date()
        return date.time
    }

    private fun getImagePath(data: Intent): String? {
        return data.getStringExtra(ImageConstant.EXTRA_FILE_PATH)
    }

    override fun init() {
        setSupportActionBar(findViewById(R.id.toolbar))

        setupView()
        setupViewModel()

    }

    /**
     * Setup View
     */
    private fun setupView() {
        val photoBoothList: List<PhotoBooth> = emptyList()
        photoBoothAdapter = PhotoBoothAdapter(photoBoothList)
        val manager = GridLayoutManager(applicationContext, 3, GridLayoutManager.VERTICAL, false)
        getBinding().rvPhotoBooth.layoutManager = manager
        getBinding().rvPhotoBooth.adapter = photoBoothAdapter

        getBinding().fab.setOnClickListener {
            showImagePickerOptions()
        }
    }

    /**
     * Setup view model
     */
    private fun setupViewModel() {
        getAllPhotos()
    }

    private fun getAllPhotos() {
        getViewModel().getAllPhotoBoothList().observe(this, Observer {
            it?.let {
                if (it.isNotEmpty()) {
                    photoBoothAdapter.setData(it)
                    getBinding().rvPhotoBooth.scrollToPosition(0)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showImagePickerOptions() {

        val pickingOption = arrayOf(
            getStringValue(R.string.camera_picker_option),
            getStringValue(R.string.gallery_picker_option)
        )

        val builder = AlertDialog.Builder(this)
        with(builder) {
            setTitle(getStringValue(R.string.image_picker_dialog_title))
            setItems(pickingOption)
            { _: DialogInterface?, which: Int ->
                when (which) {
                    0 -> openCamera()
                    1 -> openGallery()
                }
            }
            show()
        }

    }

    private fun openGallery() {
        val intent = Intent(applicationContext, ImagePickerActivity::class.java)
        intent.putExtra(ImageConstant.EXTRA_PROVIDER, ImageConstant.Provider.GALLERY)
        startActivityForResult(intent, GALLERY_IMAGE_REQUEST_CODE)
    }

    private fun openCamera() {
        val intent = Intent(applicationContext, ImagePickerActivity::class.java)
        intent.putExtra(ImageConstant.EXTRA_PROVIDER, ImageConstant.Provider.CAMERA)
        startActivityForResult(intent, CAMERA_IMAGE_REQUEST_CODE)
    }
}