package com.example.newsapplication.repository.network

import com.example.newsapplication.repository.CallBackListener
import org.json.JSONObject
import retrofit2.HttpException
import java.net.UnknownHostException

object ErrorResponseHandler {

    fun handle(throwable: Throwable, callBackListener: CallBackListener) {

        when(throwable){
            is UnknownHostException -> {
                callBackListener.onError(
                    "401", "No internet connection"
                )
            }
            is HttpException ->{
                val response = throwable.response()!!

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
            else ->{
                callBackListener.onError(
                    "401", "Something went wrong"
                )
            }
        }

    }
}