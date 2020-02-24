package org.deafsapps.android.mobilityfinder.base

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import org.deafsapps.android.mobilityfinder.datalayer.di.DaggerDatalayerComponent
import org.deafsapps.android.mobilityfinder.di.ApplicationGraph
import org.deafsapps.android.mobilityfinder.di.DaggerApplicationGraph
import org.deafsapps.android.mobilityfinder.domainlayer.di.DaggerDomainlayerComponent
import org.deafsapps.android.mobilityfinder.presentationlayer.di.DiProvider
import org.deafsapps.android.mobilityfinder.presentationlayer.di.DaggerPresentationlayerComponent
import org.deafsapps.android.mobilityfinder.presentationlayer.di.PresentationlayerComponent
import org.deafsapps.android.mobilityfinder.presentationlayer.di.PresentationlayerComponentProvider
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.main.view.ui.MainActivity

/**
 * This class implements an [Application] subclass instance which serves as entry point to the app.
 * General tool configurations such as 'LeakCanary' for memory leaks, and 'Dagger' for dependency
 * inversion are initialized here.
 *
 * @author Pablo L. Sordo Martínez
 * @since 1.0
 */
class BaseApplication : Application(), PresentationlayerComponentProvider {
//    PresentationlayerComponentProvider, DomainlayerComponentProvider, DatalayerComponentProvider {

    private lateinit var appComponent: ApplicationGraph
    val p = DaggerPresentationlayerComponent.factory().create()

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationGraph.factory()
            .create(
                applicationContext = this,
                presentationlayerComponent = p,
                domainlayerComponent = DaggerDomainlayerComponent.factory().create(),
                datalayerComponent = DaggerDatalayerComponent.factory().create()
            )
    }


    override fun providePresentationlayerComponent(): PresentationlayerComponent = p
    /*
    override fun provideDomainlayerComponent(): DomainlayerComponent =
        appComponent.domainlayerComponent().create()

    override fun provideDatalayerComponent(): DatalayerComponent =
        appComponent.datalayerComponent().create()
    */

}