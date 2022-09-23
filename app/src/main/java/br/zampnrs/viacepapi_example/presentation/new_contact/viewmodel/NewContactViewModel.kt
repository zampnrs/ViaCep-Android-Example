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
        object ContactLoadingSuccess: ViewState()
        object DbActionSuccess : ViewState()
        class DbActionError(errorMessage: String?): ViewState()
    }

    val mutableLiveData = MutableLiveData<ViewState>()

    private var contactsList: List<ContactData> = emptyList()

    fun getAll() {
        contactRepository.getAll().also {
            contactsList = it
            mutableLiveData.postValue(ViewState.ContactLoadingSuccess)
        }
    }

    fun insert(contact: ContactData) {
        try {
            contactRepository.insert(contact)
            mutableLiveData.postValue(ViewState.DbActionSuccess)
        } catch (e: Exception) {
            mutableLiveData.postValue(ViewState.DbActionError(e.message))
        }
    }

    fun update(contact: ContactData) {
        try {
            contactRepository.update(contact)
            mutableLiveData.postValue(ViewState.DbActionSuccess)
        } catch (e: Exception) {
            mutableLiveData.postValue(ViewState.DbActionError(e.message))
        }
    }

    fun delete(name: String) {
        try {
            contactRepository.delete(name)
            mutableLiveData.postValue(ViewState.DbActionSuccess)
        } catch (e: Exception) {
            mutableLiveData.postValue(ViewState.DbActionError(e.message))
        }
    }

}