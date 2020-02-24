package org.deafsapps.android.mobilityfinder.presentationlayer.di

import dagger.Component
import dagger.Subcomponent
import org.deafsapps.android.mobilityfinder.domainlayer.di.DomainlayerAbstractModule
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.main.view.ui.MainActivity
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.splash.view.ui.SplashActivity

@Component(modules = [DomainlayerAbstractModule::class])
interface PresentationlayerComponent {

    @Component.Factory
    interface Factory {
        fun create(): PresentationlayerComponent
    }

    fun inject(activity: SplashActivity)
    fun inject(activity: MainActivity)

}

interface PresentationlayerComponentProvider {
    fun providePresentationlayerComponent(): PresentationlayerComponent
}