package net.plantix.demo.data.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import net.plantix.demo.data.model.ModelUserName

@Database(entities = [(ModelUserName::class)], version = 1)
abstract class CentralDB :RoomDatabase(){
    abstract fun usernameDao() : UsernameDao
}