package com.mycondo.a99hub24.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mycondo.a99hub24.model.InPlayGame

@Dao
interface InPlayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: ArrayList<InPlayGame>)

    @Query("SELECT * FROM inPlayGame")
    fun getInPlayGame(): LiveData<List<InPlayGame>>

    @Update
    suspend fun update(note: InPlayGame)

    @Delete
    suspend fun delete(note: InPlayGame)

    @Query("DELETE FROM inPlayGame")
    suspend fun allDelete()


}