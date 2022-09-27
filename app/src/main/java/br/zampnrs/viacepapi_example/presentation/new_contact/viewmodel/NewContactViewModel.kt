package br.zampnrs.viacepapi_example.presentation.new_contact.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import br.zampnrs.viacepapi_example.data.db.ContactData
import br.zampnrs.viacepapi_example.data.db.ContactRepository
import br.zampnrs.viacepapi_example.data.network.responses.AddressResponse
import br.zampnrs.viacepapi_example.domain.AddressUseCase
import br.zampnrs.viacepapi_example.presentation.contact.viewmodel.ContactViewModel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewContactViewModel @Inject constructor(
    private val contactRepository: ContactRepository,
    private val addressUseCase: AddressUseCase
) : ViewModel() {

    sealed class ViewState {
        object InsertActionSuccess : ViewState()
        object InsertActionError : ViewState()
        object UpdateActionSuccess : ViewState()
        object UpdateActionError : ViewState()
        object DeleteActionSuccess : ViewState()
        object DeleteActionError : ViewState()
        class AddressLoadingSuccess(val address: AddressResponse) : ViewState()
        object AddressLoadingError : ViewState()
    }

    val mutableLiveData = MutableLiveData<ViewState>()

    fun getById(id: Int) = contactRepository.getById(id)

    fun insert(contact: ContactData) {
        try {
            contactRepository.insert(contact)
            mutableLiveData.postValue(ViewState.InsertActionSuccess)
        } catch (e: Exception) {
            mutableLiveData.postValue(ViewState.InsertActionError)
        }
    }

    fun update(contact: ContactData) {
        try {
            contactRepository.update(contact)
            mutableLiveData.postValue(ViewState.UpdateActionSuccess)
        } catch (e: Exception) {
            mutableLiveData.postValue(ViewState.UpdateActionError)
        }
    }

    fun deleteContact(id: Int) {
        try {
            contactRepository.delete(id)
            mutableLiveData.postValue(ViewState.DeleteActionSuccess)
        } catch (e: Exception) {
            mutableLiveData.postValue(ViewState.DeleteActionError)
        }
    }

    fun loadCep(cep: String) = viewModelScope.launch {
        try {
            addressUseCase.loadAddress(cep).also {
                mutableLiveData.postValue(ViewState.AddressLoadingSuccess(it))
            }
        } catch (e: Exception) {
            mutableLiveData.postValue(ViewState.AddressLoadingError)
        }
    }

}