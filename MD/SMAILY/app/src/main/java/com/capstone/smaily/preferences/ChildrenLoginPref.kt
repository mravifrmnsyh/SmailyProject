package com.capstone.smaily.preferences

import android.content.Context
import com.capstone.smaily.model.ChildrenLoginModel
import com.capstone.smaily.model.ParentLoginModel

class ChildrenLoginPref(context: Context) {
    private val pref = context.getSharedPreferences(CHILDREN_LOGIN, Context.MODE_PRIVATE)
    private val editor = pref.edit()

    fun setUser(message: String, accessToken: String) {
        editor.putString(MESSAGE, message)
        editor.putString(ACCESSTOKEN, accessToken)
        editor.apply()
    }
    fun getUser(): ChildrenLoginModel {
        val model = ChildrenLoginModel()
        model.message = pref.getString(MESSAGE, "").toString()
        model.accessToken = pref.getString(ACCESSTOKEN, "").toString()
        return model
    }
    fun delUser() {
        editor.clear()
        editor.apply()
    }

    companion object{
        const val CHILDREN_LOGIN = "children_login"
        const val MESSAGE = "message"
        const val ACCESSTOKEN = "access_token"
    }
}