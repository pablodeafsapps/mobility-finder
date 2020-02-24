package org.deafsapps.android.mobilityfinder.domainlayer.feature.main

import javax.inject.Inject

class MainDomainLayerBridgeImpl @Inject constructor() : MainDomainLayerBridge {

    override fun request(): Int = 3 + 5

}