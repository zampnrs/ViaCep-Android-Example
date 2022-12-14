package br.zampnrs.viacepapi_example.presentation.contact.view

import android.os.Bundle
import android.view.View

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import br.zampnrs.viacepapi_example.R
import br.zampnrs.viacepapi_example.databinding.FragmentContactBinding
import br.zampnrs.viacepapi_example.presentation.contact.viewmodel.ContactViewModel
import br.zampnrs.viacepapi_example.utils.BaseFragment
import br.zampnrs.viacepapi_example.utils.show
import br.zampnrs.viacepapi_example.utils.showToast

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ContactFragment : BaseFragment<FragmentContactBinding>(
    FragmentContactBinding::inflate
) {
    private val contactViewModel : ContactViewModel by viewModels()
    private val contactAdapter = ContactAdapter()

    override fun onResume() {
        super.onResume()

        contactViewModel.getAllContacts()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setClickListeners()
            recyclerContacts.adapter = contactAdapter
            recyclerContacts.layoutManager = LinearLayoutManager(requireContext())
            setUpViewStateFlow()
        }
        setAdapterActions()
    }

    private fun FragmentContactBinding.setClickListeners() {
        addNewContactFab.setOnClickListener {
            findNavController().navigate(
                ContactFragmentDirections.actionContactFragmentToNewContactFragment()
            )
        }
    }

    private fun setAdapterActions() {
        contactAdapter.apply {
            onSelectContact = ::onContactSelected
        }
    }

    private fun onContactSelected(contactId: Int) {
        findNavController().navigate(
            ContactFragmentDirections.actionContactFragmentToNewContactFragment()
                .setContactId(contactId)
        )
    }

    private fun FragmentContactBinding.setUpViewStateFlow() {
        lifecycleScope.launchWhenStarted {
            contactViewModel.viewStateFlow.collect { state ->
                when (state) {
                    is ContactViewModel.ViewState.Initial ->
                        progressBar.show(false)
                    is ContactViewModel.ViewState.Success -> {
                        progressBar.show(false)
                        handleContactLoadingSuccess()
                    }
                    is ContactViewModel.ViewState.Loading ->
                        progressBar.show(true)
                    is ContactViewModel.ViewState.Error ->
                        showToast(getString(R.string.contacts_loading_error_message))
                }
            }
        }
    }

    private fun FragmentContactBinding.handleContactLoadingSuccess() {
        contactViewModel.contactsList.let { list ->
            emptyContactBookTextView.show(list.isEmpty())
            contactAdapter.setList(list)
        }
    }

}