package br.zampnrs.viacepapi_example.presentation.contact.view

import android.os.Bundle
import android.view.View

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import br.zampnrs.viacepapi_example.databinding.FragmentContactBinding
import br.zampnrs.viacepapi_example.presentation.contact.viewmodel.ContactViewModel
import br.zampnrs.viacepapi_example.utils.BaseFragment

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
            subscribeLiveData()
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

    private fun FragmentContactBinding.subscribeLiveData() {
        contactViewModel.mutableLiveData.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is ContactViewModel.ViewState.AllContactsLoadingSuccess ->
                    handleContactLoadingSuccess()
                else -> {}
            }
        })
    }

    private fun FragmentContactBinding.handleContactLoadingSuccess() {
        contactViewModel.contactsList.let { list ->
            emptyContactBookTextView.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            contactAdapter.setList(list)
        }
    }

}