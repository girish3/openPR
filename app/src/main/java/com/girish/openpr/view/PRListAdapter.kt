package com.girish.openpr.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.girish.openpr.R
import com.girish.openpr.model.data.PullRequest
import kotlinx.android.synthetic.main.pr_list_item.view.*

class PRListAdapter() : RecyclerView.Adapter<PRListAdapter.MyViewHolder>() {

    var prList: List<PullRequest> = ArrayList()

    // TODO: can the contructor parameter be moved to class header?
    constructor(items: List<PullRequest>) : this() {
        prList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int) : MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.pr_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return prList.size
    }

    fun setItems(items: List<PullRequest>) {
        prList = items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // TODO: Should I create bind method in view holder and move the logic there?
        with(holder) {
            val pullRequest = prList[position]
            titleView.text = pullRequest.title
            bodyView.text = pullRequest.body
            // TODO: use resource string
            infoView.text = "#${pullRequest.prNumber} opened by ${pullRequest.author.username}"
            // TODO: use glide to manage images
        }
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
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
    }
}