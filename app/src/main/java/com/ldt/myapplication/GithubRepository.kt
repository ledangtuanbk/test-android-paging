package com.ldt.myapplication

import android.util.Log
import androidx.lifecycle.MutableLiveData

class GithubRepository(
    private val service: GithubService,
    private val cache: GithubLocalCache
) {

    // keep the last requested page. when the request is successful, increment the page number
    private var lastRequestPage = 1

    private var isRequestInProgress = false

    private val networkErrors = MutableLiveData<String>()

    fun search(query: String): RepoSearchResult {
        Log.d(TAG, "new query $query")
        lastRequestPage = 1
        requestAndSaveData(query)
        val data = cache.reposByName(query)
        return RepoSearchResult(data, networkErrors)
    }

    private fun requestAndSaveData(query: String) {
        if (isRequestInProgress) return
        isRequestInProgress = true
        searchRepos(service, query, lastRequestPage, NETWORK_PAGE_SIZE, { repos ->
            cache.insert(repos) {
                lastRequestPage++
                isRequestInProgress = false
            }
        }, { error ->
            networkErrors.postValue(error)
            isRequestInProgress = false
        })

    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
        private const val TAG = "GithubRepository"
    }
}

