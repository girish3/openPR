package com.girish.openpr.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.girish.openpr.model.data.PullRequest
import com.girish.openpr.viewmodel.PRViewModel
import kotlinx.android.synthetic.main.activity_main.*
import android.view.inputmethod.InputMethodManager
import com.girish.openpr.Injector
import com.girish.openpr.R

class MainActivity : AppCompatActivity() {

    lateinit var viewModel : PRViewModel
    lateinit var adapter: PRListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectDependencies()
        setUpRecyclerView()

        viewModel.getUIState().observe(this, Observer {
            when(it) {
                is PRViewModel.UIState.SUCCESS -> onPRFetched(it.pullRequests)
                is PRViewModel.UIState.LOADING -> showLoadingView()
                is PRViewModel.UIState.EMPTY -> showEmptyView()
                is PRViewModel.UIState.ERROR -> showErrorView(it.message)
            }
        })

        repoEditText.setOnEditorActionListener(this::onEnterClick)
        ownerEditText.setOnEditorActionListener(this::onEnterClick)
    }

    private fun setUpRecyclerView() {
        prRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PRListAdapter()
        prRecyclerView.adapter = adapter

        val layoutManager = prRecyclerView.layoutManager as LinearLayoutManager
        prRecyclerView.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                viewModel.onListScrolled(lastVisibleItem, totalItemCount)
            }
        })
    }

    private fun showErrorView(message: String?) {
        hideAllViews()
        errorView.text = if (message != null) message else getString(R.string.default_error_message)
        errorView.visibility = View.VISIBLE
    }

    fun onEnterClick(v: View, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_SEARCH) {
            val owner = ownerEditText.text.toString()
            val repo = repoEditText.text.toString()
            viewModel.fetchPullRequest(owner, repo)
            hideKeyboard()
            return true
        }

        return false
    }

    private fun showEmptyView() {
        hideAllViews()
        emptyView.visibility = View.VISIBLE
    }

    private fun showLoadingView() {
        hideAllViews()
        loadingView.visibility = View.VISIBLE
    }

    private fun onPRFetched(pullRequests: List<PullRequest>) {
        hideAllViews()
        adapter.submitList(pullRequests)
        prRecyclerView.visibility = View.VISIBLE
    }

    private fun injectDependencies() {
        viewModel = ViewModelProviders.of(this, Injector.provideViewModelFactory()).get(PRViewModel::class.java)
    }

    private fun hideAllViews() {
        startView.visibility = View.GONE
        prRecyclerView.visibility = View.GONE
        emptyView.visibility = View.GONE
        loadingView.visibility = View.GONE
        errorView.visibility = View.GONE
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(repoEditText.getWindowToken(), 0)
    }
}
