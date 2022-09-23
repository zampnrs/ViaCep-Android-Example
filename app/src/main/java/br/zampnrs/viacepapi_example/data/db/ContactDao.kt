package br.zampnrs.viacepapi_example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact ORDER BY id DESC")
    fun getAll(): List<ContactData>

    @Query("SELECT * FROM contact WHERE id = :id")
    fun getById(id: Int) : ContactData

    @Insert
    fun insert(contact: ContactData)

    @Update
    fun update(contact: ContactData)

    @Query("DELETE FROM contact WHERE name = :name")
    fun delete(name: String)

}