package io.github.hidey.sample.remotedata

import com.github.kittinunf.result.Result
import com.mercari.rxredux.Action
import io.github.hidey.sample.remotedata.repository.SampleEntity

sealed class SampleAction : Action

class ShowDataAction(val result: Result<SampleEntity, Exception>) : SampleAction()
object LoadData : SampleAction()
