package edu.uoc.android.rest

//import edu.uoc.android.rest.models.Museums

import edu.uoc.android.Museums
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query




interface MuseumService {
    @GET("/api/dataset/museus/format/json/pag-ini/{pagIni}/pag-fi/{pagFi}")
    fun museums(@Query("pagIni") pagIni: String, @Query("pagFi") pagFi: String): Call<Museums>
}

