package br.zampnrs.viacepapi_example.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "contact")
class ContactData (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "surname") val surname: String,
    @ColumnInfo(name = "country_code") val country_code: Int,
    @ColumnInfo(name = "area_code") val area_code: Int,
    @ColumnInfo(name = "phone_number") val phone_number: Int,
    @ColumnInfo(name = "cep") val cep: Int,
    @ColumnInfo(name = "street") val street: String,
    @ColumnInfo(name = "number") val number: Int,
    @ColumnInfo(name = "complement") val complement: String,
    @ColumnInfo(name = "neighborhood") val neighborhood: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "state") val state: String
)