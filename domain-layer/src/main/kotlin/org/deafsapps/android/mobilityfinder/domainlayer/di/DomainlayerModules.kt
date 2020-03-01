package org.deafsapps.android.mobilityfinder.domainlayer.di

import dagger.Binds
import dagger.Module
import org.deafsapps.android.mobilityfinder.domainlayer.DomainlayerContract
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceBo
import org.deafsapps.android.mobilityfinder.domainlayer.domain.MobilityResourceRequestBo
import org.deafsapps.android.mobilityfinder.domainlayer.feature.main.MAIN_DOMAIN_BRIDGE_TAG
import org.deafsapps.android.mobilityfinder.domainlayer.feature.main.MainDomainLayerBridge
import org.deafsapps.android.mobilityfinder.domainlayer.feature.main.MainDomainLayerBridgeImpl
import org.deafsapps.android.mobilityfinder.domainlayer.usecase.FETCH_MOBILITY_RESOURCE_LIST_UC_TAG
import org.deafsapps.android.mobilityfinder.domainlayer.usecase.FetchMobilityResourceListUc
import javax.inject.Named

@Module
abstract class DomainlayerAbstractModule {

    @Binds
    @Named(MAIN_DOMAIN_BRIDGE_TAG)
    abstract fun provideMainDomainLayerBridge(bridge: MainDomainLayerBridgeImpl): MainDomainLayerBridge

    @Binds
    @Named(FETCH_MOBILITY_RESOURCE_LIST_UC_TAG)
    abstract fun provideFetchMobilityResourceListUc(uc: FetchMobilityResourceListUc): @JvmSuppressWildcards DomainlayerContract.Presentationlayer.UseCase<MobilityResourceRequestBo, List<@JvmSuppressWildcards MobilityResourceBo>>

}