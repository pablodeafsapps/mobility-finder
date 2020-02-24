package org.deafsapps.android.mobilityfinder.domainlayer.domain

sealed class FailureBo(var msg: String = "n/a") {
    class RequestError(msg: String) : FailureBo(msg)
    class ServerError(msg: String) : FailureBo(msg)
    object NoConnection : FailureBo("No connection")
    object NoData : FailureBo("No data")
    object Unknown : FailureBo("Unknown error")
}