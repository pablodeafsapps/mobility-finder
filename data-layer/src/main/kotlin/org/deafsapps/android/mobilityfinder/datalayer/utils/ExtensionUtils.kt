package org.deafsapps.android.mobilityfinder.datalayer.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.deafsapps.android.mobilityfinder.datalayer.domain.FailureDto
import org.deafsapps.android.mobilityfinder.datalayer.domain.dtoToBoFailure
import org.deafsapps.android.mobilityfinder.domainlayer.domain.FailureBo
import retrofit2.Response

/**
 * This extension function allows any entity hosting a [Context] to easily check whether there is a
 * valid data connection. It also takes into account the Android version available.
 *
 * @author Pablo L. Sordo Martínez
 * @since 1.0
 */
fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        val networkCapabilities = connectivityManager.getNetworkCapabilities(
            connectivityManager.activeNetwork
        )
        when {
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> true
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> true
            //for other device how are able to connect with Ethernet
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) == true -> true
            else -> false
        }
    } else {
        connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo?.isConnected == true
    }
}

/**
 * This extension function provides a proceeding to handle with 'Retrofit' [Response] objects, so that
 * a parametrized 'Either' type is returned. All credits to Félix Castillo Moya (Joséfelix.castillomoya@gmail.com).
 *
 * @author Pablo L. Sordo Martínez
 * @since 1.0
 */
fun <T, R> Response<T>.safeCall(
    transform: (T) -> R,
    errorHandler: (Response<T>).() -> Either<FailureBo, Nothing> = { handleDataSourceError() }
): Either<FailureBo, R> =
    try {
        this.run {
            if (isSuccessful) {
                val body = body()
                if (body != null) {
                    transform(body).right()
                } else {
                    errorHandler()
                }
            } else {
                errorHandler()
            }
        }
    } catch (exception: Exception) {
        errorHandler()
    }

/**
 * This extension function provides a mechanism to handle HTTP errors coming from a Retrofit
 * [Response]. It returns an [Either] which models the [FailureBo] to be transmitted beyond the
 * domain layer.
 *
 * @author Pablo L. Sordo Martínez
 * @since 1.0
 */
fun <T> Response<T>?.handleDataSourceError(): Either<FailureBo, Nothing> =
    when (this?.code()) {
        400 -> FailureDto.RequestError(code = 400, msg = "Bad Request")
        401 -> FailureDto.RequestError(code = 401, msg = "Unauthorized")
        403 -> FailureDto.RequestError(code = 403, msg = "Forbidden")
        404 -> FailureDto.NoData
        408 -> FailureDto.RequestError(code = 408, msg = "Request Timeout")
        in 500..599 -> FailureDto.ServerError(this?.message())
        else -> FailureDto.Unknown
    }.dtoToBoFailure().left()