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

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Retrofit

@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
class MyChatServiceApiTest: TestBase() {
    private lateinit var mServiceApi: MyChatServiceApi
    private lateinit var mAuthServiceApi: MyAuthServiceApi

    @ExperimentalSerializationApi
    @Before
    fun setUp() {
        val contentType = "application/json".toMediaType()

        val retrofit = Retrofit.Builder()
            .baseUrl(CHAT_SERVICE)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()

        mServiceApi = retrofit.create(MyChatServiceApi::class.java)

        val retrofit2 = Retrofit.Builder()
            .baseUrl(AUTH_SERVICE)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()

        mAuthServiceApi = retrofit2.create(MyAuthServiceApi::class.java)
    }

    @Test
    fun testCountNewMessages() {
        val count = mServiceApi.countNewMessages(getAuthHeader(), SENDER_ID, RECIPIENT_ID)
        val result = count.execute()
        Assert.assertTrue(result.isSuccessful)
    }

    @Test
    fun testFindChatMessages() {
        val messages = mServiceApi.findChatMessages(getAuthHeader(), SENDER_ID, RECIPIENT_ID)
        val result = messages.execute()
        Assert.assertTrue("messages not empty", result.body()?.isNotEmpty() ?: false)
    }

    @Test
    fun testFindChatMessage() {
        val messages = mServiceApi.findChatMessage(getAuthHeader(), MESSAGE_ID)
        val result = messages.execute()
        Assert.assertTrue("message content != null", result.body()?.content != null)
    }

    private fun getAuthHeader(): String {
        val login = mAuthServiceApi.login(LOGIN_REQUEST)
        val result = login.execute()
        return result.body()?.tokenType + " " + result.body()?.accessToken
    }
}