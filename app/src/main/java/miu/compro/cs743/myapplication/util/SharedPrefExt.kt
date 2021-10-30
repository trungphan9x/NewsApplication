package miu.compro.cs743.myapplication.util

import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import miu.compro.cs743.myapplication.model.roomdb.entity.User


const val PREF_NAME = "shared_pref_news_app"
const val PREF_IS_LOG_IN = "is_login"
const val CURRENT_TAB = "current_tab"
const val CURRENT_USER = "current_user"
const val CURRENT_PICTURE = "current_picture"


fun Context.setIsLogIn(isLogin: Boolean) {
    val sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean(PREF_IS_LOG_IN, isLogin)
    editor.apply()
}


fun Context.getIsLogIn(): Boolean {
    val sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean(PREF_IS_LOG_IN, false)
}

fun Context.setCurrentUser(user: User) {
    val sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    val userString = Gson().toJson(user)
    val editor = sharedPreferences.edit()
    editor.putString(CURRENT_USER, userString)
    editor.apply()
}


fun Context.getCurrentUser(): User? {
    val sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    val userString: String? = sharedPreferences.getString(CURRENT_USER, null)
    return Gson().fromJson(userString, User::class.java) ?: null
}




fun Context.setCurrentUriPicture(uriPicture: Uri) {
    val sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString(CURRENT_PICTURE, uriPicture.toString())
    editor.apply()
}


fun Context.getCurrentUriPicture(): String? {
    val sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    return sharedPreferences.getString(CURRENT_PICTURE, null)
}




fun Context.setCurrentTab(currentTab: String?) {
    val sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString(CURRENT_TAB, currentTab)
    editor.apply()
}


fun Context.getCurrentTab(): String? {
    val sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    return sharedPreferences.getString(CURRENT_TAB, null)
}

fun Context.removeAllData() {
    val sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.remove(PREF_IS_LOG_IN)
    editor.remove(CURRENT_TAB)
    editor.remove(CURRENT_USER)
    editor.apply()
}


