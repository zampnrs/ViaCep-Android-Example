package br.zampnrs.viacepapi_example.data.di

import android.app.Application

import br.zampnrs.viacepapi_example.data.db.ContactDao
import br.zampnrs.viacepapi_example.data.db.ContactDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Singleton
    @Provides
    fun getAppDatabase(context: Application): ContactDatabase {
        return ContactDatabase.getAppDbInstance(context)
    }

    @Singleton
    @Provides
    fun appDao(appDatabase: ContactDatabase): ContactDao {
        return appDatabase.getContactDao()
    }

}