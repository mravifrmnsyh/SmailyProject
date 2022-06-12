package com.capstone.smaily.preferences

import android.content.Context
import com.capstone.smaily.model.ChildrenLoginModel

class ChildrenLoginPref(context: Context) {
    private val pref = context.getSharedPreferences(CHILDREN_LOGIN, Context.MODE_PRIVATE)
    private val editor = pref.edit()

    fun setUser(message: String, childrenId: String, parentId: String, accessToken: String) {
        editor.putString(MESSAGE, message)
        editor.putString(CHILDRENID, childrenId)
        editor.putString(PARENTID, parentId)
        editor.putString(ACCESSTOKEN, accessToken)
        editor.apply()
    }

    fun getUser(): ChildrenLoginModel {
        val model = ChildrenLoginModel()
        model.message = pref.getString(MESSAGE, "").toString()
        model.childrenId = pref.getString(CHILDRENID, "").toString()
        model.parentId = pref.getString(PARENTID, "").toString()
        model.accessToken = pref.getString(ACCESSTOKEN, "").toString()
        return model
    }

    fun delUser() {
        editor.clear()
        editor.apply()
    }

    companion object {
        const val CHILDREN_LOGIN = "children_login"
        const val MESSAGE = "message"
        const val CHILDRENID = "children_id"
        const val PARENTID = "parent_id"
        const val ACCESSTOKEN = "access_token"
    }
}