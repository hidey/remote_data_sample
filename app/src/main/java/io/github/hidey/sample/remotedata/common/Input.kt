package io.github.hidey.sample.remotedata.common

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class Input<T> {
  private val subject: Subject<T> = PublishSubject.create()
  val value: Observable<T> = subject.hide()
  fun input(value: T) = subject.onNext(value)
}
