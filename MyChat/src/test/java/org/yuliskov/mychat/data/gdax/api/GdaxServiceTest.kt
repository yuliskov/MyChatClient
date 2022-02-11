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

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.joda.time.format.ISODateTimeFormat
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.yuliskov.mychat.data.gdax.api.model.ProductId
import org.yuliskov.mychat.data.gdax.api.model.Subscribe
import org.yuliskov.mychat.data.gdax.domain.Product
import org.yuliskov.mychat.data.gdax.domain.Transaction
import org.yuliskov.mychat.data.gdax.domain.TransactionRepository
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.inject.Inject
import javax.net.ssl.*


@RunWith(RobolectricTestRunner::class)
class GdaxServiceTest {
    private lateinit var okHttpClient: OkHttpClient
    @Inject
    private lateinit var transactionRepository: TransactionRepository

    @Before
    fun setUp() {
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        //okHttpClient = getUnsafeOkHttpClient()!!
    }

    @Test
    fun genericServiceTest() {
        val gdaxService = createGdaxService()

        Assert.assertNotNull(gdaxService)
    }

    @Test
    fun repositoryTest() {
        val gdaxService = createGdaxService();
        subscribe(gdaxService)
        val (product, transaction) = observe(gdaxService)

        println(transaction)
        Assert.assertNotNull(transaction)
    }

    private fun observe(gdaxService: GdaxService): Pair<Product, Transaction> {
        return gdaxService.observeTicker()
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
            .blockingFirst()
    }

    private fun subscribe(gdaxService: GdaxService) {
        gdaxService.observeWebSocketEvent()
            .filter { it is WebSocket.Event.OnConnectionOpened<*> }
            .blockingSubscribe()

        val subscribe = Subscribe(
            type = Subscribe.Type.SUBSCRIBE,
            productIds = listOf(ProductId.BTC_USD, ProductId.ETH_USD, ProductId.LTC_USD),
            channels = listOf(Subscribe.Channel.TICKER)
        )

        gdaxService.sendSubscribe(subscribe)
    }

    private fun createGdaxService(): GdaxService {
        val moshi = Moshi.Builder()
            .add(MoshiAdapters())
            .add(KotlinJsonAdapterFactory())
            .build()
        val scarletInstance = Scarlet.Builder()
            .webSocketFactory(okHttpClient.newWebSocketFactory("wss://ws-feed.gdax.com"))
            .addMessageAdapterFactory(MoshiMessageAdapter.Factory(moshi))
            .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
            .build()

        return scarletInstance.create()
    }

    private fun getUnsafeOkHttpClient(): OkHttpClient? {
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts: Array<TrustManager> = arrayOf(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<X509Certificate?>?, authType: String?) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<X509Certificate?>?, authType: String?) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )

            // Install the all-trusting trust manager
            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }
            builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}