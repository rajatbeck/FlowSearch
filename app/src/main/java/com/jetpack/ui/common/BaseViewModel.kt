package com.jetpack.ui.common

import androidx.lifecycle.ViewModel
import com.jetpack.usecase.errors.ErrorManager
import javax.inject.Inject

abstract class BaseViewModel:ViewModel() {

    @Inject
    lateinit var errorManager: ErrorManager
}