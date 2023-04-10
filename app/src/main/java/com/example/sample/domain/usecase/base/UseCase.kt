package com.example.sample.domain.usecase.base

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import timber.log.Timber

abstract class UseCase<in P, R>(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    open suspend operator fun invoke(params: P): Result<R> {
        return try {
            withContext(context = coroutineDispatcher) {
                execute(params).let {
                    Result.success(value = it)
                }
            }
        } catch (e: CancellationException) {
            Timber.w(e)
            throw e
        } catch (e: Exception) {
            Timber.e(e)
            Result.failure(e)
        } catch (e: Throwable) {
            Timber.e(e)
            throw e
        }
    }

    protected abstract suspend fun execute(params: P): R
}