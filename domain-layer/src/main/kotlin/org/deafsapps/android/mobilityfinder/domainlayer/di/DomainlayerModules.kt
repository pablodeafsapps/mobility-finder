package org.deafsapps.android.mobilityfinder.domainlayer.di

import dagger.Binds
import dagger.Module
import org.deafsapps.android.mobilityfinder.domainlayer.feature.main.MainDomainLayerBridge
import org.deafsapps.android.mobilityfinder.domainlayer.feature.main.MainDomainLayerBridgeImpl

@Module()
abstract class DomainlayerAbstractModule {

    @Binds
    abstract fun provideMainDomainLayerBridge(bridge: MainDomainLayerBridgeImpl): MainDomainLayerBridge

}