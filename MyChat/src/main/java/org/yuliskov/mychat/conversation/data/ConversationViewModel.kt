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

package org.yuliskov.mychat.conversation.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.jetchat.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import org.yuliskov.mychat.data.meProfile
import org.yuliskov.mychat.data.mychat.domain.MyChatMessageService
import org.yuliskov.mychat.data.mychat.domain.MyChatService
import org.yuliskov.mychat.profile.ProfileScreenState
import timber.log.Timber

@ExperimentalSerializationApi
class ConversationViewModel: ViewModel() {
    private val chatService: MyChatService = MyChatService.instance
    private val chatMessageService: MyChatMessageService = MyChatMessageService.instance
    private var userId: String = ""
    private val testUser: ProfileScreenState =
        ProfileScreenState("testUserId", R.drawable.ali, "Test User", "Online", "Test User", "Test user position")
    private val testGroup: ProfileScreenState =
        ProfileScreenState("testGroupId", R.drawable.someone_else, "Test Group", "Online", "Test Group", "Test group position")

    fun setUserId(newUserId: String?) {
        if (newUserId != userId) {
            userId = newUserId ?: meProfile.userId
        }

        _uiState.value = ConversationUiState(onAdd = {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    chatMessageService.sendMessage(it, testUser, testGroup)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })

        // Create a new coroutine on the UI thread
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val initialMessages = chatService.findChatMessages(testUser.userId, testGroup.userId)
                initialMessages?.forEach { _uiState.value?.addMessage(it) }

                val subscription = chatMessageService.subscribe(userId)
                subscription.collect {
                     _uiState.value?.addMessage(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val _uiState = MutableLiveData<ConversationUiState>()
    val uiState: LiveData<ConversationUiState> = _uiState
}