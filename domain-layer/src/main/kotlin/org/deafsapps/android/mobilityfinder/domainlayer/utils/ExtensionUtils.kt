package org.deafsapps.android.mobilityfinder.domainlayer.utils

import arrow.core.Either
import arrow.core.EitherOf
import arrow.core.fix
import arrow.core.left
import org.deafsapps.android.mobilityfinder.domainlayer.domain.FailureBo
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * This extension function provides an inline 'flatMap' feature for the 'Either' type. All credits
 * to Félix Castillo Moya (Joséfelix.castillomoya@gmail.com).
 */
inline fun <A, B, C> EitherOf<A, B>.flatMapInline(f: (B) -> Either<A, C>): Either<A, C> =
    fix().let {
        when (it) {
            is Either.Right -> f(it.b)
            is Either.Left -> it
        }
    }

/**
 * This extension function provides an inline 'map' feature for the 'Either' type. All credits to
 * Félix Castillo Moya (Joséfelix.castillomoya@gmail.com).
 */
inline fun <A, B, C> EitherOf<A, B>.mapInline(f: (B) -> C): Either<A, C> =
    flatMapInline { Either.Right(f(it)) }

/**
 * This extension function maps all the possible [Exception] cases which normally apply to service queries.
 */
fun Exception.handleMultiCatch(): Either<FailureBo, Nothing> =
    when (this) {
        is SocketTimeoutException -> FailureBo.ServerError(msg = "Socket timeout exception - $localizedMessage")
        is UnknownHostException -> FailureBo.ServerError(msg = "Unknown host exception - $localizedMessage")
        else -> FailureBo.ServerError(msg = "Exception - $localizedMessage")
    }.left()