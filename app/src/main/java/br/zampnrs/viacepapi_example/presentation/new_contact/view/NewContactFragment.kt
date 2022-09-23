package br.zampnrs.viacepapi_example.presentation.new_contact.view

import android.os.Bundle
import android.view.View
import android.widget.Toast

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import br.zampnrs.viacepapi_example.R
import br.zampnrs.viacepapi_example.data.db.ContactData
import br.zampnrs.viacepapi_example.databinding.FragmentNewContactBinding
import br.zampnrs.viacepapi_example.presentation.new_contact.viewmodel.NewContactViewModel
import br.zampnrs.viacepapi_example.utils.BaseFragment
import br.zampnrs.viacepapi_example.utils.showToast

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewContactFragment : BaseFragment<FragmentNewContactBinding>(
    FragmentNewContactBinding::inflate
) {

    private val newContactViewModel: NewContactViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setClickListeners()
        }
        subscribeLiveData()
    }

    private fun FragmentNewContactBinding.setClickListeners() {
        backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        saveNewContactFab.setOnClickListener {
            if (checkEmptyFields()) {
                showToast(getString(R.string.save_contact_error), Toast.LENGTH_LONG)
            } else {
                newContactViewModel.insert(getAllFieldsData())
            }
        }
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
            complementEditText.text.isNullOrEmpty() ||
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

    private fun subscribeLiveData() {
        newContactViewModel.mutableLiveData.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is NewContactViewModel.ViewState.ContactLoadingSuccess -> {
                    TODO("Set contact information in the fields")
                }

                is NewContactViewModel.ViewState.DbActionSuccess -> {
                    showToast(getString(R.string.contact_saved_message))
                    findNavController().popBackStack()
                }

                is NewContactViewModel.ViewState.DbActionError ->
                    showToast(getString(R.string.contact_saving_error_message))
            }
        })
    }
}