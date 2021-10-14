package miu.compro.cs743.myapplication.base

import miu.compro.cs743.myapplication.model.enum.Status

data class BaseApiResult<out T> (val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): BaseApiResult<T> {
            return BaseApiResult(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): BaseApiResult<T> {
            return BaseApiResult(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): BaseApiResult<T> {
            return BaseApiResult(Status.LOADING, data, null)
        }
    }
}