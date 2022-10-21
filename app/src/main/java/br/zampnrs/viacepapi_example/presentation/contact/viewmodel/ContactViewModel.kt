package br.zampnrs.viacepapi_example.presentation.contact.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import br.zampnrs.viacepapi_example.data.db.ContactData
import br.zampnrs.viacepapi_example.data.db.ContactRepository

import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import javax.inject.Inject


@HiltViewModel
class ContactViewModel @Inject constructor(
    private val contactRepository: ContactRepository
) : ViewModel()
{
    sealed class ViewState {
        object Initial : ViewState()
        object Loading : ViewState()
        object Success : ViewState()
        object Error : ViewState()
    }

    private val _viewStateFlow = MutableStateFlow<ViewState>(ViewState.Initial)
    val viewStateFlow: StateFlow<ViewState> get() = _viewStateFlow

    var contactsList: List<ContactData> = emptyList()

    fun getAllContacts() = viewModelScope.launch {
        _viewStateFlow.value = ViewState.Loading

        try {
            contactRepository.getAll().also {
                contactsList = it
                _viewStateFlow.value = ViewState.Success
            }
        } catch (e: Exception) {
            _viewStateFlow.value = ViewState.Error
        }

    }

}