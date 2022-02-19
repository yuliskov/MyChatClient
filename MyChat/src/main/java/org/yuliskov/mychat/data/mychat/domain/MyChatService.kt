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
import org.yuliskov.mychat.conversation.data.Message
import org.yuliskov.mychat.data.mychat.api.MyChatServiceApi
import retrofit2.Retrofit

@ExperimentalSerializationApi
class MyChatService private constructor() {
    private val authService: MyAuthService
    private val serviceApi: MyChatServiceApi

    companion object {
        private const val CHAT_SERVICE = "http://localhost:8080"
        val instance: MyChatService by lazy {
            MyChatService()
        }
    }

    init {
        val contentType = "application/json".toMediaType()

        val retrofit = Retrofit.Builder()
            .baseUrl(CHAT_SERVICE)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()

        serviceApi = retrofit.create(MyChatServiceApi::class.java)
        authService = MyAuthService.instance
    }

    suspend fun findChatMessages(senderId: String, recipientId: String): List<Message>? {
        val messages = serviceApi.findChatMessages(authService.getAuthHeader() ?: "", senderId, recipientId)
        val result = messages.execute()

        return result.body()?.map { Message(it.senderName ?: "none", it.content ?: "none", it.timestamp ?: "none") }
    }
}