package org.deafsapps.android.mobilityfinder.presentationlayer.domain

data class MobilityResourceVo(
    private val id: String,
    private val name: String,
    private val x: String,
    private val y: String,
    private val scheduledArrival: String,
    private val locationType: String,
    private val companyZoneId: String,
    private val lat: String,
    private val lon: String
)

sealed class FailureVo(var msg: String? = "n/a") {

    abstract fun getErrorMessage(): String

    class Error(msg: String?) : FailureVo(msg) {
        override fun getErrorMessage() = "$msg"
    }

    object NoConnection : FailureVo() {
        override fun getErrorMessage() = "No connection"

    }

    object NoData : FailureVo() {
        override fun getErrorMessage() = "No data"
    }

    object Unknown : FailureVo() {
        override fun getErrorMessage() = "Unknown error"
    }

}