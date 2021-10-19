package miu.compro.cs743.myapplication.model.remote.response

data class ErrorResponseBody (
    val status: String?,
    val code: String?,
    val message: String?
    )