package org.deafsapps.android.mobilityfinder.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import org.deafsapps.android.mobilityfinder.presentationlayer.di.PresentationlayerComponent
import javax.inject.Singleton

@Module(subcomponents = [PresentationlayerComponent::class])
class SubcomponentsModule

@Module
class UtilsModule(private val ctx: Context) {

    @Singleton
    @Provides
    fun provideApplicationContext(): Context = ctx

}