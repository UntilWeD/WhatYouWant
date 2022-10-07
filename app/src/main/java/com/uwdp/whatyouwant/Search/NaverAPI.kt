package com.uwdp.whatyouwant.Search


import com.google.api.Http
import com.squareup.okhttp.Interceptor
import com.squareup.okhttp.OkHttpClient
import okhttp3.OkHttp
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverAPI{
    @GET("v1/search/shop.json")
    fun getSearchItems(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret:String,
        @Query("query") query: String?,
        @Query("display") display: Int? = null,
        @Query("start") start: Int? = null,
        @Query("sort") sort: String? = null
    ): Call<ResultGetSearchItems>
}



