package com.veronica.idn.githubapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.veronica.idn.githubapp.data.model.DetailUser

@Database(entities = [DetailUser::class], version = 1)
abstract class FavoriteDB : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        var instance: FavoriteDB? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteDB {
            if (instance == null) {
                synchronized(FavoriteDB::class.java) {
                    instance = Room.databaseBuilder(
                        context.applicationContext, FavoriteDB::class.java, "favorite_users"
                    ).build()
                }
            }
            return instance as FavoriteDB
        }
    }
}