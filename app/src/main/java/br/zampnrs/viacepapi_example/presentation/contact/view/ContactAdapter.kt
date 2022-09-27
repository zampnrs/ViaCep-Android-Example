package br.zampnrs.viacepapi_example.presentation.contact.view

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

import br.zampnrs.viacepapi_example.data.db.ContactData
import br.zampnrs.viacepapi_example.databinding.ItemContactBinding


class ContactAdapter(
    private var contactList: List<ContactData> = emptyList()
): RecyclerView.Adapter<ContactViewHolder>() {
    var onSelectContact: ((contactId: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(ItemContactBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contactList[position].name, contactList[position].surname)
        holder.binding.apply {
            contactNameTextView.setOnClickListener {
                onSelectContact?.invoke(contactList[position].id)
            }
        }
    }

    override fun getItemCount(): Int = contactList.size

    fun setList(contactDataList: List<ContactData>) {
        this.contactList = contactDataList
        notifyDataSetChanged()
    }

}