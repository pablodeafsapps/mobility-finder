package org.deafsapps.android.mobilityfinder.domainlayer

import arrow.core.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.deafsapps.android.mobilityfinder.domainlayer.domain.FailureBo

interface DomainlayerContract {

    interface Presentationlayer {

        interface UseCase<in T, out S> {
            fun invoke(
                scope: CoroutineScope,
                params: T? = null,
                onResult: (Either<FailureBo, S>) -> Unit
            ) {
                // task undertaken in a worker thread
                val job = scope.async { run(params) }
                // once completed, result sent in the Main thread
                scope.launch(Dispatchers.Main) { onResult(job.await()) }
            }

            suspend fun run(params: T? = null): Either<FailureBo, S>
        }

    }

    interface Datalayer {

        companion object {
            const val MOBILITY_RESOURCES_REPOSITORY_TAG = "mobilityResourcesRepository"
        }

        interface MobilityResourcesRepository {
//            suspend fun fetchMobilityResourceList(): Either<FailureBo, List<CompanyBo>>
        }

    }

}