package com.ldt.myapplication

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import java.util.concurrent.Executors

object Injection {

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(provideGithubRepository(context))
    }

    private fun provideGithubRepository(context: Context): GithubRepository {
        return GithubRepository(GithubService.create(), provideCache(context))
    }

    private fun provideCache(context: Context): GithubLocalCache {
        val database = RepoDatabase.getInstance(context);
        return GithubLocalCache(database.repoDao(), Executors.newSingleThreadExecutor())
    }
}