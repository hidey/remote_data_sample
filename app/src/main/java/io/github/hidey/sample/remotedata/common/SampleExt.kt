package io.github.hidey.sample.remotedata.common

import com.github.kittinunf.result.Result
import com.mercari.remotedata.RemoteData


fun <V : Any, E : Exception> Result<V, E>.toRemoteData(): RemoteData<V, E> = when (this) {
  is Result.Success -> RemoteData.Success(this.value)
  is Result.Failure -> RemoteData.Failure(this.error)
}
