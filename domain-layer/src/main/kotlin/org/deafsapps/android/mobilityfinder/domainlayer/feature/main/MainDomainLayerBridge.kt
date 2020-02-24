package org.deafsapps.android.mobilityfinder.domainlayer.feature.main

import org.deafsapps.android.mobilityfinder.domainlayer.base.BaseDomainLayerBridge

const val MAIN_DOMAIN_BRIDGE_TAG = "mainDomainLayerBridge"

interface MainDomainLayerBridge : BaseDomainLayerBridge {

    fun request(): Int

}