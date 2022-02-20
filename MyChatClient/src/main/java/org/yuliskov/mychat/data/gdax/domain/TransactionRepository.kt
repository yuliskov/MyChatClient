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

package org.yuliskov.mychat.data.gdax.domain

import com.tinder.scarlet.WebSocket
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import org.joda.time.format.ISODateTimeFormat
import org.yuliskov.mychat.data.gdax.api.GdaxService
import org.yuliskov.mychat.data.gdax.api.model.ProductId
import org.yuliskov.mychat.data.gdax.api.model.Subscribe
import org.yuliskov.mychat.data.gdax.inject.GdaxScope
import timber.log.Timber
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

@GdaxScope
class TransactionRepository @Inject constructor(
    private val gdaxService: GdaxService
) {

    private val transactionBookRef = AtomicReference(TransactionBook())
    private val transactionBookProcessor = BehaviorProcessor.create<TransactionBook>()

    init {
        gdaxService.observeWebSocketEvent()
            .filter { it is WebSocket.Event.OnConnectionOpened<*> }
            .subscribe({
                val subscribe = Subscribe(
                    type = Subscribe.Type.SUBSCRIBE,
                    productIds = listOf(ProductId.BTC_USD, ProductId.ETH_USD, ProductId.LTC_USD),
                    channels = listOf(Subscribe.Channel.TICKER)
                )

                gdaxService.sendSubscribe(subscribe)
            }, { e ->
                Timber.e(e)
            })

        gdaxService.observeTicker()
            .filter { it.type == "ticker" }
            .map { ticker ->
                val product = when (ticker.product) {
                    ProductId.BTC_USD -> Product.BTC
                    ProductId.ETH_USD -> Product.ETH
                    ProductId.LTC_USD -> Product.LTC
                }
                val price = ticker.price.toFloat()
                val time = ISODateTimeFormat.dateTime().parseDateTime(ticker.time)
                product to Transaction(price, time)
            }
            .subscribe({ (product, transaction) ->
                addTransaction(product, transaction)
            }, { e ->
                Timber.e(e)
            })
    }

    fun observeTransactionBook(): Flowable<TransactionBook> {
        return transactionBookProcessor
    }

    private fun addTransaction(product: Product, transaction: Transaction) {
        val transactionBook = transactionBookRef.get()
            .addingTransaction(product, transaction)
        transactionBookRef.set(transactionBook)
        transactionBookProcessor.onNext(transactionBook)
    }
}