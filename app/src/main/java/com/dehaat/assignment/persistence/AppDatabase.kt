package com.dehaat.assignment.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dehaat.assignment.models.AccountProperties
import com.dehaat.assignment.models.AuthToken
import com.dehaat.assignment.models.BlogPost

@Database(entities = [AuthToken::class, AccountProperties::class, BlogPost::class], version = 2)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getAuthTokenDao(): AuthTokenDao

    abstract fun getAccountPropertiesDao(): AccountPropertiesDao

    abstract fun getBlogPostDao(): BlogPostDao

    companion object{
        val DATABASE_NAME: String = "app_db"
    }


}








