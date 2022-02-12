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

import kotlinx.coroutines.runBlocking
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.websocket.sockjs.SockJSClient
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

class MyChatServiceInstrumentedTest {
    private val AUTH_SERVICE = "http://localhost:8081";
    private val CHAT_SERVICE = "http://localhost:8080";
    private val CHAT_WS_SERVICE = "http://localhost:8080/ws";

    @Before
    fun setUp() {
        //TODO("Not yet implemented")
    }

    @Test
    fun testConnected() = runBlocking {
        val client = StompClient(SockJSClient())
        val session = client.connect(CHAT_WS_SERVICE)

        Assert.assertNotNull(session)
    }
}