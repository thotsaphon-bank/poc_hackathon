package com.example.pochackathon.screen.profile

import com.airbnb.epoxy.Typed2EpoxyController
import com.example.pochackathon.model.User
import com.example.pochackathon.user

class ProfileController : Typed2EpoxyController<List<User>, ProfileController.Listener>() {
    override fun buildModels(users: List<User>?, listener: Listener?) {
        users?.forEach { user ->
            user {
                id(user.id)
                user(user)
                onClick { _ -> listener?.onSelectUser(user) }
            }
        }
    }

    interface Listener {
        fun onSelectUser(user: User)
    }
}
