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
class MyAuthServiceApiTest: TestBase() {
    private lateinit var mServiceApi: MyAuthServiceApi

    @ExperimentalSerializationApi
    @Before
    fun setUp() {
        val contentType = "application/json".toMediaType()
        val retrofit = Retrofit.Builder()
            .baseUrl(AUTH_SERVICE)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()

        mServiceApi = retrofit.create(MyAuthServiceApi::class.java)
    }

    @Test
    fun testLogin() {
        val login = mServiceApi.login(LOGIN_REQUEST)
        val result = login.execute()
        Assert.assertNotNull("Token != null", result.body()?.accessToken)
        Assert.assertTrue(result.isSuccessful)
    }

    @Test
    fun testSignup() {
        val signup = mServiceApi.signup(SIGNUP_REQUEST2)
        val result = signup.execute()
        Assert.assertTrue(result.isSuccessful)
    }

    @Test
    fun testGetCurrentUser() {
        val currentUser = mServiceApi.getCurrentUser(getAuthHeader())
        val result = currentUser.execute()
        Assert.assertNotNull("user id != null", result.body()?.id)
        Assert.assertNotNull("user name != null", result.body()?.name)
    }

    @Test
    fun testGetUsers() {
        val users = mServiceApi.getContacts(getAuthHeader())
        val result = users.execute()
        Assert.assertTrue("users not empty", result.body()?.isNotEmpty() ?: false)
    }

    private fun getAuthHeader(): String {
        val login = mServiceApi.login(LOGIN_REQUEST)
        val result = login.execute()
        return result.body()?.tokenType + " " + result.body()?.accessToken
    }
}