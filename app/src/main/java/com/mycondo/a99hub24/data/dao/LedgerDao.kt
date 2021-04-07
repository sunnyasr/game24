package com.mycondo.a99hub24.data.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.mycondo.a99hub24.model.Ledger

@Dao
interface LedgerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ledger: List<Ledger>)

    @Query("SELECT * FROM ledger")
    fun getLedger(): LiveData<List<Ledger>>

    @Update
    suspend fun update(ledger: Ledger)

    @Delete
    suspend fun delete(ledger: Ledger)

    @Query("DELETE FROM ledger")
    suspend fun allDelete()


}