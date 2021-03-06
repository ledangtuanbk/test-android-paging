package com.ldt.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RepoDao {
    @Query("select * from repos where (name like :queryString) or" +
            " (description like :queryString) order by stars desc, name asc")
    fun reposByName(queryString: String):LiveData<List<Repo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repos: List<Repo>)
}