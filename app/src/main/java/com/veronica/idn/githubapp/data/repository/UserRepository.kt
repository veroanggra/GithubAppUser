package com.veronica.idn.githubapp.data.repository

import com.veronica.idn.githubapp.data.model.DetailUser
import com.veronica.idn.githubapp.data.model.User
import com.veronica.idn.githubapp.network.ApiResult
import com.veronica.idn.githubapp.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val api: ApiService) {
    suspend fun getUser(): Flow<ApiResult<List<User?>?>> {
        return flow {
            try {
                val data = api.getUser().items
                emit(ApiResult.Success(data))
            } catch (e: Throwable) {
                emit(ApiResult.Error(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getListSearchUser(username: String): Flow<ApiResult<List<User?>?>> {
        return flow {
            try {
                val data = api.getSearchUser(username)
                emit(ApiResult.Success(data.items))
            } catch (e: Throwable) {
                emit(ApiResult.Error(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailUser(username: String): Flow<ApiResult<DetailUser?>> {
        return flow {
            try {
                val data = api.getDetailUser(username)
                emit(ApiResult.Success(data))
            } catch (e: Throwable) {
                emit(ApiResult.Error(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFollowers(username: String): Flow<ApiResult<List<User?>?>> {
        return flow {
            try {
                val data = api.getFollowers(username)
                emit(ApiResult.Success(data))
            } catch (e: Throwable) {
                emit(ApiResult.Error(e))
            }
        }
    }
    suspend fun getFollowing(username: String): Flow<ApiResult<List<User?>?>> {
        return flow {
            try {
                val data = api.getFollowing(username)
                emit(ApiResult.Success(data))
            } catch (e: Throwable) {
                emit(ApiResult.Error(e))
            }
        }
    }
}