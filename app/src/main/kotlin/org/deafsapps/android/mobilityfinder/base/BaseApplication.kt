package org.deafsapps.android.mobilityfinder.base

import android.app.Application

/**
 * This class implements an [Application] subclass instance which serves as entry point to the app.
 * General tool configurations such as 'LeakCanary' for memory leaks, and 'Dagger' for dependency
 * inversion are initialized here.
 *
 * @author Pablo L. Sordo Martínez
 * @since 1.0
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}