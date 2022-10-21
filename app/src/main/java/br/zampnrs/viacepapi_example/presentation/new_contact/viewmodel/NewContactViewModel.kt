package br.zampnrs.viacepapi_example.presentation.new_contact.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import br.zampnrs.viacepapi_example.data.db.ContactData
import br.zampnrs.viacepapi_example.data.db.ContactRepository
import br.zampnrs.viacepapi_example.data.network.responses.AddressResponse
import br.zampnrs.viacepapi_example.domain.AddressUseCase

import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import javax.inject.Inject


@HiltViewModel
class NewContactViewModel @Inject constructor(
    private val contactRepository: ContactRepository,
    private val addressUseCase: AddressUseCase
) : ViewModel() {

    var contact : ContactData? = null
    var address: AddressResponse? = null

    sealed class ViewState {
        object Initial : ViewState()
        object Loading : ViewState()

        object ContactByIdSuccess : ViewState()
        object InsertSuccess : ViewState()
        object UpdateSuccess : ViewState()
        object DeleteSuccess : ViewState()
        object AddressSuccess : ViewState()

        object ContactByIdError : ViewState()
        object InsertError : ViewState()
        object UpdateError : ViewState()
        object DeleteError : ViewState()
        object AddressError : ViewState()
    }

    private val _viewStateFlow = MutableStateFlow<ViewState>(ViewState.Initial)
    val viewStateFlow: StateFlow<ViewState> get() = _viewStateFlow

    fun getById(id: Int) = viewModelScope.launch {
        _viewStateFlow.value = ViewState.Loading

        try {
            contactRepository.getById(id).also {
                contact = it
                _viewStateFlow.value = ViewState.ContactByIdSuccess
            }
        } catch (e: Exception) {
            _viewStateFlow.value = ViewState.ContactByIdError
        }
    }

    fun insert(contact: ContactData) = viewModelScope.launch {
        _viewStateFlow.value = ViewState.Loading

        try {
            contactRepository.insert(contact)
            _viewStateFlow.value = ViewState.InsertSuccess
        } catch (e: Exception) {
            _viewStateFlow.value = ViewState.InsertError
        }
    }

    fun update(contact: ContactData, contactId: Int) = viewModelScope.launch {
        _viewStateFlow.value = ViewState.Loading

        try {
            contact.id = contactId
            contactRepository.update(contact)
            _viewStateFlow.value = ViewState.UpdateSuccess
        } catch (e: Exception) {
            _viewStateFlow.value = ViewState.UpdateError
        }
    }

    fun deleteContact(id: Int) = viewModelScope.launch {
        _viewStateFlow.value = ViewState.Loading

        try {
            contactRepository.delete(id)
            _viewStateFlow.value = ViewState.DeleteSuccess
        } catch (e: Exception) {
            _viewStateFlow.value = ViewState.DeleteError
        }
    }

    fun loadCep(cep: String) = viewModelScope.launch {
        _viewStateFlow.value = ViewState.Loading

        try {
            addressUseCase.loadAddress(cep).also {
                address = it
                _viewStateFlow.value = ViewState.AddressSuccess
            }
        } catch (e: Exception) {
            _viewStateFlow.value = ViewState.AddressError
        }
    }

}