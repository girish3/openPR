package com.girish.openpr

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.girish.openpr.model.data.Author
import com.girish.openpr.model.data.PullRequest
import com.girish.openpr.model.repository.ProjectRepository
import com.girish.openpr.viewmodel.PRViewModel
import io.reactivex.Observable
import org.junit.Test
import com.girish.openpr.viewmodel.PRViewModel.UIState.SUCCESS
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers

import org.junit.Assert.*
import org.mockito.Mockito.*
import org.junit.BeforeClass
import org.junit.Rule
import org.mockito.ArgumentMatchers

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PRViewModelTest {

    companion object {

        @BeforeClass
        fun setup() {
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        }
    }

    @get:Rule
    val rule = InstantTaskExecutorRule()

    /* After successfully fetching results, the method test whether SUCCESS UI state is
     * set properly with the list of Pull Requests
     */
    @Test
    fun success_ui_state_isCorrect() {
        val mockRepository = mock(ProjectRepository::class.java)
        val viewModel = PRViewModel(mockRepository)

        // dummy pull request object list
        val list = ArrayList<PullRequest>()
        list.add(PullRequest(Author("", "", ""), "", "", "", ""))
        list.add(PullRequest(Author("girish", "abc", ""), "", "", "", ""))

        `when`(mockRepository.getPullRequests(anyString(), anyString())).thenReturn(Observable.just(list))
        viewModel.fetchPullRequest("", "")
        assertEquals(viewModel.getUIState().value, SUCCESS(list))
    }

    @Test
    fun empty_ui_state_isCorrect() {
        val mockRepository = mock(ProjectRepository::class.java)
        val viewModel = PRViewModel(mockRepository)

        // dummy pull request object list
        val list = ArrayList<PullRequest>()

        `when`(mockRepository.getPullRequests(anyString(), anyString())).thenReturn(Observable.just(list))
        viewModel.fetchPullRequest("", "")
        assertTrue(viewModel.getUIState().value is PRViewModel.UIState.EMPTY)
    }
}
