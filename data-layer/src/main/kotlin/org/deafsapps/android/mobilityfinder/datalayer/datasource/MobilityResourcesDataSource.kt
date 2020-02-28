package org.deafsapps.android.mobilityfinder.datalayer.datasource

import arrow.core.Either
import org.deafsapps.android.mobilityfinder.datalayer.DatalayerContract
import org.deafsapps.android.mobilityfinder.datalayer.domain.mobilityResourceDtoToBo
import org.deafsapps.android.mobilityfinder.datalayer.service.MobilityResourcesService
import org.deafsapps.android.mobilityfinder.datalayer.utils.safeCall
import org.deafsapps.android.mobilityfinder.domainlayer.domain.FailureBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceBo
import retrofit2.Retrofit

class MobilityResourcesDataSource(private val retrofitInstance: Retrofit) :
    DatalayerContract.MobilityResourcesDataSource {

    override suspend fun fetchMobilityResourceListResponse(
        lowerLeftCorner: String, upperRightCorner: String
    ): Either<FailureBo, List<MobilityResourceBo>> =
        retrofitInstance.create(MobilityResourcesService::class.java).fetchMobilityResourceListAsync(
            lowerLeftLatLon = lowerLeftCorner, upperRightLatLon = upperRightCorner
        ).safeCall(transform = { it.mobilityResourceDtoToBo() })

}