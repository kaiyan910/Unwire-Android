package hk.olleh.unwire.post.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hk.olleh.unwire.common.model.Post
import hk.olleh.unwire.post.R
import hk.olleh.unwire.post.databinding.HolderPostBinding
import hk.olleh.unwire.post.databinding.HolderPostVideoBinding

class PostListAdapter : ListAdapter<Post, RecyclerView.ViewHolder>(PostDiffCallback()) {

    var onItemClickListener: ((Post) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == R.layout.holder_post_video) {
            VideoViewHolder(HolderPostVideoBinding.inflate(inflater, parent, false))
        } else {
            ViewHolder(HolderPostBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is VideoViewHolder -> holder.bind(getItem(position), onItemClickListener)
            is ViewHolder -> holder.bind(getItem(position), onItemClickListener)
        }
    }

    override fun getItemViewType(position: Int): Int = if (getItem(position).isVideo) {
        R.layout.holder_post_video
    } else {
        R.layout.holder_post
    }

    class ViewHolder(
        private val bindings: HolderPostBinding
    ) : RecyclerView.ViewHolder(bindings.root) {

        fun bind(post: Post, onItemClickListener: ((Post) -> Unit)?) {

            bindings.item = post
            bindings.executePendingBindings()

            itemView.setOnClickListener { onItemClickListener?.invoke(post) }
        }
    }

    class VideoViewHolder(
        private val bindings: HolderPostVideoBinding
    ) : RecyclerView.ViewHolder(bindings.root) {

        fun bind(post: Post, onItemClickListener: ((Post) -> Unit)?) {

            bindings.item = post
            bindings.executePendingBindings()

            itemView.setOnClickListener { onItemClickListener?.invoke(post) }
        }
    }

    private class PostDiffCallback : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }
}