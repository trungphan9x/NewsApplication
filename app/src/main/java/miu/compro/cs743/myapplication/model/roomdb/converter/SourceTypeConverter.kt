package miu.compro.cs743.myapplication.model.roomdb.converter

import androidx.room.TypeConverter
import miu.compro.cs743.myapplication.model.remote.response.Source
import org.json.JSONObject

class SourceTypeConverter {
    @TypeConverter
    fun fromSource(source: Source): String {
        return JSONObject().apply {
            put("id", source.id ?: "")
            put("name", source.name ?: "")
        }.toString()
    }

    @TypeConverter
    fun toSource(source: String): Source {
        val json = JSONObject(source)
        return Source(json.getString("id"), json.getString("name"))
    }
}