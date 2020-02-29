package org.deafsapps.android.mobilityfinder.datalayer.domain

import com.google.gson.annotations.SerializedName

data class MobilityResourceRequestDto(
    val lowerLeftLatLon: String,
    val upperRightLatLon: String
)

data class MobilityResourceDto(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("x") val x: Double? = null,
    @SerializedName("y") val y: Double? = null,
    @SerializedName("scheduledArrival") val scheduledArrival: Int? = null,
    @SerializedName("locationType") val locationType: Int? = null,
    @SerializedName("companyZoneId") val companyZoneId: Int? = null,
    @SerializedName("lat") val lat: Double? = null,
    @SerializedName("lon") val lon: Double? = null
)

sealed class FailureDto(val msg: String?) {
    class RequestError(val code: Int, msg: String?) : FailureDto(msg)
    class ServerError(msg: String?) : FailureDto(msg)
    object NoConnection : FailureDto("No connection")
    object NoData : FailureDto("No data")
    object Unknown : FailureDto("Unknown error")
}