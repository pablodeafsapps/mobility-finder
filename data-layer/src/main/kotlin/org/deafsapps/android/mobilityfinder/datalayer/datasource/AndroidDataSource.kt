package org.deafsapps.android.mobilityfinder.datalayer.datasource

import android.content.Context
import org.deafsapps.android.mobilityfinder.datalayer.DatalayerContract
import org.deafsapps.android.mobilityfinder.datalayer.utils.isNetworkAvailable
import javax.inject.Inject

class AndroidDataSource @Inject constructor(private val context: Context) : DatalayerContract.ConnectivityDataSource {

    /**
     * Checks whether there is a connection available or not
     *
     * @return A [Boolean] indicating the connection availability
     */
    override suspend fun checkNetworkConnectionAvailability(): Boolean =
        context.isNetworkAvailable()

}