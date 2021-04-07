package com.mycondo.a99hub24.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mycondo.a99hub24.data.dao.InPlayDao
import com.mycondo.a99hub24.data.dao.LedgerDao
import com.mycondo.a99hub24.model.InPlayGame
import com.mycondo.a99hub24.model.Ledger

@Database(
    entities = [InPlayGame::class, Ledger::class],
    version = 7,
    exportSchema = false
)
abstract class Hub24Database : RoomDatabase() {

//    abstract fun getDao(): ProfileDao
//    abstract fun getCGDao(): CGDao
    abstract fun getInPlayDao(): InPlayDao
//    abstract fun getUpcomingGameDao(): UpcomingGameDao
    abstract fun getLedgerDao(): LedgerDao

    companion object {
        private const val DATABASE_NAME = "ProfileDatabase"

        @Volatile
        var instance: Hub24Database? = null

        fun getInstance(context: Context): Hub24Database? {
            if (instance == null) {
                synchronized(Hub24Database::class.java)
                {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context, Hub24Database::class.java,
                            DATABASE_NAME
                        ).allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return instance
        }
    }

}