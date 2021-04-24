package com.mycondo.a99hub24.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.mycondo.a99hub24.data.database.Hub24Database
import com.mycondo.a99hub24.data.network.LedgerApi
import com.mycondo.a99hub24.model.Ledger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LedgerRepository  @Inject constructor(
    private val api: LedgerApi
) : BaseRepository(api) {

    suspend fun getLedger(
        token: String
    ) = safeApiCall {
        api.getLedger(token)
    }
    companion object {
        private var noteDatabase: Hub24Database? = null

        private fun initialiseDB(context: Context): Hub24Database? {
            return Hub24Database.getInstance(context)
        }

        fun insert(context: Context, note: List<Ledger>) {
            noteDatabase = initialiseDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                noteDatabase?.getLedgerDao()?.insert(note)
            }
        }

        fun getLedger(context: Context): LiveData<List<Ledger>>? {
            noteDatabase = initialiseDB(context)
            return noteDatabase?.getLedgerDao()?.getLedger()
        }

        fun update(context: Context, note: Ledger) {
            noteDatabase = initialiseDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                noteDatabase?.getLedgerDao()?.update(note)
            }
        }

//        fun search(context: Context, data: String): LiveData<List<Profile>>? {
//            noteDatabase = initialiseDB(context)
//            return noteDatabase?.getDao()?.search(data)
//        }

        fun delete(context: Context, note: Ledger) {
            noteDatabase = initialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                noteDatabase?.getLedgerDao()?.delete(note)
            }
        }

        fun allDelete(context: Context) {
            noteDatabase = initialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                noteDatabase?.getLedgerDao()?.allDelete()
            }
        }

//        suspend fun getPost(token:String): List<UGModel>{
//            = RetrofitBuilder.api.getAllPost()
//        }

    }

}

