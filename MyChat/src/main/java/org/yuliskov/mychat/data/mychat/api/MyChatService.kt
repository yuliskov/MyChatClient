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

import org.yuliskov.mychat.data.mychat.api.model.ChatMessage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface MyChatService {
    @GET("/messages/{senderId}/{recipientId}/count")
    fun countNewMessages(@Header("Authorization") auth: String,
                         @Path("senderId") senderId: String,
                         @Path("recipientId") recipientId: String): Call<Int>

    @GET("/messages/{senderId}/{recipientId}")
    fun findChatMessages(@Header("Authorization") auth: String,
                         @Path("senderId") senderId: String,
                         @Path("recipientId") recipientId: String): Call<List<ChatMessage>>

    @GET("/messages/{id}")
    fun findChatMessage(@Header("Authorization") auth: String,
                         @Path("id") id: String): Call<ChatMessage>
}