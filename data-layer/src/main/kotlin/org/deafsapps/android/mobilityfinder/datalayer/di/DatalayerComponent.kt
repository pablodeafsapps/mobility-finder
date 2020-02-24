package org.deafsapps.android.mobilityfinder.datalayer.di

import dagger.Component

@Component
interface DatalayerComponent {

    @Component.Factory
    interface Factory {
        fun create(): DatalayerComponent
    }

}

interface DatalayerComponentProvider {
    fun provideDatalayerComponent(): DatalayerComponent
}