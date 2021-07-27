package com.test.photoBooth.structure

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel


/**
 * Base ViewModel
 *
 * @author Pinkal Mistry
 * @version 1.0
 * @since 27 March 2021
 */

open class BaseViewModel : ViewModel() {

    open fun onStart() {
    }

    /**
     * This is a scope for all co-routines launched by [BaseViewModel]
     * that will be dispatched in a Pool of Thread
     */
    protected val ioScope = CoroutineScope(Dispatchers.Default)

    /**
     * Cancel all co-routines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        ioScope.coroutineContext.cancel()
    }
}