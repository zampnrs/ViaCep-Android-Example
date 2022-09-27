package br.zampnrs.viacepapi_example.data.db

import javax.inject.Inject


class ContactRepository @Inject constructor(
    private val contactDao: ContactDao
) {
    suspend fun getAll(): List<ContactData> = contactDao.getAll()

    suspend fun getById(id: Int): ContactData = contactDao.getById(id)

    suspend fun insert(contact: ContactData) {
        contactDao.insert(contact)
    }

    suspend fun update(contact: ContactData) {
        contactDao.update(contact)
    }

    suspend fun delete(id: Int) {
        contactDao.delete(id)
    }
}