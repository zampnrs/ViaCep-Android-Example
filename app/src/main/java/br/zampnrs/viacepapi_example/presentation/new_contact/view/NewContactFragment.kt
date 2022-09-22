package br.zampnrs.viacepapi_example.presentation.new_contact.view

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import br.zampnrs.viacepapi_example.databinding.FragmentNewContactBinding
import br.zampnrs.viacepapi_example.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewContactFragment : BaseFragment<FragmentNewContactBinding>(
    FragmentNewContactBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBackButton()
    }

    private fun setBackButton() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}