package org.deafsapps.android.mobilityfinder.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import dagger.BindsInstance
import dagger.Component
import org.deafsapps.android.mobilityfinder.datalayer.di.DatalayerComponent
import org.deafsapps.android.mobilityfinder.domainlayer.di.DomainlayerComponent
import org.deafsapps.android.mobilityfinder.presentationlayer.di.PresentationlayerComponent
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.main.view.ui.MainActivity
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.splash.view.ui.SplashActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [UtilsModule::class],
    dependencies = [PresentationlayerComponent::class, DomainlayerComponent::class, DatalayerComponent::class]
)
interface ApplicationGraph {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance applicationContext: Context,
            presentationlayerComponent: PresentationlayerComponent,
            domainlayerComponent: DomainlayerComponent,
            datalayerComponent: DatalayerComponent
        ): ApplicationGraph
    }

}