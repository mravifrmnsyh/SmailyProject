package com.capstone.smaily.preferences

import android.content.Context
import com.capstone.smaily.model.ParentLoginModel

class ParentLoginPref(context: Context) {
    private val pref = context.getSharedPreferences(PARENT_LOGIN, Context.MODE_PRIVATE)
    private val editor = pref.edit()

    fun setUser(id: String, name: String, email: String, accessToken: String) {
        editor.putString(ID, id)
        editor.putString(NAME, name)
        editor.putString(EMAIL, email)
        editor.putString(ACCESSTOKEN, accessToken)
        editor.apply()
    }

    fun setIdChild(idChild: String) {
        editor.putString(IDCHILDREN, idChild)
        editor.apply()
    }

    fun getUser(): ParentLoginModel {
        val model = ParentLoginModel()
        model.id = pref.getString(ID, "").toString()
        model.name = pref.getString(NAME, "").toString()
        model.email = pref.getString(EMAIL, "").toString()
        model.accesstoken = pref.getString(ACCESSTOKEN, "").toString()
        model.idChildren = pref.getString(IDCHILDREN, "").toString()
        return model
    }

    fun delUser() {
        editor.clear()
        editor.apply()
    }

    companion object {
        const val PARENT_LOGIN = "parent_login"
        const val ID = "id"
        const val NAME = "name"
        const val EMAIL = "email"
        const val ACCESSTOKEN = "access_token"
        const val IDCHILDREN = "id_children"
    }
}