package org.deafsapps.android.mobilityfinder.base

import android.app.Application
import org.deafsapps.android.mobilityfinder.di.ApplicationGraph
import org.deafsapps.android.mobilityfinder.di.DaggerApplicationGraph
import org.deafsapps.android.mobilityfinder.di.UtilsModule
import org.deafsapps.android.mobilityfinder.presentationlayer.di.PresentationlayerComponent
import org.deafsapps.android.mobilityfinder.presentationlayer.di.PresentationlayerComponentProvider

/**
 * This class implements an [Application] subclass instance which serves as entry point to the app.
 * General tool configurations such as 'LeakCanary' for memory leaks, and 'Dagger' for dependency
 * inversion are initialized here.
 *
 * @author Pablo L. Sordo Martínez
 * @since 1.0
 */
class BaseApplication : Application(), PresentationlayerComponentProvider {

    private lateinit var appComponent: ApplicationGraph

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationGraph.builder()
            .utilsModule(UtilsModule(this))
            .build()
    }

    override fun providePresentationlayerComponent(): PresentationlayerComponent =
        appComponent.presentationlayerComponent().create()

}