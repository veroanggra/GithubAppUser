package com.veronica.idn.githubapp.data.db

import androidx.room.*
import com.veronica.idn.githubapp.data.model.DetailUser

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(detailUser: DetailUser)

    @Delete
    fun delete(detailUser: DetailUser)

    @Query("SELECT * from detailuser")
    fun getFavorites(): List<DetailUser>
}