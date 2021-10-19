package miu.compro.cs743.myapplication.util

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import miu.compro.cs743.myapplication.model.enum.CurrentTab
import miu.compro.cs743.myapplication.model.roomdb.entity.User

class NewsAppSharedPreference(context: Context) {
    private val  PREF_NAME = "shared_pref_news_app"
    private val PREF_IS_LOG_IN = "is_login"
    private val CURRENT_TAB = "current_tab"
    private val CURRENT_USER = "current_user"

    private val sharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)


    fun setIsLogIn(isLogin: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(PREF_IS_LOG_IN, isLogin)
        editor.apply()
    }


    fun getIsLogIn(): Boolean {
        return sharedPreferences.getBoolean(PREF_IS_LOG_IN, false)
    }

    fun setCurrentUser(user: User) {
        val userString = Gson().toJson(user)
        val editor = sharedPreferences.edit()
        editor.putString(CURRENT_USER,userString)
        editor.apply()
    }


    fun getCurrentUser(): User? {
        val userString: String? =  sharedPreferences.getString(CURRENT_USER, null)
        return Gson().fromJson(userString, User::class.java) ?: null
    }

    fun setCurrentTab(currentTab: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(CURRENT_TAB, currentTab)
        editor.apply()
    }


    fun getCurrentTab(): String? {
        return sharedPreferences.getString(CURRENT_TAB, null)
    }

    fun removeAllData() {
        sharedPreferences.edit {
            remove(PREF_IS_LOG_IN)
            remove(CURRENT_TAB)
            remove(CURRENT_USER)
        }
    }
}

