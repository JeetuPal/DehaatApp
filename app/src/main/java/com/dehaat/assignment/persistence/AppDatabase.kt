package com.dehaat.assignment.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dehaat.assignment.models.AccountProperties
import com.dehaat.assignment.models.AuthToken
import com.dehaat.assignment.models.BlogPost
import com.dehaat.assignment.util.Converters

@Database(entities = [AuthToken::class, AccountProperties::class, BlogPost::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getAuthTokenDao(): AuthTokenDao

    abstract fun getAccountPropertiesDao(): AccountPropertiesDao

    abstract fun getBlogPostDao(): BlogPostDao

    companion object{
        val DATABASE_NAME: String = "app_db"
    }


}








