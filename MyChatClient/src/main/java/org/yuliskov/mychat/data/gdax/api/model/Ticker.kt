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

package org.yuliskov.mychat.data.gdax.api.model

import com.squareup.moshi.Json

data class Ticker(
    val type: String,
    @Json(name = "product_id")
    val product: ProductId,
    val sequence: Long,
    val time: String,
    val price: String,
    val side: String,
    @Json(name = "last_size")
    val size: String,
    @Json(name = "best_bid")
    val bestBid: String,
    @Json(name = "best_ask")
    val bestAsk: String
)