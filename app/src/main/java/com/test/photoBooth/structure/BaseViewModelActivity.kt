package com.test.photoBooth.structure

import android.os.Bundle
import android.os.PersistableBundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.test.photoBooth.BR
import org.koin.androidx.viewmodel.compat.ViewModelCompat.getViewModel

/**
 * Base ViewModel activity
 * It accepts the ViewDataBinding and the BaseViewModel as Viewmodel comman for all classes
 * This will bind views and the init viewModel at one place
 *
 * @author Pinkal Mistry
 * @version 1.0
 * @since 27 March 2021
 */
abstract class BaseViewModelActivity<DB : ViewDataBinding, VM : BaseViewModel> : BaseActivity() {

    private lateinit var binding: DB
    private lateinit var viewModel: VM

    /**
     * abstract method for assign ViewModel
     */
    protected abstract fun getViewModelClass(): Class<VM>

    /**
     * abstract method for assign xml layout
     */
    protected abstract fun getLayoutResource(): Int

    /**
     * Init method called once the ViewModel and the DataBinding init done
     */
    open fun init() {}

    /**
     * Return assigned ViewDataBinding
     */
    fun getBinding(): DB {
        return binding
    }

    /**
     * Return assigned ViewModel
     */
    fun getViewModel(): VM {
        return viewModel
    }

    /**
     * Init DataBinding
     */
    private fun initDataBinding(): DB {
        return DataBindingUtil.setContentView(this, getLayoutResource())
    }

    /**
     * Init viewModel
     */
    private fun initViewModel(): VM {
        //return ViewModelProvider(this).get(getViewModelClass())
        return getViewModel(this, getViewModelClass())
    }

    private fun initState(savedInstanceState: Bundle?) {}

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setup(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup(savedInstanceState)
    }

    /**
     * Setup the ViewModel And the ViewDataBinding
     * And then call the init method that can access in the
     */
    private fun setup(savedInstanceState: Bundle?) {
        viewModel = initViewModel()
        binding = initDataBinding()
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings();
        binding.lifecycleOwner = this
        initState(savedInstanceState)
        init()
        viewModel.onStart()
    }





}