package miu.compro.cs743.myapplication.base

import miu.compro.cs743.myapplication.util.ResponseHandler
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseRepository : KoinComponent {
    private val responseHandler : ResponseHandler by inject()

    suspend fun <T> safeApi(callApi: suspend () -> T): BaseApiResult<T> {
        try {
            callApi.invoke().let { response ->
                return responseHandler.handleSuccess(response)
            }
        } catch (ex: Exception) {
            return responseHandler.handleException(ex)
        }
    }
}