package br.zampnrs.viacepapi_example.presentation.contact.view

import androidx.recyclerview.widget.RecyclerView
import br.zampnrs.viacepapi_example.databinding.ItemContactBinding


class ContactViewHolder(
    val binding: ItemContactBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(contactName : String) {
        binding.contactNameTextView.text = contactName
    }

}