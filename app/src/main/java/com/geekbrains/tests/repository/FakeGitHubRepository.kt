package com.geekbrains.tests.repository

import com.geekbrains.tests.model.SearchResponse
import com.geekbrains.tests.presenter.RepositoryContract
import retrofit2.Response

class FakeGitHubRepository : RepositoryContract {

    override fun searchGithub(
        query: String,
        callback: RepositoryCallback
    ) {
        callback.handleGitHubResponse(Response.success(SearchResponse(FAKE_RESPONSE_REPO_TOTAL_COUNT, listOf())))
    }

    companion object{
        const val FAKE_RESPONSE_REPO_TOTAL_COUNT = 84
    }
}