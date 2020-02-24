package org.deafsapps.android.mobilityfinder.domainlayer.di

import dagger.Component

@Component(modules = [DomainlayerAbstractModule::class])
interface DomainlayerComponent {

    @Component.Factory
    interface Factory {
        fun create(): DomainlayerComponent
    }

}

interface DomainlayerComponentProvider {
    fun provideDomainlayerComponent(): DomainlayerComponent
}