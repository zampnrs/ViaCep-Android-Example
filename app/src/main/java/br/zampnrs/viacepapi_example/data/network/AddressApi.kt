package br.zampnrs.viacepapi_example.data.network

import br.zampnrs.viacepapi_example.data.network.responses.AddressResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface AddressApi {
    @GET("{cep}/json")
    suspend fun loadAddress(
        @Path("cep") cep: String
    ): AddressResponse
}