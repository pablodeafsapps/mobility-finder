package org.deafsapps.android.mobilityfinder.di

import dagger.Component
import org.deafsapps.android.mobilityfinder.datalayer.di.DatalayerAbstractModule
import org.deafsapps.android.mobilityfinder.datalayer.di.DatalayerModule
import org.deafsapps.android.mobilityfinder.domainlayer.di.DomainlayerAbstractModule
import org.deafsapps.android.mobilityfinder.presentationlayer.di.PresentationlayerComponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [UtilsModule::class, SubcomponentsModule::class,
        DomainlayerAbstractModule::class, DatalayerModule::class, DatalayerAbstractModule::class]
)
interface ApplicationGraph {

    fun presentationlayerComponent(): PresentationlayerComponent.Factory

}