package org.deafsapps.android.mobilityfinder.presentationlayer.feature.detail.view.state

import org.deafsapps.android.mobilityfinder.presentationlayer.base.BaseState
import org.deafsapps.android.mobilityfinder.presentationlayer.domain.FailureVo
import org.deafsapps.android.mobilityfinder.presentationlayer.domain.MobilityResourceVo

sealed class DetailState : BaseState() {
    class ShowError(val failure: FailureVo?) : DetailState()
    class DisplayMobilityResourceInfo(val info: MobilityResourceVo): DetailState()
}