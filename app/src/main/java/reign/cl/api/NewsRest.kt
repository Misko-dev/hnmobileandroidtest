package reign.cl.api

import reign.cl.model.JsonData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsRest {
    @GET("search_by_date")
    fun getNewsList(
        @Query("query") query: String?
    ): Call<JsonData>

}