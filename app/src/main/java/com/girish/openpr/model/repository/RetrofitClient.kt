package com.girish.openpr.model.repository

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

// Singleton
object RetrofitClient {

    var retrofit : Retrofit? = null
    val endpoint : String = "https://api.github.com/"

    fun getInstance() : Retrofit {

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        }

        // TODO: can you avoid using non-null assertion operator?
        return retrofit!!
    }
}