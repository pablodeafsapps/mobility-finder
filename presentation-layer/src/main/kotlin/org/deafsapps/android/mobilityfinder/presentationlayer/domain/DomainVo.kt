package org.deafsapps.android.mobilityfinder.presentationlayer.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MobilityResourceVo(
    val id: String,
    val name: String,
    val x: String,
    val y: String,
    val scheduledArrival: String,
    val locationType: String,
    val companyZoneId: String,
    val lat: Double,
    val lon: Double
) : Parcelable

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