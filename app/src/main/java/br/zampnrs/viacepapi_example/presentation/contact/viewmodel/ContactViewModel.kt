package br.zampnrs.viacepapi_example.presentation.contact.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import br.zampnrs.viacepapi_example.data.db.ContactData
import br.zampnrs.viacepapi_example.data.db.ContactRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ContactViewModel @Inject constructor(
    private val contactRepository: ContactRepository
) : ViewModel()
{
    sealed class ViewState {
        object AllContactsLoadingSuccess: ViewState()

    }

    val mutableLiveData = MutableLiveData<ViewState>()

    var contactsList: List<ContactData> = emptyList()

    fun getAllContacts() {
        contactRepository.getAll().also {
            contactsList = it
            mutableLiveData.postValue(ViewState.AllContactsLoadingSuccess)
        }
    }

}