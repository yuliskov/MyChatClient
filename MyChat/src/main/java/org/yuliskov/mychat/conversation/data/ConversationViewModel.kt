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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.yuliskov.mychat.data.meProfile
import org.yuliskov.mychat.data.mychat.domain.MyChatMessageService
import timber.log.Timber

class ConversationViewModel: ViewModel() {
    private var userId: String = ""

    fun setUserId(newUserId: String?) {
        if (newUserId != userId) {
            userId = newUserId ?: meProfile.userId
        }

        _uiState.value = ConversationUiState()

        // Create a new coroutine on the UI thread
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val subscription = MyChatMessageService.instance.subscribe(userId)
                subscription.collect {
                     _uiState.value?.addMessage(it)
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private val _uiState = MutableLiveData<ConversationUiState>()
    val uiState: LiveData<ConversationUiState> = _uiState
}