package com.girish.openpr.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.girish.openpr.R
import com.girish.openpr.model.data.PullRequest

class PRViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var titleView: TextView
    var avatarView: ImageView
    var bodyView: TextView
    var infoView: TextView

    init {
        titleView = view.findViewById(R.id.title)
        avatarView = view.findViewById(R.id.avatar)
        bodyView = view.findViewById(R.id.body)
        infoView = view.findViewById(R.id.info)
    }

    fun bind(pr: PullRequest) {
        titleView.text = pr.title
        bodyView.text = pr.body
        // TODO: use resource string
        infoView.text = "#${pr.prNumber} opened by ${pr.author.username}"
        // TODO: use glide to manage images
    }

    companion object {
        fun create(parent: ViewGroup): PRViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.pr_list_item, parent, false)
            return PRViewHolder(view)
        }
    }
}