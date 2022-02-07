package com.example.projekat.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "mjerneJedinice",
    foreignKeys = arrayOf(
        ForeignKey(entity = Aktivnosti::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("id_aktivnosti"),
            onDelete = ForeignKey.CASCADE
        )
    ))
data class MjerneJedinice (

    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    @ColumnInfo(name = "naziv")
    val naziv : String,

    @ColumnInfo(name = "id_aktivnosti")
    val id_aktivnosti : Int
)