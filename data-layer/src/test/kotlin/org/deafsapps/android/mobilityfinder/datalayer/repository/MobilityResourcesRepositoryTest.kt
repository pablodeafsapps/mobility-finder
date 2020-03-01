package org.deafsapps.android.mobilityfinder.datalayer.repository

import arrow.core.Either
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.test.runBlockingTest
import org.deafsapps.android.mobilityfinder.datalayer.DatalayerContract
import org.deafsapps.android.mobilityfinder.datalayer.domain.boToDto
import org.deafsapps.android.mobilityfinder.domainlayer.DomainlayerContract
import org.deafsapps.android.mobilityfinder.domainlayer.domain.FailureBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceRequestBo
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.lang.IllegalStateException

class MobilityResourcesRepositoryTest {

    @Test
    fun `When 'fetchMobilityResourceList' is invoked, connection 'ok' -- the data-source method is invoked`() {
        // given
        val mockConnectivityDataSource: DatalayerContract.ConnectivityDataSource = mock {
            onBlocking { checkNetworkConnectionAvailability() }.doReturn(true)
        }
        val mockMobilityDataSource: DatalayerContract.MobilityDataSource = mock()
        val repository: DomainlayerContract.Datalayer.DataRepository =
            MobilityResourcesRepository.apply {
                connectivityDataSource = mockConnectivityDataSource
                mobilityDataSource = mockMobilityDataSource
            }
        runBlockingTest {
            // when
            repository.fetchMobilityResourceList(request = getDummyMobilityResourceRequestBo())
            // then
            verify((repository as MobilityResourcesRepository).mobilityDataSource)
                .fetchMobilityResourceListResponse(any())
        }
    }

    @Test
    fun `When 'fetchMobilityResourceList' is invoked, connection 'ko' -- 'NoConnection' error is returned`() {
        // given
        val mockConnectivityDataSource: DatalayerContract.ConnectivityDataSource = mock {
            onBlocking { checkNetworkConnectionAvailability() }.doReturn(false)
        }
        val mockMobilityDataSource: DatalayerContract.MobilityDataSource = mock()
        val repository: DomainlayerContract.Datalayer.DataRepository =
            MobilityResourcesRepository.apply {
                connectivityDataSource = mockConnectivityDataSource
                mobilityDataSource = mockMobilityDataSource
            }
        runBlockingTest {
            // when
            val actualResult = repository.fetchMobilityResourceList(request = getDummyMobilityResourceRequestBo())
            // then
            Assert.assertTrue(actualResult.isLeft() && (actualResult as Either.Left<FailureBo>).a is FailureBo.NoConnection)
        }
    }

    @Test
    fun `When 'fetchMobilityResourceList' is invoked, connection 'ok', data-source throws Exception -- 'Unknown' error is returned`() {
        // given
        val mockConnectivityDataSource: DatalayerContract.ConnectivityDataSource = mock {
            onBlocking { checkNetworkConnectionAvailability() }.doReturn(true)
        }
        val mockMobilityDataSource: DatalayerContract.MobilityDataSource = mock {
            onBlocking { fetchMobilityResourceListResponse(request = getDummyMobilityResourceRequestBo().boToDto()) }
                .doThrow(IllegalStateException())
        }
        val repository: DomainlayerContract.Datalayer.DataRepository =
            MobilityResourcesRepository.apply {
                connectivityDataSource = mockConnectivityDataSource
                mobilityDataSource = mockMobilityDataSource
            }
        runBlockingTest {
            // when
            val actualResult = repository.fetchMobilityResourceList(request = getDummyMobilityResourceRequestBo())
            // then
            Assert.assertTrue(actualResult.isLeft() && (actualResult as Either.Left<FailureBo>).a is FailureBo.Unknown)
        }
    }

    private fun getDummyMobilityResourceRequestBo() = MobilityResourceRequestBo()

}