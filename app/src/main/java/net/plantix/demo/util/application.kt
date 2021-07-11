package net.plantix.demo.util

import android.app.Application
import androidx.room.Room
import net.plantix.demo.data.DB.CentralDB

class application : Application() {

    companion object {
        var database: CentralDB? = null
    }

    override fun onCreate() {
        super.onCreate()
        database =  Room.databaseBuilder(applicationContext, CentralDB::class.java, "plantix_local_db").fallbackToDestructiveMigration().build()
    }
}