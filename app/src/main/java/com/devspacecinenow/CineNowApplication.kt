package com.devspacecinenow

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CineNowApplication : Application()
/*    val repository by lazy {
        CineNowServiceLocator.getRepository(application = this)
    }*/
