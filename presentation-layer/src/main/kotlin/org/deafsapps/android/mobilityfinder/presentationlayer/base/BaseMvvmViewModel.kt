package org.deafsapps.android.mobilityfinder.presentationlayer.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.deafsapps.android.mobilityfinder.domainlayer.base.BaseDomainLayerBridge
import kotlin.coroutines.CoroutineContext

/**
 * This parametrized abstract class is intended to be extended by any app presentation-layer view-model which aims to be
 * integrated within the MVVM architecture pattern.
 *
 * @param T represents the bridge to the domain layer, and must extend from [BaseDomainLayerBridge]
 * @param S represents the state of the view, and must extend from [BaseState]
 * @property bridge the bridge which connects to the 'domain-layer' module
 * @property screenState the [LiveData] which will be updated to notify the view
 * @property viewModelJob represents the job to be launched for the coroutine
 * @property coroutineContext a context to host the coroutine
 *
 * @author Pablo L. Sordo
 * @since 1.0
 */
abstract class BaseMvvmViewModel<T : BaseDomainLayerBridge, S : BaseState> : ViewModel(),
    CoroutineScope {

    abstract val bridge: T?
    abstract val screenState: LiveData<ScreenState<S>>
    private val viewModelJob = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + viewModelJob

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    protected fun getDomainLayerBridge(): BaseDomainLayerBridge? = bridge

    /**
     * To be implemented by the ViewModel implementation
     */
    abstract fun getDomainLayerBridgeId(): String

}