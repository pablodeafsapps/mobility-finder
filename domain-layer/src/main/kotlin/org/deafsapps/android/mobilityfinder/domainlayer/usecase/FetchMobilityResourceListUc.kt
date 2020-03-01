package org.deafsapps.android.mobilityfinder.domainlayer.usecase

import arrow.core.Either
import arrow.core.left
import org.deafsapps.android.mobilityfinder.domainlayer.DomainlayerContract
import org.deafsapps.android.mobilityfinder.domainlayer.DomainlayerContract.Datalayer.Companion.MOBILITY_RESOURCES_REPOSITORY_TAG
import org.deafsapps.android.mobilityfinder.domainlayer.domain.FailureBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceRequestBo
import org.deafsapps.android.mobilityfinder.domainlayer.utils.handleMultiCatch
import javax.inject.Inject
import javax.inject.Named

const val FETCH_MOBILITY_RESOURCE_LIST_UC_TAG = "fetchMobilityResourceListUc"

class FetchMobilityResourceListUc @Inject constructor(
    @Named(MOBILITY_RESOURCES_REPOSITORY_TAG)
    private val dataRepository: DomainlayerContract.Datalayer.DataRepository
) : DomainlayerContract.Presentationlayer.UseCase<MobilityResourceRequestBo, List<@JvmSuppressWildcards MobilityResourceBo>> {

    override suspend fun run(params: MobilityResourceRequestBo?): Either<FailureBo, List<MobilityResourceBo>> =
        params?.let {
            try {
                dataRepository.fetchMobilityResourceList(request = params)
            } catch (exception: Exception) {
                exception.handleMultiCatch()
            }
        } ?: run {
            FailureBo.InputParamsError("Request input parameters are null").left()
        }

}