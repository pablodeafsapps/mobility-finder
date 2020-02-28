package org.deafsapps.android.mobilityfinder.datalayer

import arrow.core.Either
import org.deafsapps.android.mobilityfinder.domainlayer.domain.FailureBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceBo

interface DatalayerContract {

    companion object {
        const val MOBILITY_RESOURCES_DATA_SOURCE_TAG = "mobilityResourcesDataSource"
    }

    interface ConnectivityDataSource {
        suspend fun checkNetworkConnectionAvailability(): Boolean
    }

    interface MobilityResourcesDataSource {
        suspend fun fetchMobilityResourceListResponse(
            lowerLeftCorner: String, upperRightCorner: String
        ): Either<FailureBo, List<MobilityResourceBo>>
    }

}