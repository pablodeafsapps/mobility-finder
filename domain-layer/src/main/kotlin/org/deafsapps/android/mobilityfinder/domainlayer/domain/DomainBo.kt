package org.deafsapps.android.mobilityfinder.domainlayer.domain

data class MobilityResourceBo(
    val id: String,
    val name: String,
    val x: Float,
    val y: Float,
    val scheduledArrival: Int,
    val locationType: Int,
    val companyZoneId: Int,
    val lat: Float,
    val lon: Float
)

sealed class FailureBo(var msg: String = "n/a") {
    class RequestError(msg: String) : FailureBo(msg)
    class ServerError(msg: String) : FailureBo(msg)
    object NoConnection : FailureBo("No connection")
    object NoData : FailureBo("No data")
    object Unknown : FailureBo("Unknown error")
}