package org.deafsapps.android.mobilityfinder.datalayer.repository

import android.util.Log
import arrow.core.Either
import arrow.core.left
import org.deafsapps.android.mobilityfinder.datalayer.DatalayerContract
import org.deafsapps.android.mobilityfinder.datalayer.domain.FailureDto
import org.deafsapps.android.mobilityfinder.datalayer.domain.dtoToBoFailure
import org.deafsapps.android.mobilityfinder.domainlayer.DomainlayerContract
import org.deafsapps.android.mobilityfinder.domainlayer.domain.FailureBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceBo
import java.net.SocketTimeoutException

object DataRepository : DomainlayerContract.Datalayer.MobilityResourcesRepository {

    lateinit var connectivityDataSource: DatalayerContract.ConnectivityDataSource
    lateinit var mobilityResourcesDataSource: DatalayerContract.MobilityResourcesDataSource

    /**
     * This method fetches a list of [MobilityResourceBo] after checking the data connection
     *
     * @return A [MobilityResourceBo] list, or an error otherwise
     * @throws SocketTimeoutException
     */
    @Throws(SocketTimeoutException::class)
    override suspend fun fetchMobilityResourceList(): Either<FailureBo, List<MobilityResourceBo>> =
        try {
            connectivityDataSource.checkNetworkConnectionAvailability().takeIf { it }?.let {
                mobilityResourcesDataSource.fetchMobilityResourceListResponse(
                    lowerLeftCorner = "38.711046,-9.160096", upperRightCorner = "38.739429,-9.137115"
                )
            } ?: run {
                FailureDto.NoConnection.dtoToBoFailure().left()
            }
        } catch (e: IllegalStateException) {
            Log.e("fetchMobilityResou...", "Error: ${e.message}")
            FailureDto.Unknown.dtoToBoFailure().left()
        }

}