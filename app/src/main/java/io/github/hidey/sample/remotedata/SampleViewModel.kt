package io.github.hidey.sample.remotedata

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.mercari.rxredux.Store
import io.github.hidey.sample.remotedata.repository.SampleRepository
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class SampleViewModel(
  private val inputs: SampleInputs,
  private val repository: SampleRepository = SampleRepository()
) : LifecycleObserver {
  private val store = Store(SampleState(), SampleReducer())
  private val compositeDisposable = CompositeDisposable()

  val states: Observable<SampleState> = store.states

  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  fun onCreate() {
    compositeDisposable.addAll(
      store.dispatch(inputs.load.value.flatMap {
        repository.getSample().toObservable()
      }
        .map<SampleAction>(::ShowDataAction)
        .startWith(LoadData)
      )
    )
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  fun onStart() {
    inputs.load.input(Unit)
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  fun onCleared() {
    compositeDisposable.clear()
  }
}
