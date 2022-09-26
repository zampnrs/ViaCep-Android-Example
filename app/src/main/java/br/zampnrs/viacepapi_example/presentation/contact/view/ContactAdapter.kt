package br.zampnrs.viacepapi_example.presentation.contact.view

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

import br.zampnrs.viacepapi_example.data.db.ContactData
import br.zampnrs.viacepapi_example.databinding.ItemContactBinding


class ContactAdapter(
    private var contactList: List<ContactData> = emptyList()
): RecyclerView.Adapter<ContactViewHolder>() {
    var onSelectContact: ((contactId: Int, edit: Boolean) -> Unit)? = null
    var onDeleteContact: ((contactName: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(ItemContactBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contactList[position].name)
        holder.binding.apply {
            contactNameTextView.setOnClickListener {
                onSelectContact?.invoke(contactList[position].id, false)
            }
            editContactButton.setOnClickListener {
                onSelectContact?.invoke(contactList[position].id, true)
            }
            deleteContactButton.setOnClickListener {
                onDeleteContact?.invoke(contactList[position].name)
            }
        }
    }

    override fun getItemCount(): Int = contactList.size

    fun setList(contactDataList: List<ContactData>) {
        this.contactList = contactDataList
        notifyDataSetChanged()
    }

}