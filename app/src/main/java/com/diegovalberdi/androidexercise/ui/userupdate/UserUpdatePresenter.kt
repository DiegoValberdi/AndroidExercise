package com.diegovalberdi.androidexercise.ui.userupdate

import com.diegovalberdi.androidexercise.data.remote.RemoteRepository
import com.diegovalberdi.androidexercise.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserUpdatePresenter(
    val view: UserUpdateView,
    val remoteRepository: RemoteRepository
) {

    fun onAcceptClicked(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            val resultMessage = remoteRepository.putOne(user)
            withContext(Dispatchers.Main) {
                view.showMessage(resultMessage)
            }
        }
    }
}

interface UserUpdateView {
    fun showMessage(message: String)
}