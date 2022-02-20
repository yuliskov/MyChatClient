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

package org.yuliskov.mychat.data.gdax.presenter

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Flowables
import org.yuliskov.mychat.data.gdax.domain.Product
import org.yuliskov.mychat.data.gdax.domain.TransactionBook
import org.yuliskov.mychat.data.gdax.domain.TransactionRepository
import org.yuliskov.mychat.data.gdax.target.GdaxTarget
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GdaxPresenter @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    private lateinit var target: GdaxTarget
    private var currentProduct = Product.BTC
    private var transactionBook = TransactionBook()
    private val compositeDisposable = CompositeDisposable()

    fun takeTarget(target: GdaxTarget) {
        this.target = target

        val transactionBookSubscription = Flowables.combineLatest(
            Flowable.interval(UPDATE_INTERVAL_SECONDS, TimeUnit.SECONDS),
            transactionRepository.observeTransactionBook()
        )
            .map { (_, transactionBook) -> transactionBook }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                transactionBook = it
                showTransactionBook()
            }, Timber::e)
        showTransactionBook()

        compositeDisposable.addAll(transactionBookSubscription)
    }

    fun dropTarget() {
        compositeDisposable.clear()
    }

    fun handleShowBTC() {
        currentProduct = Product.BTC
        showTransactionBook()
    }

    fun handleShowETH() {
        currentProduct = Product.ETH
        showTransactionBook()
    }

    fun handleShowLTC() {
        currentProduct = Product.LTC
        showTransactionBook()
    }

    private fun showTransactionBook() {
        val transactions = transactionBook.getTransactions(currentProduct)
        target.showTransactions(currentProduct, transactions)
    }

    private companion object {
        private const val UPDATE_INTERVAL_SECONDS = 1L
    }
}