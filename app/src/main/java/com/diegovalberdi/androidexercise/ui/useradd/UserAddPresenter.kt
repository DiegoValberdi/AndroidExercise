package com.diegovalberdi.androidexercise.ui.useradd

import com.diegovalberdi.androidexercise.data.remote.RemoteRepository
import com.diegovalberdi.androidexercise.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserAddPresenter(val view: UserAddView, val remoteRepository: RemoteRepository) {

    fun onAcceptClicked(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            val resultMessage = remoteRepository.postOne(user)
            withContext(Dispatchers.Main) {
                view.showMessage(resultMessage)
            }
        }
    }
}

interface UserAddView {
    fun showMessage(message:String)
}