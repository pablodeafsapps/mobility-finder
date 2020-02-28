package org.deafsapps.android.mobilityfinder.presentationlayer.base

import org.deafsapps.android.mobilityfinder.domainlayer.base.BaseDomainLayerBridge

/**
 * This parametrized interface is intended to be implemented by any app presentation-layer view which aims to be
 * integrated within the MVVM architecture pattern.
 *
 * @param T represents the ViewModel, and thus it must extend from @see{BaseMvvmViewModel}
 * @param S represents the bridge to the domain layer, and must extend from @see{BaseDomainLayerBridge}
 * @param U represents the state of the view, and must extend from @see{BaseState}
 * @property viewModel a reference to the [BaseMvvmViewModel] associated to this view
 *
 * @author Pablo L. Sordo
 * @since 1.0
 */
interface BaseMvvmView<T : BaseMvvmViewModel<S, U>, S : BaseDomainLayerBridge, U : BaseState> {

    val viewModel: T?

    /**
     * Handles the possible state values
     *
     * @param renderState the actual state, extending from U
     */
    fun processRenderState(renderState: U?)

}