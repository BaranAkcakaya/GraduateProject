package com.egemeninceler.donempro.Network

import com.egemeninceler.donempro.DetailActivity
import com.egemeninceler.donempro.Model.Crime
import com.egemeninceler.donempro.Model.Healtcare
import com.egemeninceler.donempro.Model.Numbeo
import com.egemeninceler.donempro.Model.Pollution
import com.egemeninceler.donempro.country
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CountryService {

    @GET("api/city_prices?api_key=APIKEY")
    fun getNumbeo(@Query("query") country: String, @Query("currency") currency: String ) : Call<Numbeo>

    @GET("api/city_healthcare?api_key=APIKEY&")
    fun getHealtcare(@Query("query") city: String) : Call<Healtcare>

    @GET("api/city_pollution?api_key=APIKEY&")
    fun getPollution(@Query("query") city: String) : Call<Pollution>

    @GET("api/city_crime?api_key=APIKEY&")
    fun getCrime(@Query("query") city: String) : Call<Crime>
}
