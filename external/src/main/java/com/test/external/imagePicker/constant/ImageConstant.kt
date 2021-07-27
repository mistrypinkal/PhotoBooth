package com.test.external.imagePicker.constant

/**
 * Image Constant
 *
 * @author Pinkal Mistry
 * @version 1.0
 * @since 27 March 2021
 */
interface ImageConstant {

    companion object{
        const val EXTRA_ERROR = "extra.error"
        const val EXTRA_FILE_PATH = "extra.file_path"
        const val EXTRA_PROVIDER = "extra.provider"

        internal const val FILE_PROVIDER=".fileprovider"
    }

    enum class Provider {
        CAMERA,
        GALLERY
    }
}