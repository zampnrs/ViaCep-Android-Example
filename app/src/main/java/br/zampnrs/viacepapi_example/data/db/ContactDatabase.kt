package br.zampnrs.viacepapi_example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [ContactData::class], version = 1, exportSchema = false)
abstract class ContactDatabase: RoomDatabase() {

    abstract fun getContactDao(): ContactDao

    companion object {
        private var DB_INSTANCE: ContactDatabase? = null

        fun getAppDbInstance(context: Context): ContactDatabase {
            if(DB_INSTANCE == null) {
                DB_INSTANCE = Room.databaseBuilder(
                    context.applicationContext, ContactDatabase::class.java, "contactDb"
                ).build()
            }
            return DB_INSTANCE!!
        }

    }
}