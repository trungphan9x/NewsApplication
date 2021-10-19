package miu.compro.cs743.myapplication.model.roomdb.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import miu.compro.cs743.myapplication.util.fromJson
import miu.compro.cs743.myapplication.util.toJson
import javax.xml.transform.Source

class ObjectConverters {
//
//    @TypeConverter
//    fun fromSource(any: Source?): String? = if (any==null) null else Gson().toJson(any)
//
//    @TypeConverter
//    fun toSource(string: String?): Source? = if (string.isNullOrEmpty()) null else Gson().fromJson(string, Source::class.java)

    @TypeConverter
    fun fromSource(any: Source?): String? = if (any==null) null else toJson(any)

    @TypeConverter
    fun toSource(string: String?): Source? = if (string.isNullOrEmpty()) null else fromJson(string)
}