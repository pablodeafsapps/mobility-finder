package org.deafsapps.android.mobilityfinder.domainlayer.feature.main

import arrow.core.Either
import kotlinx.coroutines.CoroutineScope
import org.deafsapps.android.mobilityfinder.domainlayer.base.BaseDomainLayerBridge
import org.deafsapps.android.mobilityfinder.domainlayer.domain.FailureBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceRequestBo

const val MAIN_DOMAIN_BRIDGE_TAG = "mainDomainLayerBridge"

interface MainDomainLayerBridge : BaseDomainLayerBridge {

    fun fetchMobilityRerources(
        scope: CoroutineScope,
        params: MobilityResourceRequestBo,
        onResult: (Either<FailureBo, List<MobilityResourceBo>>) -> Unit = {}
    )

}