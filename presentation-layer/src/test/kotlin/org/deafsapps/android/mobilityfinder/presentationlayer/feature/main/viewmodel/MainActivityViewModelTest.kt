package org.deafsapps.android.mobilityfinder.presentationlayer.feature.main.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.deafsapps.android.mobilityfinder.domainlayer.domain.FailureBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceBo
import org.deafsapps.android.mobilityfinder.domainlayer.feature.main.MainDomainLayerBridge
import org.deafsapps.android.mobilityfinder.presentationlayer.base.ScreenState
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.main.view.state.MainState
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

private const val DEFAULT_VALUE_STRING = "none"
private const val DEFAULT_VALUE_INT = -1
private const val DEFAULT_VALUE_DOUBLE = 0.0

class MainActivityViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `When 'onViewCreated' is invoked, bridge returns error -- 'ShowError' state is set`() {
        // given
        val mockBridge: MainDomainLayerBridge = mock()
        val argumentCaptor = argumentCaptor<(Either<FailureBo, List<MobilityResourceBo>>) -> Unit>()
        val viewModel = MainActivityViewModel(bridge = mockBridge)
        viewModel.screenState
        // when
        viewModel.onViewCreated()
        // then
        verify(mockBridge).fetchMobilityRerources(any(), any(), argumentCaptor.capture())
        argumentCaptor.firstValue.invoke(FailureBo.Unknown.left())
        viewModel.screenState.observeForever { state ->
            assertTrue((state as? ScreenState.Render)?.renderState is MainState.ShowError)
        }
    }

    @Test
    fun `When 'onViewCreated' is invoked, bridge returns empty data list -- 'NoData' state is set`() {
        // given
        val mockBridge: MainDomainLayerBridge = mock()
        val argumentCaptor = argumentCaptor<(Either<FailureBo, List<MobilityResourceBo>>) -> Unit>()
        val viewModel = MainActivityViewModel(bridge = mockBridge)
        viewModel.screenState
        // when
        viewModel.onViewCreated()
        // then
        verify(mockBridge).fetchMobilityRerources(any(), any(), argumentCaptor.capture())
        argumentCaptor.firstValue.invoke(emptyList<MobilityResourceBo>().right())
        viewModel.screenState.observeForever { state ->
            assertTrue((state as? ScreenState.Render)?.renderState is MainState.ShowError)
        }
    }

    @Test
    fun `When 'onViewCreated' is invoked, bridge returns non-empty data list -- 'DisplayMobilityResources' state is set`() {
        // given
        val mockBridge: MainDomainLayerBridge = mock()
        val argumentCaptor = argumentCaptor<(Either<FailureBo, List<MobilityResourceBo>>) -> Unit>()
        val viewModel = MainActivityViewModel(bridge = mockBridge)
        viewModel.screenState
        // when
        viewModel.onViewCreated()
        // then
        verify(mockBridge).fetchMobilityRerources(any(), any(), argumentCaptor.capture())
        argumentCaptor.firstValue.invoke(getDummyMobilityResourceBoList().right())
        viewModel.screenState.observeForever { state ->
            assertTrue((state as? ScreenState.Render)?.renderState is MainState.MoveCameraToFirstLocation)
        }
    }

    private fun getDummyMobilityResourceBoList(): List<MobilityResourceBo> = listOf(
        getDummyMobilityResourceBo()
    )

    private fun getDummyMobilityResourceBo(): MobilityResourceBo = MobilityResourceBo(
        id = DEFAULT_VALUE_STRING,
        name = DEFAULT_VALUE_STRING,
        x = DEFAULT_VALUE_DOUBLE,
        y = DEFAULT_VALUE_DOUBLE,
        scheduledArrival = DEFAULT_VALUE_INT,
        locationType = DEFAULT_VALUE_INT,
        companyZoneId = DEFAULT_VALUE_INT,
        lat = DEFAULT_VALUE_DOUBLE,
        lon = DEFAULT_VALUE_DOUBLE
    )

}