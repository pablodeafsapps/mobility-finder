package org.deafsapps.android.mobilityfinder.domainlayer.domain

data class MobilityResourceRequestBo(
    val lowerLeftLat: Double = 38.711046,
    val lowerLeftLon: Double = -9.160096,
    val upperRightLat: Double = 38.739429,
    val upperRightLon: Double = -9.137115
)

data class MobilityResourceBo(
    val id: String,
    val name: String,
    val x: Double,
    val y: Double,
    val scheduledArrival: Int,
    val locationType: Int,
    val companyZoneId: Int,
    val lat: Double,
    val lon: Double
)

sealed class FailureBo(var msg: String = "n/a") {
    class InputParamsError(msg: String) : FailureBo(msg)
    class RequestError(msg: String) : FailureBo(msg)
    class ServerError(msg: String) : FailureBo(msg)
    object NoConnection : FailureBo("No connection")
    object NoData : FailureBo("No data")
    object Unknown : FailureBo("Unknown error")
}