package com.example.sample.domain.usecase.base

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

abstract class FlowUseCase<in P, R>(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(params: P): Result<Flow<R>> {

        return try {
            Result.success(
                value = execute(params)
                    .catch { e -> Result.failure<R>(e) }
                    .flowOn(coroutineDispatcher)
            )
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

    protected abstract fun execute(params: P): Flow<R>
}
