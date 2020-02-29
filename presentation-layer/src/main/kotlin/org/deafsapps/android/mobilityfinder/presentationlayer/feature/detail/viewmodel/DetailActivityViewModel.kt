package org.deafsapps.android.mobilityfinder.presentationlayer.feature.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.Marker
import org.deafsapps.android.mobilityfinder.domainlayer.domain.FailureBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceBo
import org.deafsapps.android.mobilityfinder.domainlayer.feature.detail.DETAIL_DOMAIN_BRIDGE_TAG
import org.deafsapps.android.mobilityfinder.domainlayer.feature.detail.DetailDomainLayerBridge
import org.deafsapps.android.mobilityfinder.domainlayer.feature.main.MAIN_DOMAIN_BRIDGE_TAG
import org.deafsapps.android.mobilityfinder.domainlayer.feature.main.MainDomainLayerBridge
import org.deafsapps.android.mobilityfinder.presentationlayer.base.BaseMvvmViewModel
import org.deafsapps.android.mobilityfinder.presentationlayer.base.ScreenState
import org.deafsapps.android.mobilityfinder.presentationlayer.domain.FailureVo
import org.deafsapps.android.mobilityfinder.presentationlayer.domain.MobilityResourceVo
import org.deafsapps.android.mobilityfinder.presentationlayer.domain.boToVoFailure
import org.deafsapps.android.mobilityfinder.presentationlayer.domain.mobilityResourceBoToVo
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.detail.view.state.DetailState
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.main.view.state.MainState
import javax.inject.Inject
import javax.inject.Named

class DetailActivityViewModel @Inject constructor(): BaseMvvmViewModel<DetailDomainLayerBridge, DetailState>() {

    override val bridge: DetailDomainLayerBridge? = null   // no 'domain-layer' needed
    private lateinit var _detailState: MutableLiveData<ScreenState<DetailState>>
    override val screenState: LiveData<ScreenState<DetailState>>
        get() {
            if (!::_detailState.isInitialized) {
                _detailState = MutableLiveData()
            }
            return _detailState
        }

    override fun getDomainLayerBridgeId(): String = DETAIL_DOMAIN_BRIDGE_TAG

    fun onViewCreated(data: MobilityResourceVo?) {
        _detailState.value = ScreenState.Render(
            if (data != null) DetailState.DisplayMobilityResourceInfo(info = data) else DetailState.ShowError(FailureVo.NoData)
        )
    }

}