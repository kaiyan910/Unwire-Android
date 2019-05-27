package hk.olleh.unwire.post.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hk.olleh.unwire.common.model.Post
import hk.olleh.unwire.post.databinding.HolderPostBinding

class PostListAdapter : ListAdapter<Post, PostListAdapter.ViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(HolderPostBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val bindings: HolderPostBinding
    ): RecyclerView.ViewHolder(bindings.root) {

        fun bind(post: Post) {

            bindings.item = post
            bindings.executePendingBindings()
        }
    }

    private class PostDiffCallback : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem.hashCode() == newItem.hashCode()
    }
}