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

package org.yuliskov.mychat.data.mychat.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessage(
    val id: String? = null,
    val senderId: String? = null,
    val recipientId: String? = null,
    val senderName: String? = null,
    val recipientName: String? = null,
    val content: String? = null,
    val timestamp: String? = null,
    val chatId: String? = null,
    val status: String? = null,
)
