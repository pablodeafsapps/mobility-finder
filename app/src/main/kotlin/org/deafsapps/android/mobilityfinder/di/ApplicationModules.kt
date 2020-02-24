package org.deafsapps.android.mobilityfinder.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import org.deafsapps.android.mobilityfinder.datalayer.di.DatalayerComponent
import org.deafsapps.android.mobilityfinder.domainlayer.di.DomainlayerComponent
import org.deafsapps.android.mobilityfinder.presentationlayer.di.PresentationlayerComponent
import javax.inject.Singleton

//@Module(subcomponents = [PresentationlayerComponent::class, DomainlayerComponent::class, DatalayerComponent::class])
//class SubcomponentsModule

@Module
abstract class UtilsModule {

    @Singleton
    @Binds
    abstract fun provideApplicationContext(context: Context): Context

}