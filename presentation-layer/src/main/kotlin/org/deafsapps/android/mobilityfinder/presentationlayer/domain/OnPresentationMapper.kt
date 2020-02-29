package org.deafsapps.android.mobilityfinder.presentationlayer.domain

import org.deafsapps.android.mobilityfinder.domainlayer.domain.FailureBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceBo

fun List<MobilityResourceBo>.mobilityResourceBoToVo(): List<MobilityResourceVo> =
    map { it.boToVo() }

fun MobilityResourceBo.boToVo() = MobilityResourceVo(
    id = id,
    name = name,
    x = x.toString(),
    y = y.toString(),
    scheduledArrival = scheduledArrival.toString(),
    locationType = locationType.toString(),
    companyZoneId = companyZoneId.toString(),
    lat = lat,
    lon = lon
)

/**
 * Extension function which maps a failure Business Object to a failure Visual Object
 *
 * @return the [FailureVo] type equivalent datum
 *
 * @author Pablo L. Sordo
 * @since 1.0
 */
fun FailureBo.boToVoFailure(): FailureVo {
    return when (this) {
        is FailureBo.InputParamsError -> FailureVo.Error(msg = msg)
        is FailureBo.RequestError -> FailureVo.Error(msg = "Request Error: $msg")
        is FailureBo.ServerError -> FailureVo.Error(msg = "Server Error: $msg")
        FailureBo.NoConnection -> FailureVo.NoConnection
        FailureBo.NoData -> FailureVo.NoData
        FailureBo.Unknown -> FailureVo.Unknown
    }
}