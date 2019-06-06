package com.girish.openpr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.girish.openpr.model.data.PullRequest
import com.girish.openpr.viewmodel.PRViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel : PRViewModel

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
    }

    private fun handleResults(pullRequest: List<PullRequest>) {
        println(pullRequest.toString())
    }

    private fun handleError(throwable: Throwable) {
        println(throwable.toString())
    }

    private fun injectDependencies() {
        viewModel = ViewModelProviders.of(this).get(PRViewModel::class.java)
    }
}
