package org.deafsapps.android.mobilityfinder.datalayer.domain

import org.deafsapps.android.mobilityfinder.domainlayer.domain.FailureBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceRequestBo

const val DEFAULT_VALUE_STRING = "none"
const val DEFAULT_VALUE_INTEGER = -1
const val DEFAULT_VALUE_DOUBLE = 0.0

fun MobilityResourceRequestBo.boToDto() = MobilityResourceRequestDto(
    lowerLeftLatLon = "$lowerLeftLat,$lowerLeftLon",
    upperRightLatLon = "$upperRightLat,$upperRightLon"
)

fun List<MobilityResourceDto>.mobilityResourceDtoToBo(): List<MobilityResourceBo> =
    map { it.dtoToBo() }

private fun MobilityResourceDto.dtoToBo() = MobilityResourceBo(
    id = id ?: DEFAULT_VALUE_STRING,
    name = name ?: DEFAULT_VALUE_STRING,
    x = x ?: DEFAULT_VALUE_DOUBLE,
    y = y ?: DEFAULT_VALUE_DOUBLE,
    scheduledArrival = scheduledArrival ?: DEFAULT_VALUE_INTEGER,
    locationType = locationType ?: DEFAULT_VALUE_INTEGER,
    companyZoneId = companyZoneId ?: DEFAULT_VALUE_INTEGER,
    lat = lat ?: DEFAULT_VALUE_DOUBLE,
    lon = lon ?: DEFAULT_VALUE_DOUBLE
)

fun FailureDto.dtoToBoFailure(): FailureBo =
    when (this) {
        is FailureDto.RequestError -> FailureBo.RequestError(msg = "$code - $msg")
        is FailureDto.ServerError -> FailureBo.ServerError(msg = msg ?: DEFAULT_VALUE_STRING)
        FailureDto.NoConnection -> FailureBo.NoConnection
        FailureDto.NoData -> FailureBo.NoData
        FailureDto.Unknown -> FailureBo.Unknown
    }