package com.ldt.myapplication

import androidx.lifecycle.LiveData

data class RepoSearchResult(
    val data: LiveData<List<Repo>>,
    val networkErrors: LiveData<String>
) {

}