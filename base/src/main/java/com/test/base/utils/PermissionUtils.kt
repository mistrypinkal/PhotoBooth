package com.test.base.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

/**
 * Permission Utils
 *
 * @author Pinkal Mistry
 * @version 1.0
 * @since 27 March 2021
 */
object PermissionUtils {

    /**
     * Check if Specified Permissions are granted or not. If single permission is denied then
     * function will return false.
     *
     * @param context Application Context
     * @param permissions Array of Permission to Check
     *
     * @return true if all specified permission is granted
     */
    fun isPermissionGranted(context: Context, permissions: Array<String>): Boolean {
        return permissions.filter {
            checkPermission(context, it)
        }.size == permissions.size
    }

    /**
     * Check if Camera Permission is granted
     *
     * @return true if specified permission is granted
     */
    private fun checkPermission(context: Context, permission: String): Boolean {
        val selfPermission = ContextCompat.checkSelfPermission(context, permission)
        return selfPermission == PackageManager.PERMISSION_GRANTED
    }

    fun checkSDKVersionForPermission(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

}