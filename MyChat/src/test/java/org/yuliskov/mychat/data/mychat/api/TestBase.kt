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

import org.yuliskov.mychat.data.mychat.api.model.LoginRequest
import org.yuliskov.mychat.data.mychat.api.model.SignupRequest

open class TestBase {
    companion object {
        const val AUTH_SERVICE = "http://localhost:8081"
        const val CHAT_SERVICE = "http://localhost:8080"
        const val CHAT_WS_SERVICE = "http://localhost:8080/ws"
        const val MESSAGE_ID = "62099f63a6adac17b0bcd061"
        const val SENDER_ID = "62099f63a6adac17b0bcd061"
        const val RECIPIENT_ID = "6209a01da6adac17b0bcd062"
        const val SENDER_NAME = "test"
        const val SENDER_EMAIL = "myemail@gmail.com"
        const val SENDER_PASSWORD = "testTest1314"
        const val SENDER_PIC_URL = "http://localhost:8081"
        const val RECIPIENT_NAME = "test3"
        const val RECIPIENT_EMAIL = "myemail2@gmail.com"
        const val RECIPIENT_PASSWORD = "testTest1314"
        const val RECIPIENT_PIC_URL = "http://localhost:8081"
        const val SIMPLE_MESSAGE = "simple message for user with id = $RECIPIENT_ID"

        val SIGNUP_REQUEST = SignupRequest(SENDER_NAME, SENDER_NAME, SENDER_EMAIL, SENDER_PASSWORD, SENDER_PIC_URL)
        val SIGNUP_REQUEST2 = SignupRequest(RECIPIENT_NAME, RECIPIENT_NAME, RECIPIENT_EMAIL, RECIPIENT_PASSWORD, RECIPIENT_PIC_URL)
        val LOGIN_REQUEST = LoginRequest(SENDER_NAME, SENDER_PASSWORD)
    }
}
