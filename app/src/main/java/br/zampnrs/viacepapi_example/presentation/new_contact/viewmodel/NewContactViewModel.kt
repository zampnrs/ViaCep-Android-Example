package br.zampnrs.viacepapi_example.presentation.new_contact.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.zampnrs.viacepapi_example.data.db.ContactData
import br.zampnrs.viacepapi_example.data.db.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class NewContactViewModel @Inject constructor(
    private val contactRepository: ContactRepository
) : ViewModel() {

    sealed class ViewState {
        object InsertActionSuccess : ViewState()
        object InsertActionError : ViewState()
        object UpdateActionSuccess : ViewState()
        object UpdateActionError : ViewState()
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

}