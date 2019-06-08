package com.girish.openpr.view

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.girish.openpr.model.data.PullRequest

class PRListAdapter : ListAdapter<PullRequest, PRViewHolder>(PR_COMPARATOR) {

    var prList: List<PullRequest> = ArrayList()

    companion object {
        private val PR_COMPARATOR = object : DiffUtil.ItemCallback<PullRequest>() {
            override fun areItemsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean =
                oldItem.prNumber == newItem.prNumber

            override fun areContentsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int) : PRViewHolder {
        return PRViewHolder.create(parent)
    }

    fun setItems(items: List<PullRequest>) {
        prList = items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PRViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}