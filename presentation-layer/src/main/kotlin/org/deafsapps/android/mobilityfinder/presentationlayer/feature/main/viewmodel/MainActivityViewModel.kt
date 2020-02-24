package org.deafsapps.android.mobilityfinder.presentationlayer.feature.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.deafsapps.android.mobilityfinder.domainlayer.feature.main.MAIN_DOMAIN_BRIDGE_TAG
import org.deafsapps.android.mobilityfinder.domainlayer.feature.main.MainDomainLayerBridge
import org.deafsapps.android.mobilityfinder.domainlayer.feature.splash.SPLASH_DOMAIN_BRIDGE_TAG
import org.deafsapps.android.mobilityfinder.domainlayer.feature.splash.SplashDomainLayerBridge
import org.deafsapps.android.mobilityfinder.presentationlayer.base.BaseMvvmViewModel
import org.deafsapps.android.mobilityfinder.presentationlayer.base.ScreenState
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.main.view.state.MainState
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.splash.view.state.SplashState
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(override val bridge: MainDomainLayerBridge) : BaseMvvmViewModel<MainDomainLayerBridge, MainState>() {

//    override val bridge: MainDomainLayerBridge? = null
    private lateinit var _mainState: MutableLiveData<ScreenState<MainState>>
    override val screenState: LiveData<ScreenState<MainState>>
        get() {
            if (!::_mainState.isInitialized) {
                _mainState = MutableLiveData()
            }
            return _mainState
        }

    override fun getDomainLayerBridgeId(): String = MAIN_DOMAIN_BRIDGE_TAG

    fun onViewCreated(): Int = bridge.request()

}