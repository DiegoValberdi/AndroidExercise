package com.diegovalberdi.androidexercise.ui.userlist

import com.diegovalberdi.androidexercise.data.remote.RemoteRepository
import com.diegovalberdi.androidexercise.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserListPresenter(val view: UserListView, val remoteRepository: RemoteRepository) {

    fun init() {
        getAllUsers()
    }

    fun searchUserById(userId: Int?) {
        if (userId == null) {
            view.showMessage("Introduce an id for me to do my job please")
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val user = remoteRepository.getOne(userId)
                withContext(Dispatchers.Main) {
                    if (user != null) {
                        val users = listOf(user)
                        view.showUsers(users)
                    } else {
                        view.showMessage("This app's creator apologize for the technical problems")
                    }
                }
            }
        }
    }

    fun getAllUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            val users = remoteRepository.getAll()
            withContext(Dispatchers.Main) {
                if (users != null) {
                    view.showUsers(users)
                } else {
                    view.showMessage("This app's creator apologize for the technical problems")
                }
            }
        }
    }

    fun deleteSelectedUser(userId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val resultMessage = remoteRepository.deleteOne(userId)
            withContext(Dispatchers.Main) {
                view.showMessage(resultMessage)
                getAllUsers()
            }
        }
    }

    fun goToUserUpdate(user: User) {
        view.openUserUpdate(user)
    }
}

interface UserListView {
    fun showUsers(users: List<User>)
    fun openUserUpdate(user: User)
    fun showMessage(message: String)
}