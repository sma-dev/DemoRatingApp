package ru.alexmenkov_photo.demoratingapp.data

data class Page<T>(
    var content: List<T> = ArrayList(),
    var pageable: Pageable? = null,
    var totalPages: Int = 0,
    var totalElements: Int = 0,
    var last: Boolean = false,
    var first: Boolean = true,
    var sort: Sort? = null,
    var numberOfElements: Int = 0,
    var size: Int = 0,
    var number: Int = 0,
    var empty: Boolean = true

) {

    class Pageable(
        var sort: Sort,
        var pageNumber: Int,
        var pageSize: Int,
        var offset: Int,
        var unpaged: Boolean,
        var paged: Boolean
    )

    class Sort(
        var sorted: Boolean,
        var unsorted: Boolean,
        var empty: Boolean
    )
}