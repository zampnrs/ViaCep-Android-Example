package br.zampnrs.viacepapi_example.data.di

import br.zampnrs.viacepapi_example.data.network.AddressApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    val BASE_URL = "https://viacep.com.br/ws/"

    @Provides
    @Singleton
    fun getAddressApi(retrofit: Retrofit): AddressApi {
        return retrofit.create(AddressApi::class.java)
    }

    @Provides
    @Singleton
    fun getRetroInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}
