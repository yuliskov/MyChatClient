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

package org.yuliskov.mychat.data.gdax.api

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.yuliskov.mychat.data.gdax.api.model.ProductId
import org.yuliskov.mychat.data.gdax.api.model.Subscribe

class MoshiAdapters {
    @FromJson
    fun productIdFromJson(string: String): ProductId {
        return ProductId.values().find { it.text == string }!!
    }

    @ToJson
    fun productIdToJson(data: ProductId): String {
        return data.text
    }

    @FromJson
    fun subscribeTypeFromJson(string: String): Subscribe.Type {
        return Subscribe.Type.values().find { it.text == string }!!
    }

    @ToJson
    fun subscribeTypeToJson(data: Subscribe.Type): String {
        return data.text
    }

    @FromJson
    fun subscribeChannelFromJson(string: String): Subscribe.Channel {
        return Subscribe.Channel.values().find { it.text == string }!!
    }

    @ToJson
    fun subscribeChannelJson(data: Subscribe.Channel): String {
        return data.text
    }
}