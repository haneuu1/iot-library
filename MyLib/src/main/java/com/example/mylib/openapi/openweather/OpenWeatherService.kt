package com.example.mylib.openapi.openweather

import android.util.Log
import com.example.mylib.openapi.OpenApi
import com.example.mylib.openapi.openweather.data.WeatherCast
import com.example.mylib.openapi.piapi.PiApi
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val API_KEY = "4eae87498e49663a1ed97abf4fc4b4b9"

interface OpenWeatherService {
    // http://api.openweathermap.org/data/2.5/weather?q={city}&APPID={API_KEY}&lang=kr
    @GET("/data/2.5/weather")

    fun getWeatherCast ( // call 객체를 만드는 factory method
        @Query("q") city: String,
        @Query("APPID") apiKey: String = API_KEY,
        @Query("lang") lang: String = "kr"
    ) : Call<WeatherCast> // 실제 호출하는 객체를 리턴
}

// singleton : 오직 한 개의 인스턴스만 운영
// 자동으로 인스턴스가 만들어짐
object OpenWeather : OpenApi() {
    override val TAG = javaClass.simpleName
    override val BASE_URL = "http://api.openweathermap.org"

    private val service = retrofit.create(OpenWeatherService::class.java)

    fun getWeatherCast(city: String, callback: (WeatherCast)->Unit) {
        service.getWeatherCast(city).enqueue(ApiCallback<WeatherCast>(callback))
    }

//    fun getWeatherCast(city: String, callback: (WeatherCast)->Unit) {
//        getService()
//            .getWeatherCast(city)
//            .enqueue(object: Callback<WeatherCast> {
//
//                // 응답 실패 시
//                override fun onFailure(call: Call<WeatherCast>, t: Throwable) {
//                    Log.e(TAG, t.toString())
//                }
//
//                // 응답 성공 시
//                override fun onResponse(
//                    call: Call<WeatherCast>,
//                    response: Response<WeatherCast>
//                ) {
//                    if (response.isSuccessful) { // code: 200 체크
//                        val result = response.body() // body() 의 리턴 타입 ImageSearchResult?
//                        callback(result!!) // isSuccessful 로 검사했으므로 null 이 없음을 보장함
//                    } else {
//                        Log.w(TAG, "${response.code()}, ${response.message()}")
//                        Log.w(TAG, "${response.toString()}")
//                    }
//                }
//
//            })
//    }

}