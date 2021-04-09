package com.mycondo.a99hub24.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.mycondo.a99hub24.data.database.Hub24Database
import com.mycondo.a99hub24.data.network.HomeApi
import com.mycondo.a99hub24.data.preferences.LimitPreferences
import com.mycondo.a99hub24.data.responses.LimitResponse
import com.mycondo.a99hub24.model.InPlayGame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: HomeApi,
    private val preferences: LimitPreferences
) : BaseRepository(api) {

    suspend fun getCoins(
        token: String
    ) = safeApiCall {
        api.getLimitCoins(token)
    }

    suspend fun getInPlay() = safeApiCall {
        api.getInPlay()
    }


    suspend fun saveCoins(token: LimitResponse) {
        preferences.store(token)
    }



    companion object {
        private var noteDatabase: Hub24Database? = null

        private fun initialiseDB(context: Context): Hub24Database? {
            return Hub24Database.getInstance(context)
        }

        fun insert(context: Context, note: ArrayList<InPlayGame>) {
            noteDatabase = initialiseDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                noteDatabase?.getInPlayDao()?.insert(note)
            }
        }

        fun getInPlayGame(context: Context): LiveData<List<InPlayGame>>? {
            noteDatabase = initialiseDB(context)
            return noteDatabase?.getInPlayDao()?.getInPlayGame()
        }

        fun update(context: Context, note: InPlayGame) {
            noteDatabase = initialiseDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                noteDatabase?.getInPlayDao()?.update(note)
            }
        }

//        fun search(context: Context, data: String): LiveData<List<Profile>>? {
//            noteDatabase = initialiseDB(context)
//            return noteDatabase?.getDao()?.search(data)
//        }

        fun delete(context: Context, note: InPlayGame) {
            noteDatabase = initialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                noteDatabase?.getInPlayDao()?.delete(note)
            }
        }

        fun allDelete(context: Context) {
            noteDatabase = initialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                noteDatabase?.getInPlayDao()?.allDelete()
            }
        }

//        suspend fun getPost(token:String): List<UGModel>{
//            = RetrofitBuilder.api.getAllPost()
//        }

    }

}

