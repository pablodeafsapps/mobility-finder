package org.deafsapps.android.mobilityfinder.datalayer.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.deafsapps.android.mobilityfinder.datalayer.BuildConfig
import org.deafsapps.android.mobilityfinder.datalayer.DatalayerContract
import org.deafsapps.android.mobilityfinder.datalayer.DatalayerContract.Companion.CONNECTIVITY_DATA_SOURCE_TAG
import org.deafsapps.android.mobilityfinder.datalayer.DatalayerContract.Companion.MOBILITY_RESOURCES_DATA_SOURCE_TAG
import org.deafsapps.android.mobilityfinder.datalayer.datasource.AndroidDataSource
import org.deafsapps.android.mobilityfinder.datalayer.datasource.MobilityResourcesDataSource
import org.deafsapps.android.mobilityfinder.datalayer.repository.DataRepository
import org.deafsapps.android.mobilityfinder.domainlayer.DomainlayerContract
import org.deafsapps.android.mobilityfinder.domainlayer.DomainlayerContract.Datalayer.Companion.MOBILITY_RESOURCES_REPOSITORY_TAG
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class DatalayerAbstractModule {

    @Binds
    @Named(MOBILITY_RESOURCES_DATA_SOURCE_TAG)
    abstract fun provideMobilityResourcesDataSource(ds: MobilityResourcesDataSource): DatalayerContract.MobilityResourcesDataSource

    @Binds
    @Named(CONNECTIVITY_DATA_SOURCE_TAG)
    abstract fun provideConnectivityDataSource(ds: AndroidDataSource): DatalayerContract.ConnectivityDataSource

}

@Module
class DatalayerModule {

    @Provides
    @Named(MOBILITY_RESOURCES_REPOSITORY_TAG)
    fun provideMobilityResourceRepository(
        @Named(CONNECTIVITY_DATA_SOURCE_TAG)
        connectivityDs: DatalayerContract.ConnectivityDataSource,
        @Named(MOBILITY_RESOURCES_DATA_SOURCE_TAG)
        mobilityResourcesDs: DatalayerContract.MobilityResourcesDataSource
    ): DomainlayerContract.Datalayer.MobilityResourcesRepository =
        DataRepository.apply {
            connectivityDataSource = connectivityDs
            mobilityResourcesDataSource = mobilityResourcesDs
        }

    @Provides
    @Singleton
    fun provideRetrofitInstance(): Retrofit = Retrofit.Builder()
        .client(provideHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BuildConfig.API_URL)
        .build()

}

fun provideHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
        interceptor.level = HttpLoggingInterceptor.Level.NONE
    }
    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()
}