package com.girish.openpr.view

import androidx.recyclerview.widget.DiffUtil
import com.girish.openpr.R
import com.girish.openpr.model.data.PullRequest

class PRListAdapter : DataBindingAdapter<PullRequest>(PR_COMPARATOR) {

    companion object {
        private val PR_COMPARATOR = object : DiffUtil.ItemCallback<PullRequest>() {
            override fun areItemsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean =
                oldItem.prNumber == newItem.prNumber

            override fun areContentsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean =
                oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.pr_list_item
    }
}
