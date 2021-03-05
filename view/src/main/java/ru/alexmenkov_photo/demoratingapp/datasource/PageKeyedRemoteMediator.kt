package ru.alexmenkov_photo.demoratingapp.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import retrofit2.HttpException
import ru.alexmenkov_photo.demoratingapp.data.Page
import ru.alexmenkov_photo.demoratingapp.service.room.RoomService
import java.io.IOException


@ExperimentalPagingApi
class PageKeyedRemoteMediator<T : Any, R : Any>(
    private val tag: String,
    private val database: RoomService,
    private val loadRemote: suspend (page: Int, loadSize: Int) -> Page<R>,
    private val refreshCache: suspend (loadType: LoadType, page: Page<R>, tag: String, increment: Int) -> Unit
) : RemoteMediator<Int, T>() {

    private val remoteKeyDao = database.remoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, T>
    ): MediatorResult {
        try {
            var increment = 1

            // Get the closest item from PagingState that we want to load data around.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    increment = state.config.initialLoadSize / state.config.pageSize
                    0
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao?.getRemoteKeyByTag(tag)
                    }

                    if (remoteKey?.nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey.nextKey
                }
            }

            val data = loadRemote.invoke(
                loadKey, when (loadType) {
                    LoadType.REFRESH -> state.config.initialLoadSize
                    else -> state.config.pageSize
                }
            )

            val items = data.content

            database.withTransaction {
                refreshCache.invoke(loadType, data, tag, increment)
            }

            return MediatorResult.Success(endOfPaginationReached = items.isEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}