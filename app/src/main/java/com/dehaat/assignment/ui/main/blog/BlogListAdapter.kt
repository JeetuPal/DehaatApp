package com.dehaat.assignment.ui.main.blog

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.RequestManager

import com.dehaat.assignment.R
import com.dehaat.assignment.api.main.responses.BooksSearchResponse
import com.dehaat.assignment.models.BlogPost
import com.dehaat.assignment.util.GenericViewHolder
import kotlinx.android.synthetic.main.layout_blog_list_item.view.*

class BlogListAdapter(
    private val requestManager: RequestManager,
    private val interaction: Interaction? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG: String = "AppDebug"
    private val NO_MORE_RESULTS = -1
    private val BLOG_ITEM = 0
    private val EMPTY_BOOKS = listOf<BooksSearchResponse>()
    private val NO_MORE_RESULTS_BLOG_MARKER = BlogPost(
        NO_MORE_RESULTS,
        "",
        "",
        EMPTY_BOOKS
    )

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BlogPost>() {

        override fun areItemsTheSame(oldItem: BlogPost, newItem: BlogPost): Boolean {
            return oldItem.pk == newItem.pk
        }

        override fun areContentsTheSame(oldItem: BlogPost, newItem: BlogPost): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(
        BlogRecyclerChangeCallback(this),
        AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
    )

    internal inner class BlogRecyclerChangeCallback(
        private val adapter: BlogListAdapter
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

        when(viewType){
            NO_MORE_RESULTS -> {
                Log.e(TAG, "onCreateViewHolder: No more results...")
                return GenericViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.layout_no_more_results,
                        parent,
                        false
                    )
                )
            }
            BLOG_ITEM -> {
                return BlogViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_blog_list_item, parent, false),
                    interaction = interaction,
                    requestManager = requestManager
                )
            }
            else -> {
                return BlogViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.layout_blog_list_item, parent, false),
                    interaction = interaction,
                    requestManager = requestManager
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BlogViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (differ.currentList.get(position).pk > -1){
            return BLOG_ITEM
        }
        return differ.currentList.get(position).pk // -1
    }

    fun submitList(list: List<BlogPost>?, isQueryExhausted: Boolean) {
        val newList= list?.toMutableList()
        if (isQueryExhausted){
            newList?.add(NO_MORE_RESULTS_BLOG_MARKER)
        }
        differ.submitList(list)
    }

    class BlogViewHolder
    constructor(
        itemView: View,
        val requestManager: RequestManager,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: BlogPost) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            itemView.blog_title.text = item.authorName
            itemView.blog_author.text = item.authorBio
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: BlogPost)
    }
}

