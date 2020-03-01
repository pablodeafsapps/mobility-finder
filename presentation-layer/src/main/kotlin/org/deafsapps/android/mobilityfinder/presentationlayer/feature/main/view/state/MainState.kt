package org.deafsapps.android.mobilityfinder.presentationlayer.feature.main.view.state

import com.google.android.gms.maps.model.Marker
import org.deafsapps.android.mobilityfinder.presentationlayer.base.BaseState
import org.deafsapps.android.mobilityfinder.presentationlayer.domain.FailureVo
import org.deafsapps.android.mobilityfinder.presentationlayer.domain.MobilityResourceVo

sealed class MainState : BaseState() {
    class DisplayMobilityResources(val data: List<MobilityResourceVo>) : MainState()
    class MoveCameraToFirstLocation(val location: MobilityResourceVo) : MainState()
    class NavigateToDetail(val data: Marker) : MainState()
    class ShowError(val failure: FailureVo?) : MainState()
}