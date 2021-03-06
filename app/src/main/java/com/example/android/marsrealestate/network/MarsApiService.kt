/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://mars.udacity.com/"

enum class MarsApiFilter(val value: String) { SHOW_RENT("rent"), SHOW_BUY("buy"), SHOW_ALL("all") }

// note. Use the Moshi builder to create a Moshi object with the KotlinJsonAdapterFactory
private val moshi =  Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

// note. Declared retrofit builder with ScalarsConverterFactory and base URL
private val retrofit = Retrofit.Builder()
//        .addConverterFactory(ScalarsConverterFactory.create()) // note. string to json
        .addConverterFactory(MoshiConverterFactory.create(moshi))   // note. string to kotlin object
        .addCallAdapterFactory(CoroutineCallAdapterFactory())   // note. able to use coroutine in retrofit
        .baseUrl(BASE_URL)
        .build()

// note. Implement the MarsApiService interface.
interface MarsApiService {
    @GET("realestate")
    fun getProperties(
            @Query("filter") type: String
    ):
//            Call<List<MarsProperty>>
            Deferred<List<MarsProperty>>
}

// note. Create the MarsApi object using Retrofit to implement the MarsApiService
object MarsApi {
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}
