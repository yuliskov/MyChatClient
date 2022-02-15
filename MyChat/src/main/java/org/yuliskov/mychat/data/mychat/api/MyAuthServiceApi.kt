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

package org.yuliskov.mychat.data.mychat.api

import org.yuliskov.mychat.data.mychat.api.model.LoginRequest
import org.yuliskov.mychat.data.mychat.api.model.LoginResponse
import org.yuliskov.mychat.data.mychat.api.model.SignupRequest
import org.yuliskov.mychat.data.mychat.api.model.ChatUser
import retrofit2.Call
import retrofit2.http.*

interface MyAuthServiceApi {
    @Headers("Content-Type: application/json")
    @POST("/signin")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("/users")
    fun signup(@Body request: SignupRequest): Call<Unit>

    @Headers("Content-Type: application/json")
    @GET("/users/me")
    fun getCurrentUser(@Header("Authorization") auth: String): Call<ChatUser>

    @Headers("Content-Type: application/json")
    @GET("/users/summaries")
    fun getContacts(@Header("Authorization") auth: String): Call<List<ChatUser>>
}