package com.veronica.idn.githubapp.data.repository

import android.app.Application
import com.veronica.idn.githubapp.data.db.FavoriteDB
import com.veronica.idn.githubapp.data.db.FavoriteDao
import com.veronica.idn.githubapp.data.model.DetailUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepository @Inject constructor(application: Application) {
    private val favoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDB.getDatabase(application)
        favoriteDao = db.favoriteDao()
    }

    fun getFavoriteUser(): kotlinx.coroutines.flow.Flow<List<DetailUser>> {
        return flow {
            val favoriteUser = favoriteDao.getFavorites()
            emit(favoriteUser)
        }.flowOn(Dispatchers.IO)
    }

    fun insert(favoriteUser: DetailUser) {
        executorService.execute { favoriteDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: DetailUser) {
        executorService.execute { favoriteDao.delete(favoriteUser) }
    }
}