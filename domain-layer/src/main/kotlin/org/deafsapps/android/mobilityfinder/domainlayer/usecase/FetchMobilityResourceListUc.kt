package org.deafsapps.android.mobilityfinder.domainlayer.usecase

import arrow.core.Either
import org.deafsapps.android.mobilityfinder.domainlayer.DomainlayerContract
import org.deafsapps.android.mobilityfinder.domainlayer.domain.FailureBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceBo
import org.deafsapps.android.mobilityfinder.domainlayer.utils.handleMultiCatch
import javax.inject.Inject

const val FETCH_MOBILITY_RESOURCE_LIST_UC_TAG = "fetchMobilityResourceeListUc"

class FetchMobilityResourceListUc @Inject constructor(
    private val dataRepository: DomainlayerContract.Datalayer.MobilityResourcesRepository
) : DomainlayerContract.Presentationlayer.UseCase<Any, List<@JvmSuppressWildcards MobilityResourceBo>> {

    override suspend fun run(params: Any?): Either<FailureBo, List<MobilityResourceBo>> =
        try {
            dataRepository.fetchMobilityResourceList()
        } catch (exception: Exception) {
            exception.handleMultiCatch()
        }

}