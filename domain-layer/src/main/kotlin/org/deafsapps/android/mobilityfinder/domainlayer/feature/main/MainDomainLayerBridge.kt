package org.deafsapps.android.mobilityfinder.domainlayer.feature.main

import arrow.core.Either
import kotlinx.coroutines.CoroutineScope
import org.deafsapps.android.mobilityfinder.domainlayer.base.BaseDomainLayerBridge
import org.deafsapps.android.mobilityfinder.domainlayer.domain.FailureBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceBo

const val MAIN_DOMAIN_BRIDGE_TAG = "mainDomainLayerBridge"

interface MainDomainLayerBridge : BaseDomainLayerBridge {

    fun fetchMobilityRerources(
        scope: CoroutineScope,
        onResult: (Either<FailureBo, List<MobilityResourceBo>>) -> Unit = {}
    )

}