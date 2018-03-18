package com.dmanluc.githubrepos.domain.interactor

import android.util.Log
import io.reactivex.observers.DisposableSingleObserver
import retrofit2.HttpException

/**
 * Base Single Observer for Reactive Programming
 *
 * @author Daniel Manrique Lucas
 */
open class BaseObserver<T> : DisposableSingleObserver<T>() {

    override fun onError(e: Throwable) {
        if (e is HttpException) {
            val code = e.code()
            val message = e.message
            Log.i(TAG, "code : $code")
            onErrorMessage(message)
        } else {
            onErrorMessage(e.message)
        }
    }

    override fun onSuccess(t: T) {}

    open fun onErrorMessage(errorMessage: String?) {}

    companion object {
        private val TAG = this::class.java.simpleName
    }
}
