package reign.cl.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    const val news_url = "http://hn.algolia.com/api/v1/"
    var retrofit = Retrofit.Builder()
        .baseUrl(news_url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}