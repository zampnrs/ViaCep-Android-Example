package br.zampnrs.viacepapi_example.domain

import br.zampnrs.viacepapi_example.data.network.AddressApi
import javax.inject.Inject


class AddressUseCase @Inject constructor(
    private val api: AddressApi
) {

    suspend fun loadAddress(cep: String) = api.loadAddress(cep)
}