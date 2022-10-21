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

        object ContactLoadingByIdSuccess : ViewState()
        object ContactLoadingByIdError : ViewState()
        object InsertActionSuccess : ViewState()
        object InsertActionError : ViewState()
        object UpdateActionSuccess : ViewState()
        object UpdateActionError : ViewState()
        object DeleteActionSuccess : ViewState()
        object DeleteActionError : ViewState()

        object AddressLoadingSuccess : ViewState()
        object AddressLoadingError : ViewState()
    }

    private val _viewStateFlow = MutableStateFlow<ViewState>(ViewState.Initial)
    val viewStateFlow: StateFlow<ViewState> get() = _viewStateFlow

    fun getById(id: Int) = viewModelScope.launch {
        try {
            contactRepository.getById(id).also {
                contact = it
                _viewStateFlow.value = ViewState.ContactLoadingByIdSuccess
            }
        } catch (e: Exception) {
            _viewStateFlow.value = ViewState.ContactLoadingByIdError
        }
    }

    fun insert(contact: ContactData) = viewModelScope.launch {
        try {
            contactRepository.insert(contact)
            _viewStateFlow.value = ViewState.InsertActionSuccess
        } catch (e: Exception) {
            _viewStateFlow.value = ViewState.InsertActionError
        }
    }

    fun update(contact: ContactData, contactId: Int) = viewModelScope.launch {
        try {
            contact.id = contactId
            contactRepository.update(contact)
            _viewStateFlow.value = ViewState.UpdateActionSuccess
        } catch (e: Exception) {
            _viewStateFlow.value = ViewState.UpdateActionError
        }
    }

    fun deleteContact(id: Int) = viewModelScope.launch {
        try {
            contactRepository.delete(id)
            _viewStateFlow.value = ViewState.DeleteActionSuccess
        } catch (e: Exception) {
            _viewStateFlow.value = ViewState.DeleteActionError
        }
    }

    fun loadCep(cep: String) = viewModelScope.launch {
        try {
            addressUseCase.loadAddress(cep).also {
                address = it
                _viewStateFlow.value = ViewState.AddressLoadingSuccess
            }
        } catch (e: Exception) {
            _viewStateFlow.value = ViewState.AddressLoadingError
        }
    }

}