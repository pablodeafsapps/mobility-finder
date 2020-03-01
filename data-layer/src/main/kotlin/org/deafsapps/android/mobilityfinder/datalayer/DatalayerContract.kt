package org.deafsapps.android.mobilityfinder.datalayer

import arrow.core.Either
import org.deafsapps.android.mobilityfinder.datalayer.domain.MobilityResourceRequestDto
import org.deafsapps.android.mobilityfinder.domainlayer.domain.FailureBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceBo

interface DatalayerContract {

    companion object {
        const val CONNECTIVITY_DATA_SOURCE_TAG = "connectivityDataSource"
        const val MOBILITY_RESOURCES_DATA_SOURCE_TAG = "mobilityResourcesDataSource"
    }

    interface ConnectivityDataSource {
        suspend fun checkNetworkConnectionAvailability(): Boolean
    }

    interface MobilityDataSource {
        suspend fun fetchMobilityResourceListResponse(request: MobilityResourceRequestDto): Either<FailureBo, List<MobilityResourceBo>>
    }

}