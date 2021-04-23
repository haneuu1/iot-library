package com.example.mylib.openapi.kakao.image

import android.util.Log
import com.example.mylib.openapi.kakao.image.data.ImageSearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoImageSearchService {

    @Headers("Authorization: KakaoAK db172a7f2a2236ada32e3b239aae0089")
    @GET("/v2/search/image") // 카카오가 정의한 url
    fun requestSearchImage ( // call 객체를 만드는 factory method
        @Query("query") keyword: String,
        @Query("sort") sort: String = "recency",
        @Query("page") page: Int,
        @Query("size") size: Int = 10
    ) : Call<ImageSearchResult> // 실제 호출하는 객체를 리턴
}

// singleton : 오직 한 개의 인스턴스만 운영
// 자동으로 인스턴스가 만들어짐
object KakaoImageSearch {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dapi.kakao.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getService(): KakaoImageSearchService = retrofit.create(
        KakaoImageSearchService::class.java)

    fun searchImage(keyword: String, page: Int, callback: (ImageSearchResult)->Unit) {
        getService()
            .requestSearchImage(keyword = keyword, page = page)
            .enqueue(object: Callback<ImageSearchResult> {

                // 응답 실패 시
                override fun onFailure(call: Call<ImageSearchResult>, t: Throwable) {
                    Log.e("----", t.toString())
                }

                // 응답 성공 시
                override fun onResponse(
                    call: Call<ImageSearchResult>,
                    response: Response<ImageSearchResult>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body() // body() 의 리턴 타입 ImageSearchResult?
                        callback(result!!)
                    } else {
                        Log.w("MainActivity", "${response.code()}, ${response.message()}")
                        Log.w("MainActivity", "${response.toString()}")
                    }
                }

            })
    }
}