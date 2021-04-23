package com.example.mylib.openapi

import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class OpenApi {

    // open: 자식이 오버라이드 해서 재 정의 하겠다는 의미
    open val TAG = ""
    open val BASE_URL = ""

    protected val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()

    protected val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    // T: 데이터 타입의 변수화
    inner class ApiCallback<T>(val callback: (T)->Unit) : Callback<T> {
        // 응답 실패 시
        override fun onFailure(call: Call<T>, t: Throwable) {
            Log.e(TAG, t.toString())
        }

        // 응답 성공 시
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) { // code: 200 체크
                val result = response.body() // body() 의 리턴 타입 ImageSearchResult?
                callback(result!!) // isSuccessful 로 검사했으므로 null 이 없음을 보장함
            } else {
                Log.w(TAG, "${response.code()}, ${response.message()}")
                Log.w(TAG, "${response.toString()}")
            }
        }
    }

}