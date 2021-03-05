package ru.alexmenkov_photo.demoratingapp.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.alexmenkov_photo.demoratingapp.data.Page
import ru.alexmenkov_photo.demoratingapp.service.room.entity.Lot
import javax.inject.Inject


class TestLotPagingSource @Inject constructor() : PagingSource<Int, Lot>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Lot> {
        return try {
            val currentPage = params.key
            val response = Page<Lot>()
            currentPage?.let {
                response.number = it
            }

            if (response.number == 0) {
                response.content =
                    listOf(
                        Lot(1, "100092"),
                        Lot(2, "100093"),
                        Lot(3, "100094"),
                        Lot(3, "100094"),
                        Lot(3, "100094"),
                        Lot(3, "100094"),
                        Lot(3, "100094"),
                        Lot(3, "100094"),
                        Lot(4, "100095")
                    )
            } else {
                response.content = listOf()
            }

            val result: List<Lot> = response.content

            LoadResult.Page(
                data = result,
                prevKey = null,
                nextKey = if (!result.isNullOrEmpty()) response.number + 1
                else null
            )

        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Lot>): Int? {
        return state.anchorPosition
    }
}
