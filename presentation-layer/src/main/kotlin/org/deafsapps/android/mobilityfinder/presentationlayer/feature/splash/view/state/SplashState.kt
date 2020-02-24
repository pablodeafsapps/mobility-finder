package org.deafsapps.android.mobilityfinder.presentationlayer.feature.splash.view.state

import org.deafsapps.android.mobilityfinder.presentationlayer.base.BaseState

sealed class SplashState : BaseState() {
    object LoadingFinished : SplashState()
}