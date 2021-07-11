package net.plantix.demo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TableUserName")
data class ModelUserName (
    @PrimaryKey(autoGenerate = true)
    var UserId: Int = 0,
    var UserName : String
)