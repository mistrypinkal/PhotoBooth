package com.test.photoBooth.structure

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Base AppCompatActivity
 * Whatever the common fun related info will be declare here
 * That can access everywhere
 *
 * @author Pinkal Mistry
 * @version 1.0
 * @since 27 March 2021
 */
open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun getStringValue(id: Int?): String? {
        return id?.let { applicationContext.resources.getString(it) }
    }

}