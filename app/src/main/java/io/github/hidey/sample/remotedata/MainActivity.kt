package io.github.hidey.sample.remotedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.mercari.remotedata.RemoteData
import io.github.hidey.sample.remotedata.repository.SampleEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
  private val inputs = SampleInputs()
  private val viewModel = SampleViewModel(inputs)

  private val compositeDisposable = CompositeDisposable()

  private lateinit var adapter: SampleAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    lifecycle.addObserver(viewModel)
    setupView()
    bindView()

  }

  private fun setupView() {
    adapter = SampleAdapter()
    recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    recyclerView.adapter = adapter
  }

  private fun bindView() {
    compositeDisposable.add(
      viewModel.states.map(SampleState::entity)
        .distinctUntilChanged()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::updateView)
    )
  }

  private fun updateView(entity: RemoteData<SampleEntity, Exception>) {
    when (entity) {
      is RemoteData.Success -> {
        titleView.text = entity.value.title
        adapter.items = entity.value.items
      }
      is RemoteData.Failure -> errorMessage.text = entity.error.localizedMessage
    }
    errorView.isVisible = entity.isFailure
    loadingView.isVisible = entity.isLoading
  }

  override fun onStart() {
    super.onStart()
    inputs.load.input(Unit)
  }

  override fun onDestroy() {
    lifecycle.removeObserver(viewModel)
    compositeDisposable.clear()
    super.onDestroy()
  }

  class SampleAdapter : RecyclerView.Adapter<SampleViewHolder>() {
    var items: List<String> = emptyList()
      set(value) {
        field = value
        notifyDataSetChanged()
      }

    private fun inflateItemView(parent: ViewGroup) = LayoutInflater.from(parent.context)
      .inflate(R.layout.item_view, parent, false)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
      SampleViewHolder(inflateItemView(parent))

    override fun getItemCount() = items.count()

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
      holder.bind(items[position])
    }
  }

  class SampleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val textView = view.findViewById<TextView>(R.id.text)
    fun bind(text: String) {
      textView.text = text
    }
  }
}
