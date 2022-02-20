/*
 * Copyright 2022 Yuriy Lyskov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.yuliskov.mychat.data.mychat.domain

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.yuliskov.mychat.data.mychat.api.MyAuthServiceApi
import org.yuliskov.mychat.data.mychat.api.model.LoginRequest
import retrofit2.Retrofit

@ExperimentalSerializationApi
class MyAuthService private constructor() {
    private val serviceApi: MyAuthServiceApi
    private var authHeader: String? = null

    companion object {
        private const val AUTH_SERVICE = "http://localhost:8081"
        val instance: MyAuthService by lazy {
            MyAuthService()
        }
    }

    init {
        val contentType = "application/json".toMediaType()

        val retrofit2 = Retrofit.Builder()
            .baseUrl(AUTH_SERVICE)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()

        serviceApi = retrofit2.create(MyAuthServiceApi::class.java)
    }

    /**
     * Enter to existing account
     */
    suspend fun login(senderName: String, senderPassword: String): String? {
        authHeader = loginInt(senderName, senderPassword)
        return authHeader
    }

    fun getAuthHeader(): String? {
        return authHeader
    }

    private fun loginInt(senderName: String, senderPassword: String): String {
        val login = serviceApi.login(LoginRequest(senderName, senderPassword))
        val result = login.execute()
        return result.body()?.tokenType + " " + result.body()?.accessToken
    }
}