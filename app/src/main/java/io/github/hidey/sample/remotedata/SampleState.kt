package io.github.hidey.sample.remotedata

import com.mercari.remotedata.RemoteData
import com.mercari.rxredux.State
import io.github.hidey.sample.remotedata.repository.SampleEntity

data class SampleState(
  val entity: RemoteData<SampleEntity, Exception> = RemoteData.Initial
) : State
