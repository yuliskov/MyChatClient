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

package org.yuliskov.mychat.buildsrc

object Versions {
    const val ktlint = "0.43.2"
}

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.1.0"
    const val jdkDesugar = "com.android.tools:desugar_jdk_libs:1.1.5"

    const val junit = "junit:junit:4.13"
    const val robolectric = "org.robolectric:robolectric:4.7.3"

    const val material3 = "com.google.android.material:material:1.5.0-rc01"

    object Accompanist {
        const val version = "0.22.1-rc"
        const val insets = "com.google.accompanist:accompanist-insets:$version"
    }

    object Kotlin {
        private const val version = "1.6.10"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
        const val serializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:$version"
        const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2"
    }

    object Coroutines {
        private const val version = "1.6.0"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.4.1"
        const val coreKtx = "androidx.core:core-ktx:1.7.0"

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.4.0"
        }

        object Compose {
            const val snapshot = ""
            const val version = "1.1.0-rc03"

            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val layout = "androidx.compose.foundation:foundation-layout:$version"
            const val material = "androidx.compose.material:material:$version"
            const val materialIconsExtended = "androidx.compose.material:material-icons-extended:$version"
            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:$version"
            const val tooling = "androidx.compose.ui:ui-tooling:$version"
            const val test = "androidx.compose.ui:ui-test:$version"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:$version"
            const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:$version"
            const val uiUtil = "androidx.compose.ui:ui-util:${version}"
            const val viewBinding = "androidx.compose.ui:ui-viewbinding:$version"

            object Material3 {
                const val snapshot = ""
                const val version = "1.0.0-alpha02"

                const val material3 = "androidx.compose.material3:material3:$version"
            }
        }

        object Navigation {
            private const val version = "2.4.0"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
        }

        object Test {
            private const val version = "1.4.0"
            const val core = "androidx.test:core:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.2"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

            const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"
        }

        object Lifecycle {
            private const val version = "2.4.0"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
        }
    }

    object Tinder {
        private const val version = "0.1.12"
        const val scarlet = "com.tinder.scarlet:scarlet:$version"
        const val moshiMessageAdapter = "com.tinder.scarlet:message-adapter-moshi:$version"
        const val rxJavaStreamAdapter = "com.tinder.scarlet:stream-adapter-rxjava2:$version"
        const val webSocketOkHttp = "com.tinder.scarlet:websocket-okhttp:$version"
        const val androidLifecycle = "com.tinder.scarlet:lifecycle-android:$version"
    }

    object ReactiveX {
        const val rxJava = "io.reactivex.rxjava2:rxjava:2.2.21"
        const val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"
        const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:2.4.0"
    }

    object Square {
        private const val okHttpVersion = "4.9.3"
        private const val daggerVersion = "2.40.5"
        const val moshi = "com.squareup.moshi:moshi-kotlin:1.13.0"
        const val okHttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"
        const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"
        const val dagger = "com.google.dagger:dagger:$daggerVersion"
        const val daggerCompiler = "com.google.dagger:dagger-compiler:$daggerVersion"
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    }

    object Joda {
        const val time = "joda-time:joda-time:2.10.13"
    }

    object JakeWharton {
        const val timber = "com.jakewharton.timber:timber:5.0.1"
        const val retrofitKotlinSerializer = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
    }

    object Krossbow {
        private const val version = "3.1.1"
        const val stomp = "org.hildan.krossbow:krossbow-stomp-core:$version"
        const val sockjs = "org.hildan.krossbow:krossbow-websocket-sockjs:$version"
        const val kxserialization = "org.hildan.krossbow:krossbow-stomp-kxserialization:$version"
    }

    object JavaX {
        const val websockets = "org.glassfish.tyrus.bundles:tyrus-standalone-client:1.9"
        const val websocketApi = "javax.websocket:javax.websocket-api:1.1"
        const val xmlParserBackport = "javax.xml.parsers:jaxp-api:1.4.5" // Java 8 backport: xml parser
        const val timeBackport = "com.github.seratch:java-time-backport:1.0.0" // Java 8 backport: time
    }

    object Jackson {
        private const val version = "2.13.1"
        const val core = "com.fasterxml.jackson.core:jackson-core:$version"
        const val databind = "com.fasterxml.jackson.core:jackson-databind:$version"
        const val jacksonAnnotations = "com.fasterxml.jackson.core:jackson-annotations:$version"
    }
}

object Urls {
    const val composeSnapshotRepo = "https://androidx.dev/snapshots/builds/" +
        "${Libs.AndroidX.Compose.snapshot}/artifacts/repository/"
    const val composeMaterial3SnapshotRepo = "https://androidx.dev/snapshots/builds/" +
            "${Libs.AndroidX.Compose.Material3.snapshot}/artifacts/repository/"
    const val accompanistSnapshotRepo = "https://oss.sonatype.org/content/repositories/snapshots"
}
