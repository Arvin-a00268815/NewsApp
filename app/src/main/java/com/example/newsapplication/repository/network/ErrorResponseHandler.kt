package com.example.newsapplication.repository.network

import com.example.newsapplication.repository.CallBackListener
import org.json.JSONObject
import retrofit2.HttpException

object ErrorResponseHandler {

    fun handle(throwable: Throwable, callBackListener: CallBackListener) {
        val response = (throwable as HttpException).response()!!

        with(response) {

            errorBody()?.apply {


                val jsonObject = JSONObject(string())

                callBackListener.onError(
                    jsonObject.getString("code"),
                    jsonObject.getString("message")
                )
            }
        }
    }
}