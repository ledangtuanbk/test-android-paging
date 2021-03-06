package com.ldt.myapplication

import android.util.Log
import androidx.lifecycle.LiveData
import java.util.concurrent.Executor

class GithubLocalCache(
    private val repoDao: RepoDao,
    private val ioExecutor: Executor
) {

    companion object{
        private const val TAG = "GithubLocalCache";
    }

    fun insert(repos:List<Repo>, insertFinished: ()->Unit){
        ioExecutor.execute {
            Log.d(TAG, "inserting ${repos.size} repos")
            repoDao.insert(repos)
            insertFinished()
        }
    }

    fun reposByName(name:String):LiveData<List<Repo>>{
        val query = "%${name.replace(' ', '%')}%"
        return repoDao.reposByName(query)
    }

}
