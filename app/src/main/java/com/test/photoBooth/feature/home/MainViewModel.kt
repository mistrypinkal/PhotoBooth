package com.test.photoBooth.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.external.database.PhotoBooth
import com.test.photoBooth.structure.BaseViewModel
import com.test.repository.PhotoBoothRepo
import kotlinx.coroutines.launch

class MainViewModel(private var photoBoothRepo: PhotoBoothRepo) : BaseViewModel() {

    private lateinit var photoListLD : MutableLiveData<List<PhotoBooth>>

    fun insertPhoto(photoBooth: PhotoBooth) {
        ioScope.launch { photoBoothRepo.insertDataAsync(photoBooth) }
    }

    fun getAllPhotoBoothList(): LiveData<List<PhotoBooth>> {
        photoListLD= MutableLiveData()
        ioScope.launch { photoListLD.postValue(photoBoothRepo.getAllPhotoBoothData()) }
        return photoListLD
    }

}