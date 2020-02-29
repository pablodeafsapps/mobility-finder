package org.deafsapps.android.mobilityfinder.presentationlayer.feature.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.Marker
import org.deafsapps.android.mobilityfinder.domainlayer.domain.FailureBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceRequestBo
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
        _mainState.value = ScreenState.Loading
        fetchMobilityResourcesAndHandleResponse()
    }

    fun onRefreshSelected() {
        _mainState.value = ScreenState.Loading
        fetchMobilityResourcesAndHandleResponse()
    }

    fun onLocationSelected(marker: Marker) {
        _mainState.value = ScreenState.Render(MainState.NavigateToDetail(data = marker))
    }

    private fun fetchMobilityResourcesAndHandleResponse() {
        bridge.fetchMobilityRerources(
            scope = this,
            params = MobilityResourceRequestBo(),   // default values
            onResult = {
                it.fold(::handleError, ::handleFetchMobilityResourcesSuccess)
            }
        )
    }

    private fun handleFetchMobilityResourcesSuccess(data: List<MobilityResourceBo>) {
        if (data.isNotEmpty()) {
            _mainState.value =
                ScreenState.Render(MainState.DisplayMobilityResources(data.mobilityResourceBoToVo()))
            _mainState.value =
                ScreenState.Render(MainState.MoveCameraToFirstLocation(data.mobilityResourceBoToVo().first()))
        } else {
            _mainState.value =
                ScreenState.Render(MainState.ShowError(FailureBo.NoData.boToVoFailure()))
        }
    }

    private fun handleError(failureBo: FailureBo) {
        _mainState.value =
            ScreenState.Render(MainState.ShowError(failureBo.boToVoFailure()))
    }

}