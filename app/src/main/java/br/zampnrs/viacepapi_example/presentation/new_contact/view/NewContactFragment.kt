package br.zampnrs.viacepapi_example.presentation.new_contact.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import br.zampnrs.viacepapi_example.R
import br.zampnrs.viacepapi_example.data.db.ContactData
import br.zampnrs.viacepapi_example.data.network.responses.AddressResponse
import br.zampnrs.viacepapi_example.databinding.FragmentNewContactBinding
import br.zampnrs.viacepapi_example.presentation.new_contact.viewmodel.NewContactViewModel
import br.zampnrs.viacepapi_example.utils.BaseFragment
import br.zampnrs.viacepapi_example.utils.Constants
import br.zampnrs.viacepapi_example.utils.showToast

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewContactFragment : BaseFragment<FragmentNewContactBinding>(
    FragmentNewContactBinding::inflate
) {

    private val newContactViewModel: NewContactViewModel by viewModels()
    private val args: NewContactFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setClickListeners()
            setTextChangeListener()
            subscribeLiveData()
            if (args.contactId != Constants.DEFAULT_CONTACT_ID) {
                newContactViewModel.getById(args.contactId)
            }
        }
    }

    private fun FragmentNewContactBinding.setClickListeners() {
        backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        deleteButton.setOnClickListener {
            showAlert(getString(R.string.alert_title), getString(R.string.delete_message))
        }

        saveNewContactFab.setOnClickListener {
            if (checkEmptyFields()) {
                showToast(getString(R.string.save_contact_error), Toast.LENGTH_LONG)
            } else {
                handleContactActions()
            }
        }
    }

    private fun FragmentNewContactBinding.handleContactActions() {
        getAllFieldsData().also {
            if (args.contactId == Constants.DEFAULT_CONTACT_ID) {
                newContactViewModel.insert(it)
            } else {
                newContactViewModel.update(it, args.contactId)
            }
        }
    }

    private fun showAlert(alertTitle : String, alertMessage : String) {
        val builder = activity?.let{ AlertDialog.Builder(it) }
        builder?.setMessage(alertMessage)
        builder?.setTitle(alertTitle)
        builder?.setPositiveButton(getString(R.string.delete_action)) { dialog, _ ->
            dialog.dismiss()
            newContactViewModel.deleteContact(args.contactId)
        }
        builder?.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = builder?.create()
        alertDialog?.show()
    }

    private fun FragmentNewContactBinding.setTextChangeListener() {
        cepEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0.toString().let {
                    if (it.length == Constants.CEP_LENGTH) {
                        progressBar.visibility = View.VISIBLE
                        newContactViewModel.loadCep(it)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun FragmentNewContactBinding.fillAllFields(contact : ContactData) {
        nameEditText.setText(contact.name)
        surnameEditText.setText(contact.surname)
        countryCodeEditText.setText(contact.country_code.toString())
        areaCodeEditText.setText(contact.area_code.toString())
        phoneNumberEditText.setText(contact.phone_number.toString())
        cepEditText.setText(contact.cep.toString())
        streetEditText.setText(contact.street)
        numberEditText.setText(contact.number.toString())
        complementEditText.setText(contact.complement)
        neighborhoodEditText.setText(contact.neighborhood)
        cityEditText.setText(contact.city)
        ufEditText.setText(contact.state)
    }

    private fun FragmentNewContactBinding.checkEmptyFields(): Boolean =
        nameEditText.text.isNullOrEmpty() ||
            surnameEditText.text.isNullOrEmpty() ||
            countryCodeEditText.text.isNullOrEmpty() ||
            areaCodeEditText.text.isNullOrEmpty() ||
            phoneNumberEditText.text.isNullOrEmpty() ||
            cepEditText.text.isNullOrEmpty() ||
            streetEditText.text.isNullOrEmpty() ||
            numberEditText.text.isNullOrEmpty() ||
            neighborhoodEditText.text.isNullOrEmpty() ||
            cityEditText.text.isNullOrEmpty() ||
            ufEditText.text.isNullOrEmpty()

    private fun FragmentNewContactBinding.getAllFieldsData(): ContactData = ContactData(
        name = nameEditText.text.toString(),
        surname = surnameEditText.text.toString(),
        country_code = countryCodeEditText.text.toString().toInt(),
        area_code = areaCodeEditText.text.toString().toInt(),
        phone_number = phoneNumberEditText.text.toString().toInt(),
        cep = cepEditText.text.toString().toInt(),
        street = streetEditText.text.toString(),
        number = numberEditText.text.toString().toInt(),
        complement = complementEditText.text.toString(),
        neighborhood = neighborhoodEditText.text.toString(),
        city = cityEditText.text.toString(),
        state = ufEditText.text.toString()
    )

    private fun FragmentNewContactBinding.subscribeLiveData() {
        newContactViewModel.mutableLiveData.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is NewContactViewModel.ViewState.ContactLoadingByIdSuccess ->
                    fillAllFields(state.contact)

                is NewContactViewModel.ViewState.ContactLoadingByIdError -> {
                    showToast(getString(R.string.error_loading_contact))
                    findNavController().popBackStack()
                }

                is NewContactViewModel.ViewState.InsertActionSuccess -> {
                    showToast(getString(R.string.contact_saved_message))
                    findNavController().popBackStack()
                }

                is NewContactViewModel.ViewState.InsertActionError ->
                    showToast(getString(R.string.contact_saving_error_message))

                is NewContactViewModel.ViewState.UpdateActionSuccess -> {
                    showToast(getString(R.string.contact_updated_message))
                    findNavController().popBackStack()
                }

                is NewContactViewModel.ViewState.UpdateActionError ->
                    showToast(getString(R.string.contact_updating_error_message))

                is NewContactViewModel.ViewState.DeleteActionSuccess ->
                    findNavController().popBackStack()

                is NewContactViewModel.ViewState.DeleteActionError ->
                    showToast(getString(R.string.contact_deleting_error_message))

                is NewContactViewModel.ViewState.AddressLoadingSuccess -> {
                    progressBar.visibility = View.GONE
                    fillAddressFields(state.address)
                }

                is NewContactViewModel.ViewState.AddressLoadingError -> {
                    progressBar.visibility = View.GONE
                    showToast(getString(R.string.wrong_zip_code))
                }
            }
        })
    }

    private fun FragmentNewContactBinding.fillAddressFields(address: AddressResponse) {
        streetEditText.setText(address.logradouro)
        neighborhoodEditText.setText(address.bairro)
        cityEditText.setText(address.localidade)
        ufEditText.setText(address.uf)
    }
}