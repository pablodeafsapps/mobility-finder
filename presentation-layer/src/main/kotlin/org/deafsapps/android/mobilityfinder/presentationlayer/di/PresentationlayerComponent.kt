package org.deafsapps.android.mobilityfinder.presentationlayer.di

import dagger.Subcomponent
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.detail.view.ui.DetailActivity
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.main.view.ui.MainActivity
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.splash.view.ui.SplashActivity

@Subcomponent
interface PresentationlayerComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): PresentationlayerComponent
    }

    fun inject(activity: SplashActivity)
    fun inject(activity: MainActivity)
    fun inject(activity: DetailActivity)

}

interface PresentationlayerComponentProvider {
    fun providePresentationlayerComponent(): PresentationlayerComponent
}