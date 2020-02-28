package org.deafsapps.android.mobilityfinder.presentationlayer.feature.splash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.deafsapps.android.mobilityfinder.domainlayer.feature.splash.SPLASH_DOMAIN_BRIDGE_TAG
import org.deafsapps.android.mobilityfinder.domainlayer.feature.splash.SplashDomainLayerBridge
import org.deafsapps.android.mobilityfinder.presentationlayer.base.BaseMvvmViewModel
import org.deafsapps.android.mobilityfinder.presentationlayer.base.ScreenState
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.splash.view.state.SplashState
import javax.inject.Inject

class SplashActivityViewModel @Inject constructor() : BaseMvvmViewModel<SplashDomainLayerBridge, SplashState>() {

    override val bridge: SplashDomainLayerBridge? = null   // no 'domain-layer' needed
    private lateinit var _splashState: MutableLiveData<ScreenState<SplashState>>
    override val screenState: LiveData<ScreenState<SplashState>>
        get() {
            if (!::_splashState.isInitialized) {
                _splashState = MutableLiveData()
            }
            return _splashState
        }

    override fun getDomainLayerBridgeId(): String = SPLASH_DOMAIN_BRIDGE_TAG

    fun onViewCreated() {
        _splashState.value = ScreenState.Render(SplashState.LoadingFinished)
    }

}