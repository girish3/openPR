package com.girish.openpr.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.girish.openpr.R
import com.girish.openpr.model.data.PullRequest

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
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text = prList[position].author.username
    }


    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var textView: TextView

        init {
            textView = view.findViewById(R.id.text)

            textView.setOnClickListener {
                adapterPosition
            }
        }
    }
}