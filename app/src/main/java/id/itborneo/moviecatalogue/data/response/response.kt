package id.itborneo.moviecatalogue.data.response

data class response(
    val page: Int,
    val results: List<result>,
    val total_pages: Int,
    val total_results: Int
)