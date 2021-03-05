package ru.alexmenkov_photo.demoratingapp.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import dagger.Module
import dagger.Provides
import ru.alexmenkov_photo.demoratingapp.data.Page
import ru.alexmenkov_photo.demoratingapp.data.lot.BaseLotDto
import ru.alexmenkov_photo.demoratingapp.datasource.LotPagingSource
import ru.alexmenkov_photo.demoratingapp.datasource.PageKeyedRemoteMediator
import ru.alexmenkov_photo.demoratingapp.datasource.TestLotPagingSource
import ru.alexmenkov_photo.demoratingapp.service.room.RoomService
import ru.alexmenkov_photo.demoratingapp.service.room.entity.Lot
import javax.inject.Named


@Module
class PagingSourceModule {

    @Provides
    @MockData
    fun provideMockLotPagingSource(testLotPagingSource: TestLotPagingSource): PagingSource<Int, Lot> =
        testLotPagingSource

    @Provides
    @RealData
    fun provideRealLotPagingSource(lotPagingSource: LotPagingSource): PagingSource<Int, Lot> =
        lotPagingSource

    @Provides
    @Named("default")
    fun providePager(/*@MockData*/@RealData pagingSource: PagingSource<Int, Lot>): Pager<Int, Lot> {
        return Pager(
            config = PagingConfig(pageSize = LotPagingSource.PAGE_SIZE, enablePlaceholders = true),
            pagingSourceFactory = { pagingSource }
        )
    }

    @ExperimentalPagingApi
    @Provides
    @Named("mediator")
    fun provideCachedPager(room: RoomService): Pager<Int, Lot> {
        return Pager(
            config = PagingConfig(pageSize = LotPagingSource.PAGE_SIZE, enablePlaceholders = true),
            initialKey = 0,
            remoteMediator = PageKeyedRemoteMediator("dialog", room, { pageNumber, pageSize ->
                /*val bearer = "Bearer " + AuthService.getInstance(application)
                    .getActiveAccount().authResponseDto?.accessToken
                api.getLotPage(
                    bearer = bearer,
                    pageNumber = pageNumber,
                    pageSize = pageSize,
                    targetLang = Locale.getDefault().language
                )*/
                Page<BaseLotDto>()
            }, { loadType, page, tag, increment ->
                //RoomDialogService.saveBaseDialogDto(page, tag, loadType, increment, application)
            })
        ) {
            room.lotDao().basePagingSource()
        }
    }
}