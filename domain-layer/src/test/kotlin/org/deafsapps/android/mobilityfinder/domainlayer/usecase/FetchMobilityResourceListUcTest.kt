package org.deafsapps.android.mobilityfinder.domainlayer.usecase

import arrow.core.Either
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.test.runBlockingTest
import org.deafsapps.android.mobilityfinder.domainlayer.DomainlayerContract
import org.deafsapps.android.mobilityfinder.domainlayer.domain.FailureBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceRequestBo
import org.junit.Assert.assertTrue
import org.junit.Test

class FetchMobilityResourceListUcTest {

    private lateinit var useCase: DomainlayerContract.Presentationlayer.UseCase<MobilityResourceRequestBo, List<@JvmSuppressWildcards MobilityResourceBo>>

    @Test
    fun `When use-case is invoked, 'params' not null -- repository method is invoked`() {
        // given
        val mockDataRepository: DomainlayerContract.Datalayer.DataRepository = mock()
        useCase = FetchMobilityResourceListUc(dataRepository = mockDataRepository)
        // when
        runBlockingTest {
            useCase.run(params = getDummyMobilityResourceRequestBo())
            // then
            verify(mockDataRepository).fetchMobilityResourceList(any())
        }
    }

    @Test
    fun `When use-case is invoked, 'params' null -- 'InputParamsError' error is returned`() {
        // given
        val mockDataRepository: DomainlayerContract.Datalayer.DataRepository = mock()
        useCase = FetchMobilityResourceListUc(dataRepository = mockDataRepository)
        // when
        runBlockingTest {
            val actualResult = useCase.run(params = null)
            // then
            assertTrue(actualResult.isLeft() && (actualResult as Either.Left<FailureBo>).a is FailureBo.InputParamsError)
        }
    }

    private fun getDummyMobilityResourceRequestBo() = MobilityResourceRequestBo()

}