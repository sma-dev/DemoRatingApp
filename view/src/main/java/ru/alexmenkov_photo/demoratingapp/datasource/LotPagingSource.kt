package ru.alexmenkov_photo.demoratingapp.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.alexmenkov_photo.demoratingapp.service.retrofit.LotServiceRestApi
import ru.alexmenkov_photo.demoratingapp.service.room.entity.Lot
import java.util.*
import javax.inject.Inject


class LotPagingSource @Inject constructor(val api: LotServiceRestApi) : PagingSource<Int, Lot>() {

    companion object {
        const val PAGE_SIZE = 10
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Lot> {
        return try {
            val currentPage = params.key
            val response = api.getLotPage(
                path = "0000.0000",
                pageNumber = currentPage ?: 0,
                pageSize = PAGE_SIZE,
                targetLang = Locale.getDefault().language
            )
            val result: List<Lot> = response.content.map {
                val image = if (!it.lotImageDtoList.isNullOrEmpty()) it.lotImageDtoList[0] else null
                // TODO add modelMapper
                Lot(
                    id = it.id ?: 0,
                    lotCode = it.lotCode ?: "",
                    lotTitle = it.baseGroupDto?.title?.requestedTranslation?.value ?: "",
                    lotDescription = it.baseGroupDto?.description?.requestedTranslation?.value
                        ?: "",
                    imageType = image?.type,
                    imageUrl = image?.url,
                    rating = it.rating ?: 0.0
                )
            }
            if (response.number == 0) {

            }
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
