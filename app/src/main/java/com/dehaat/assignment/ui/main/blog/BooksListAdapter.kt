package com.dehaat.assignment.ui.main.blog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.RequestManager

import com.dehaat.assignment.R
import com.dehaat.assignment.api.main.responses.BooksSearchResponse
import kotlinx.android.synthetic.main.layout_book_list_item.view.*

class BooksListAdapter(
    private val requestManager: RequestManager,
    private val interaction: Interaction? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BooksSearchResponse>() {

        override fun areItemsTheSame(
            oldItem: BooksSearchResponse,
            newItem: BooksSearchResponse
        ): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: BooksSearchResponse,
            newItem: BooksSearchResponse
        ): Boolean {
            return oldItem.equals(newItem)
        }

    }
    private val differ = AsyncListDiffer(
        BookRecyclerChangeCallback(this),
        AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
    )


    internal inner class BookRecyclerChangeCallback(
        private val adapter: BooksListAdapter
    ) : ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) {
            adapter.notifyItemRangeChanged(position, count, payload)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            adapter.notifyDataSetChanged()
        }

        override fun onInserted(position: Int, count: Int) {
            adapter.notifyItemRangeChanged(position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return BookViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_book_list_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BookViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<BooksSearchResponse>) {
        differ.submitList(list)
    }

    class BookViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: BooksSearchResponse) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            itemView.book_title.text = item.title
            itemView.book_description.text = item.description
            itemView.book_publisher.text = item.publisher
            itemView.book_price.text = "Price Rs. " + item.price
            itemView.book_published_date.text = item.publishedDate
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: BooksSearchResponse)
    }
}

