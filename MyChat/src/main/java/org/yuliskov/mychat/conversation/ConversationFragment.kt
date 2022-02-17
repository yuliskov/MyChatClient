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

package org.yuliskov.mychat.conversation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.compose.jetchat.R
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ViewWindowInsetObserver
import com.google.accompanist.insets.navigationBarsPadding
import org.yuliskov.mychat.MainViewModel
import org.yuliskov.mychat.conversation.data.ConversationViewModel
import org.yuliskov.mychat.data.exampleUiState
import org.yuliskov.mychat.theme.MyChatTheme

class ConversationFragment : Fragment() {
    private val conversationViewModel: ConversationViewModel by viewModels()
    private val activityViewModel: MainViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        conversationViewModel.setUserId("1234546")
    }

    @OptIn(ExperimentalAnimatedInsets::class) // Opt-in to experiment animated insets support
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)

        // Create a ViewWindowInsetObserver using this view, and call start() to
        // start listening now. The WindowInsets instance is returned, allowing us to
        // provide it to AmbientWindowInsets in our content below.
        val windowInsets = ViewWindowInsetObserver(this)
            // We use the `windowInsetsAnimationsEnabled` parameter to enable animated
            // insets support. This allows our `ConversationContent` to animate with the
            // on-screen keyboard (IME) as it enters/exits the screen.
            .start(windowInsetsAnimationsEnabled = true)

        setContent {
            val uiState by conversationViewModel.uiState.observeAsState()

            CompositionLocalProvider(
                LocalBackPressedDispatcher provides requireActivity().onBackPressedDispatcher,
                LocalWindowInsets provides windowInsets,
            ) {
                MyChatTheme {
                    ConversationContent(
                        // TODO: modified
                        //uiState = exampleUiState,
                        uiState = uiState!!,
                        navigateToProfile = { user ->
                            // Click callback
                            val bundle = bundleOf("userId" to user)
                            findNavController().navigate(
                                R.id.nav_profile,
                                bundle
                            )
                        },
                        onNavIconPressed = {
                            activityViewModel.openDrawer()
                        },
                        // Add padding so that we are inset from any left/right navigation bars
                        // (usually shown when in landscape orientation)
                        modifier = Modifier.navigationBarsPadding(bottom = false)
                    )
                }
            }
        }
    }
}
