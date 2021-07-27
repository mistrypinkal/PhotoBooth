package com.test.repository

import com.test.external.database.PhotoBooth
import com.test.external.database.PhotoBoothDAO

class PhotoBoothRepo(private val photoBoothDAO: PhotoBoothDAO) {
    suspend fun insertDataAsync(photoBooth: PhotoBooth) =
        photoBoothDAO.insertPhotoBoothData(photoBooth)

    suspend fun getAllPhotoBoothData() = photoBoothDAO.getAllPhotoBoothData()
}