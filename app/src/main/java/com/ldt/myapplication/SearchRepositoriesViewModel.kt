package com.ldt.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class SearchRepositoriesViewModel(private val repository: GithubRepository) : ViewModel() {

    private val queryLiveData = MutableLiveData<String>()
    private val repoResult:LiveData<RepoSearchResult> = Transformations.map(queryLiveData){
        repository.search(it)
    }

    fun searchRepo(queryString: String) {
        queryLiveData.postValue(queryString)
    }
}