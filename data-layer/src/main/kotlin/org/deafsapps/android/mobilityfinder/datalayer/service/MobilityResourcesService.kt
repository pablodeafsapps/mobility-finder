package org.deafsapps.android.mobilityfinder.datalayer.service

import org.deafsapps.android.mobilityfinder.datalayer.domain.MobilityResourceDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MobilityResourcesService {

    @Headers("accept: application/json")
    @GET("/tripplan/api/v1/routers/lisboa/resources")
    suspend fun fetchMobilityResourceListAsync(
        @Query("lowerLeftLatLon") lowerLeftLatLon: String,
        @Query("lowerLeftLatLon") upperRightLatLon: String
    ): Response<List<MobilityResourceDto>>

}