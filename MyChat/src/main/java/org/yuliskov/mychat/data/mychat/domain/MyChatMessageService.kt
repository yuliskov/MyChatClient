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

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.conversions.kxserialization.StompSessionWithKxSerialization
import org.hildan.krossbow.stomp.conversions.kxserialization.convertAndSend
import org.hildan.krossbow.stomp.conversions.kxserialization.subscribe
import org.hildan.krossbow.stomp.conversions.kxserialization.withJsonConversions
import org.hildan.krossbow.websocket.sockjs.SockJSClient
import org.yuliskov.mychat.conversation.data.Message
import org.yuliskov.mychat.data.mychat.api.model.ChatMessage
import org.yuliskov.mychat.profile.ProfileScreenState

/**
 * https://developer.android.com/kotlin/coroutines<br/>
 * https://medium.com/swlh/how-can-we-use-coroutinescopes-in-kotlin-2210695f0e89<br/>
 * https://developer.android.com/kotlin/flow
 */
class MyChatMessageService private constructor() {
    //private val context: CoroutineContext = Dispatchers.IO
    //private val scope = CoroutineScope(context + SupervisorJob())
    private var jsonStompSession: StompSessionWithKxSerialization? = null

    companion object {
        private const val MESSAGE_SEND_URL = "/app/chat"
        private const val MESSAGE_RECEIVE_URL = "/user/%s/queue/messages"
        private const val CHAT_SERVICE_URL = "http://localhost:8080/ws"
        val instance: MyChatMessageService by lazy {
            MyChatMessageService()
        }
    }

    private suspend fun getSession(): StompSessionWithKxSerialization {
        if (jsonStompSession == null) {
            val client = StompClient(SockJSClient())
            val session = client.connect(CHAT_SERVICE_URL)
            jsonStompSession = session.withJsonConversions()
        }

        return jsonStompSession!!
    }

    private suspend fun sendMessage(message: ChatMessage) {
        withContext(Dispatchers.IO) {
            getSession().convertAndSend(
                MESSAGE_SEND_URL,
                message,
                ChatMessage.serializer()
            )
        }
    }

    private suspend fun subscribeInt(senderId: String): Flow<ChatMessage> {
        return getSession().subscribe(MESSAGE_RECEIVE_URL.format(senderId), ChatMessage.serializer())
    }

    suspend fun sendMessage(message: Message, from: ProfileScreenState, to: ProfileScreenState) {
        sendMessage(ChatMessage(null, from.userId, to.userId, from.name, to.name, message.content, message.timestamp))
    }

    suspend fun subscribe(userId: String): Flow<Message> {
        return subscribeInt(userId).map { Message(it.senderName ?: "", it.content ?: "", it.timestamp ?: "") }
    }
}
