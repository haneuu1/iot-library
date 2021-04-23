package com.example.mylib.openapi.smartdoorapi

import com.example.mylib.openapi.OpenApi
import com.example.mylib.openapi.smartdoorapi.data.MqttData
import com.example.mylib.openapi.smartdoorapi.data.RecordingData
import retrofit2.Call
import retrofit2.http.GET

interface SmartdoorApiService {
    // 비디오
    @GET("/recording")
    fun getRecordingList(

    ): Call<RecordingData>
    
    // 출입 기록
    @GET("/mqtt")
    fun getMqttList(
        
    ): Call<MqttData>
}

object SmartdoorApi : OpenApi() {
    override val TAG = javaClass.simpleName
    override val BASE_URL = "http://172.30.1.44:8000" // 라즈베리파이

    private val service = retrofit.create(SmartdoorApiService::class.java)

    fun getRecordingList(callback: (RecordingData)->Unit) {
        service.getRecordingList().enqueue(ApiCallback<RecordingData>(callback))
    }
    
    fun getMqttList(callback: (MqttData)->Unit) {
        service.getMqttList().enqueue(ApiCallback<MqttData>(callback))
    }
}