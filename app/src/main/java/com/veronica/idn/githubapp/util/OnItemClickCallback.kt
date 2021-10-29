package com.veronica.idn.githubapp.util

import com.veronica.idn.githubapp.data.model.User

interface OnItemClickCallback {
    fun onItemClicked(user: User?)

}
