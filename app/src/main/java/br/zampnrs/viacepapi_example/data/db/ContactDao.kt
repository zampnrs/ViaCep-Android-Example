package br.zampnrs.viacepapi_example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact ORDER BY id DESC")
    suspend fun getAll(): List<ContactData>

    @Query("SELECT * FROM contact WHERE id = :id")
    suspend fun getById(id: Int) : ContactData

    @Insert
    suspend fun insert(contact: ContactData)

    @Update
    suspend fun update(contact: ContactData)

    @Query("DELETE FROM contact WHERE id = :id")
    suspend fun delete(id: Int)

}