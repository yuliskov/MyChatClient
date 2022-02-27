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

package org.yuliskov.mychat.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class LoginViewModel : ViewModel() {
    // login activity can observe if there is a logged in user.
    // if there is none, this user will be set after login call.
    //val user = authRepository.getLoggedInUser()
    val user = null

    fun login(userName: String, password: String) {
        //authRepository.login(userName, password)
    }

    fun goToInitialState() {
        //user.postValue(Resource(Status.SUCCESS, null, null))
    }

    private val _loginData = MutableLiveData<User>()
    val loginData: LiveData<User> = _loginData
}

//@Immutable
//data class User(
//    val userId: String,
//    @DrawableRes val photo: Int?,
//    val name: String
//)

//@Entity
//data class User(
//    @SerializedName("id") @PrimaryKey var id: String,
//    @SerializedName("login") val login: String,
//    @SerializedName("token") var token: String,
//    @SerializedName("last_fetch") @ColumnInfo(name = "lastFetch") var lastFetch: Long = Calendar.getInstance().timeInMillis
////    @Relation(parentColumn = "email", entityColumn = "userEmail")
////    val repos: List<Repo>
//) : JSONConvertable {
//
//}

data class User(
    var id: String,
    val login: String,
    var token: String,
    var lastFetch: Long = Calendar.getInstance().timeInMillis
)
