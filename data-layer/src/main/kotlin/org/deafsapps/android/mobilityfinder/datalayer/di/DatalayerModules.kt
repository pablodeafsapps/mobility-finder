package org.deafsapps.android.mobilityfinder.datalayer.di

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.deafsapps.android.mobilityfinder.datalayer.BuildConfig
import org.deafsapps.android.mobilityfinder.datalayer.DatalayerContract
import org.deafsapps.android.mobilityfinder.datalayer.datasource.AndroidDataSource
import org.deafsapps.android.mobilityfinder.datalayer.datasource.MobilityResourcesDataSource
import org.deafsapps.android.mobilityfinder.datalayer.repository.DataRepository
import org.deafsapps.android.mobilityfinder.domainlayer.DomainlayerContract
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DatalayerModule() {

    @Provides
    fun provideMobilityResourceRepository(
        connectivityDs: DatalayerContract.ConnectivityDataSource,
        mobilityResourcesDs: DatalayerContract.MobilityResourcesDataSource
    ): DomainlayerContract.Datalayer.MobilityResourcesRepository =
        DataRepository.apply {
            connectivityDataSource = connectivityDs
            mobilityResourcesDataSource = mobilityResourcesDs
        }

    @Provides
    fun provideConnectivityDataSource(ctx: Context): DatalayerContract.ConnectivityDataSource =
        AndroidDataSource(context = ctx)

    @Provides
    fun provideMobilityResourcesDataSource(rfitInstance: Retrofit): DatalayerContract.MobilityResourcesDataSource =
        MobilityResourcesDataSource(retrofitInstance = rfitInstance)

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