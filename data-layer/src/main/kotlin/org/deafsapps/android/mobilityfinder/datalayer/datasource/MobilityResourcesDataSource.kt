package org.deafsapps.android.mobilityfinder.datalayer.datasource

import arrow.core.Either
import org.deafsapps.android.mobilityfinder.datalayer.DatalayerContract
import org.deafsapps.android.mobilityfinder.datalayer.domain.MobilityResourceRequestDto
import org.deafsapps.android.mobilityfinder.datalayer.domain.mobilityResourceDtoToBo
import org.deafsapps.android.mobilityfinder.datalayer.service.MobilityResourcesService
import org.deafsapps.android.mobilityfinder.datalayer.utils.safeCall
import org.deafsapps.android.mobilityfinder.domainlayer.domain.FailureBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceBo
import retrofit2.Retrofit
import javax.inject.Inject

class MobilityResourcesDataSource @Inject constructor(private val retrofitInstance: Retrofit) :
    DatalayerContract.MobilityResourcesDataSource {

    override suspend fun fetchMobilityResourceListResponse(request: MobilityResourceRequestDto): Either<FailureBo, List<MobilityResourceBo>> =
        retrofitInstance.create(MobilityResourcesService::class.java).fetchMobilityResourceListAsync(
            lowerLeftLatLon = request.lowerLeftLatLon, upperRightLatLon = request.upperRightLatLon
        ).safeCall(transform = { it.mobilityResourceDtoToBo() })

}