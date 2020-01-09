package io.github.hidey.sample.remotedata.repository

import com.github.kittinunf.result.Result
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SampleRepository {
  private val itemList = (1..10).map { "item$it" }

  fun getSample(): Single<Result<SampleEntity, Exception>> = Single.just("")
    .observeOn(Schedulers.io())
    .delay(300, TimeUnit.MILLISECONDS)
    .map { SampleEntity("Sample", itemList) }
    .map { Result.of(it) }
    .onErrorReturn { Result.error(Exception(it)) }
}
