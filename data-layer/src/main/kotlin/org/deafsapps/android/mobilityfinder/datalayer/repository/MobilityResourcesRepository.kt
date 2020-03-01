package org.deafsapps.android.mobilityfinder.datalayer.repository

import android.util.Log
import arrow.core.Either
import arrow.core.left
import org.deafsapps.android.mobilityfinder.datalayer.DatalayerContract
import org.deafsapps.android.mobilityfinder.datalayer.domain.FailureDto
import org.deafsapps.android.mobilityfinder.datalayer.domain.boToDto
import org.deafsapps.android.mobilityfinder.datalayer.domain.dtoToBoFailure
import org.deafsapps.android.mobilityfinder.domainlayer.DomainlayerContract
import org.deafsapps.android.mobilityfinder.domainlayer.domain.FailureBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceRequestBo
import java.net.SocketTimeoutException

object MobilityResourcesRepository : DomainlayerContract.Datalayer.DataRepository {

    lateinit var connectivityDataSource: DatalayerContract.ConnectivityDataSource
    lateinit var mobilityDataSource: DatalayerContract.MobilityDataSource

    /**
     * This method fetches a list of [MobilityResourceBo] after checking the data connection
     *
     * @return A [MobilityResourceBo] list, or an error otherwise
     * @throws SocketTimeoutException
     */
    @Throws(SocketTimeoutException::class)
    override suspend fun fetchMobilityResourceList(request: MobilityResourceRequestBo): Either<FailureBo, List<MobilityResourceBo>> =
        try {
            connectivityDataSource.checkNetworkConnectionAvailability().takeIf { it }?.let {
                mobilityDataSource.fetchMobilityResourceListResponse(request = request.boToDto())
            } ?: run {
                FailureDto.NoConnection.dtoToBoFailure().left()
            }
        } catch (e: IllegalStateException) {
            Log.e("fetchMobilityResou...", "Error: ${e.message}")
            FailureDto.Unknown.dtoToBoFailure().left()
        }

}