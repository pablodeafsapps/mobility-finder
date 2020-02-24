package org.deafsapps.android.mobilityfinder.presentationlayer.feature.main.view.state

import org.deafsapps.android.mobilityfinder.presentationlayer.base.BaseState

sealed class MainState : BaseState() {
    object LoadingFinished : MainState()
}