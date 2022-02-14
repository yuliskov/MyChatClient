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

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.conversions.kxserialization.convertAndSend
import org.hildan.krossbow.stomp.conversions.kxserialization.subscribe
import org.hildan.krossbow.stomp.conversions.kxserialization.withJsonConversions
import org.hildan.krossbow.stomp.sendText
import org.hildan.krossbow.stomp.subscribeText
import org.hildan.krossbow.websocket.sockjs.SockJSClient
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.yuliskov.mychat.data.mychat.api.model.ChatMessage

@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE)
class MyChatStompServiceTest: TestBase() {
    @Before
    fun setUp() {
        //TODO("Not yet implemented")
    }

    @Test
    fun testConnected() = runBlocking {
        val client = StompClient(SockJSClient())
        val session = client.connect(CHAT_WS_SERVICE)

        // Send chat message
        session.sendText("/app/chat", """
            {
                "senderId": "$SENDER_ID", 
                "recipientId": "$RECIPIENT_ID",
                "senderName": "$SENDER_NAME",
                "recipientName": "$RECIPIENT_NAME",
                "content": "$SIMPLE_MESSAGE",
                "timestamp": "${System.currentTimeMillis()}"
            }
        """.trimIndent())

        // Receive chat message
        val subscription: Flow<String> = session.subscribeText("/user/$SENDER_ID/queue/messages")

        // {"id":"6209a22f74c12301028e086b","senderId":"6209a01da6adac17b0bcd062","senderName":"test3"}
        val collectorJob = launch {
            subscription.collect { msg ->
                println("Received: $msg")
            }
        }

        //delay(30_000)

        //collectorJob.cancel()

        //session.disconnect()

        Assert.assertNotNull(session)
    }

    @Test
    fun jsonConversionTest() = runBlocking {
        val client = StompClient(SockJSClient())
        val session = client.connect(CHAT_WS_SERVICE)
        val jsonStompSession = session.withJsonConversions()

        // Send chat message
        jsonStompSession.convertAndSend("/app/chat",
            ChatMessage(null, SENDER_ID, RECIPIENT_ID, SENDER_NAME, RECIPIENT_NAME, SIMPLE_MESSAGE, "${System.currentTimeMillis()}"),
            ChatMessage.serializer()
        )

        // Receive chat message
        val subscription: Flow<ChatMessage> = jsonStompSession.subscribe("/user/$SENDER_ID/queue/messages", ChatMessage.serializer())

        // {"id":"6209a22f74c12301028e086b","senderId":"6209a01da6adac17b0bcd062","senderName":"test3"}
        val collectorJob = launch {
            subscription.collect { msg ->
                println("Received: $msg")
            }
        }

        Assert.assertNotNull(session)
    }
}