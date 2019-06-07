package com.girish.openpr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.girish.openpr.model.data.PullRequest
import com.girish.openpr.view.PRListAdapter
import com.girish.openpr.viewmodel.PRViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel : PRViewModel
    lateinit var adapter: PRListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO: should you create an injector interface if time doesn't permit to use Dagger?
        injectDependencies()

        getButton.setOnClickListener {
            val owner = ownerEditText.text.toString()
            val repo = repoEditText.text.toString()

            viewModel.getPullRequests(owner, repo)
                .subscribe(this@MainActivity::handleResults, this@MainActivity::handleError)
        }

        // init recyclerview
        // TODO: should you be initializing reycler view here?
        prRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PRListAdapter()
        prRecyclerView.adapter = adapter
    }

    private fun handleResults(pullRequests: List<PullRequest>) {
        //println(pullRequests.toString())
        adapter.setItems(pullRequests)
    }

    private fun handleError(throwable: Throwable) {
        println(throwable.toString())
    }

    private fun injectDependencies() {
        viewModel = ViewModelProviders.of(this).get(PRViewModel::class.java)
    }
}
