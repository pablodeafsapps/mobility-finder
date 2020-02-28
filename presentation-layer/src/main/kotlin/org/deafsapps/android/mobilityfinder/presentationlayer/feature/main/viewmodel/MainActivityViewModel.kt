package org.deafsapps.android.mobilityfinder.presentationlayer.feature.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.deafsapps.android.mobilityfinder.domainlayer.domain.FailureBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceBo
import org.deafsapps.android.mobilityfinder.domainlayer.feature.main.MAIN_DOMAIN_BRIDGE_TAG
import org.deafsapps.android.mobilityfinder.domainlayer.feature.main.MainDomainLayerBridge
import org.deafsapps.android.mobilityfinder.presentationlayer.base.BaseMvvmViewModel
import org.deafsapps.android.mobilityfinder.presentationlayer.base.ScreenState
import org.deafsapps.android.mobilityfinder.presentationlayer.domain.boToVoFailure
import org.deafsapps.android.mobilityfinder.presentationlayer.domain.mobilityResourceBoToVo
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.main.view.state.MainState
import javax.inject.Inject
import javax.inject.Named

open class MainActivityViewModel @Inject constructor(
    @Named(MAIN_DOMAIN_BRIDGE_TAG)
    override val bridge: MainDomainLayerBridge
) : BaseMvvmViewModel<MainDomainLayerBridge, MainState>() {

    private lateinit var _mainState: MutableLiveData<ScreenState<MainState>>
    override val screenState: LiveData<ScreenState<MainState>>
        get() {
            if (!::_mainState.isInitialized) {
                _mainState = MutableLiveData()
            }
            return _mainState
        }

    override fun getDomainLayerBridgeId(): String = MAIN_DOMAIN_BRIDGE_TAG

    fun onViewCreated() {
        bridge.fetchMobilityRerources(
            scope = this,
            onResult = {
                it.fold(::handleError, ::handleFetchMobilityResourcesSuccess)
            }
        )
    }

    private fun handleFetchMobilityResourcesSuccess(data: List<MobilityResourceBo>) {
        _mainState.value =
            ScreenState.Render(MainState.DisplayReferenceLocationData(data.mobilityResourceBoToVo()))
    }

    private fun handleError(failureBo: FailureBo) {
        _mainState.value =
            ScreenState.Render(MainState.ShowError(failureBo.boToVoFailure()))
    }

}