package org.deafsapps.android.mobilityfinder.presentationlayer.feature.main.view.state

import org.deafsapps.android.mobilityfinder.presentationlayer.base.BaseState
import org.deafsapps.android.mobilityfinder.presentationlayer.domain.FailureVo
import org.deafsapps.android.mobilityfinder.presentationlayer.domain.MobilityResourceVo

sealed class MainState : BaseState() {
    class ShowError(val failure: FailureVo?) : MainState()
    class DisplayReferenceLocationData(val data: List<MobilityResourceVo>) : MainState()
}