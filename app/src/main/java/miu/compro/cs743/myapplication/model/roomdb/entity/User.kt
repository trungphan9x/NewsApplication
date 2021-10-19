package miu.compro.cs743.myapplication.model.roomdb.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "firstname") val firstname: String?,
    @ColumnInfo(name = "lastname") val lastname: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "password") val password: String?
    ) : Parcelable {
    constructor(firstname: String?, lastname: String?, email: String?, password: String?) : this(0, firstname, lastname, email, password)
}