package br.zampnrs.viacepapi_example.data.db

import javax.inject.Inject


class ContactRepository @Inject constructor(
    private val contactDao: ContactDao
) {
    fun getAll(): List<ContactData> = contactDao.getAll()

    fun getById(id: Int): ContactData = contactDao.getById(id)

    fun insert(contact: ContactData) {
        contactDao.insert(contact)
    }

    fun update(contact: ContactData) {
        contactDao.update(contact)
    }

    fun delete(id: Int) {
        contactDao.delete(id)
    }
}