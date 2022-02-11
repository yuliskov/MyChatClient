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

package org.yuliskov.mychat.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.yuliskov.mychat.data.colleagueProfile
import org.yuliskov.mychat.data.meProfile
import org.yuliskov.mychat.theme.MyChatTheme

@Preview(widthDp = 340, name = "340 width - Me")
@Composable
fun ProfilePreview340() {
    MyChatTheme {
        ProfileScreen(meProfile)
    }
}

@Preview(widthDp = 480, name = "480 width - Me")
@Composable
fun ProfilePreview480Me() {
    MyChatTheme {
        ProfileScreen(meProfile)
    }
}

@Preview(widthDp = 480, name = "480 width - Other")
@Composable
fun ProfilePreview480Other() {
    MyChatTheme {
        ProfileScreen(colleagueProfile)
    }
}
@Preview(widthDp = 340, name = "340 width - Me - Dark")
@Composable
fun ProfilePreview340MeDark() {
    MyChatTheme(isDarkTheme = true) {
        ProfileScreen(meProfile)
    }
}

@Preview(widthDp = 480, name = "480 width - Me - Dark")
@Composable
fun ProfilePreview480MeDark() {
    MyChatTheme(isDarkTheme = true) {
        ProfileScreen(meProfile)
    }
}

@Preview(widthDp = 480, name = "480 width - Other - Dark")
@Composable
fun ProfilePreview480OtherDark() {
    MyChatTheme(isDarkTheme = true) {
        ProfileScreen(colleagueProfile)
    }
}
