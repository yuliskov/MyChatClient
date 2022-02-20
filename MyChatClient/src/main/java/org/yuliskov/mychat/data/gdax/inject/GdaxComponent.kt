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

package org.yuliskov.mychat.data.gdax.inject

import android.app.Application
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.yuliskov.mychat.data.gdax.api.GdaxService
import org.yuliskov.mychat.data.gdax.api.MoshiAdapters

@GdaxScope
@Component(modules = [(GdaxComponent.GdaxModule::class)], dependencies = [(GdaxComponent.Dependency::class)])
interface GdaxComponent {
    //fun inject(gdaxFragment: GdaxFragment)

    interface Dependency {
        fun application(): Application
    }

    @Component.Builder
    interface Builder {
        fun dependency(dependency: Dependency): Builder

        fun build(): GdaxComponent
    }

    @Module
    class GdaxModule {
        @Provides
        @GdaxScope
        fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        @Provides
        @GdaxScope
        fun provideLifecycle(application: Application): Lifecycle {
            return AndroidLifecycle.ofApplicationForeground(application)
        }

        @Provides
        @GdaxScope
        fun provideGdaxService(client: OkHttpClient, lifecycle: Lifecycle): GdaxService {
            val moshi = Moshi.Builder()
                .add(MoshiAdapters())
                .add(KotlinJsonAdapterFactory())
                .build()
            val scarlet = Scarlet.Builder()
                .webSocketFactory(client.newWebSocketFactory("wss://ws-feed.gdax.com"))
                .lifecycle(lifecycle)
                .addMessageAdapterFactory(MoshiMessageAdapter.Factory(moshi))
                .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
                .build()
            return scarlet.create()
        }
    }

    interface ComponentProvider {
        val gdaxComponent: GdaxComponent
    }
}