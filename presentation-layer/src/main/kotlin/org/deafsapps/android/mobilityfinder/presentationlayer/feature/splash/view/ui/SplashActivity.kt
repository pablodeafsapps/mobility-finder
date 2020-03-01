package org.deafsapps.android.mobilityfinder.presentationlayer.feature.splash.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import org.deafsapps.android.mobilityfinder.domainlayer.feature.splash.SplashDomainLayerBridge
import org.deafsapps.android.mobilityfinder.presentationlayer.base.BaseMvvmView
import org.deafsapps.android.mobilityfinder.presentationlayer.base.ScreenState
import org.deafsapps.android.mobilityfinder.presentationlayer.di.PresentationlayerComponentProvider
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.main.view.ui.MainActivity
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.splash.view.state.SplashState
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.splash.viewmodel.SplashActivityViewModel
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class SplashActivity : AppCompatActivity(),
    BaseMvvmView<SplashActivityViewModel, SplashDomainLayerBridge, SplashState> {

    @Inject
    lateinit var vm: SplashActivityViewModel
    override val viewModel: SplashActivityViewModel by lazy { vm }

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as PresentationlayerComponentProvider)
            .providePresentationlayerComponent().inject(this)
        super.onCreate(savedInstanceState)
        initModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onViewCreated()
    }

    override fun processRenderState(renderState: SplashState?) {
        when (renderState) {
            is SplashState.LoadingFinished -> navigateToMainActivity()
        }
    }

    private fun initModel() {
        viewModel.screenState.observe(this, Observer { screenState ->
            when (screenState) {
                is ScreenState.Render<SplashState> -> processRenderState(screenState.renderState)
            }
        })
    }

    private fun navigateToMainActivity() {
        startActivity<MainActivity>()
        finish()
    }

}
