package miu.compro.cs743.myapplication.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


inline fun <reified T> fromJson(json: String): T {
    return Gson().fromJson(json, object: TypeToken<T>(){}.type)
}

inline fun <reified T> fromJson(any: Any): T {
    return Gson().fromJson(Gson().toJson(any), object: TypeToken<T>(){}.type)
}

fun toJson(any: Any) = Gson().toJson(any)