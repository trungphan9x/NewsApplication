package miu.compro.cs743.myapplication.model.remote.response

import java.io.Serializable

data class Source(
    val id: String?,
    val name: String?
) : Serializable