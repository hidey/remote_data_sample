package io.github.hidey.sample.remotedata

import com.mercari.remotedata.RemoteData
import com.mercari.rxredux.Reducer
import io.github.hidey.sample.remotedata.common.toRemoteData

class SampleReducer : Reducer<SampleState, SampleAction> {
  override fun reduce(currentState: SampleState, action: SampleAction): SampleState =
    when (action) {
      is ShowDataAction -> currentState.copy(entity = action.result.toRemoteData())
      is LoadData -> currentState.copy(entity = RemoteData.Loading())
    }
}
