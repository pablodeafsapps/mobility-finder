package org.deafsapps.android.mobilityfinder.domainlayer.feature.main

import arrow.core.Either
import kotlinx.coroutines.CoroutineScope
import org.deafsapps.android.mobilityfinder.domainlayer.DomainlayerContract
import org.deafsapps.android.mobilityfinder.domainlayer.domain.FailureBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceBo
import org.deafsapps.android.mobilityfinder.domainlayer.usecase.FETCH_MOBILITY_RESOURCE_LIST_UC_TAG
import javax.inject.Inject
import javax.inject.Named

class MainDomainLayerBridgeImpl @Inject constructor(
    @Named(FETCH_MOBILITY_RESOURCE_LIST_UC_TAG)
    private val fetchMobilityResourceListUc: @JvmSuppressWildcards DomainlayerContract.Presentationlayer.UseCase<Any, List<@JvmSuppressWildcards MobilityResourceBo>>
) : MainDomainLayerBridge {

    override fun fetchMobilityRerources(
        scope: CoroutineScope,
        onResult: (Either<FailureBo, List<MobilityResourceBo>>) -> Unit
    ) {
        fetchMobilityResourceListUc.invoke(scope = scope, onResult = onResult)
    }


}