package edu.uoc.android.rest

//import edu.uoc.android.rest.RetrofitFactory.API_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {
    val API_URL: String = "https://do.diba.cat"

    val museumAPI: MuseumService
        get() {
            val retrofit = Retrofit.Builder().baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
            return retrofit.create(MuseumService::class.java)
        }
}