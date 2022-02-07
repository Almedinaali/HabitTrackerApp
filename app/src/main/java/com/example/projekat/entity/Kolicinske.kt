package com.example.projekat.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "kolicinske",
    foreignKeys = arrayOf(
        ForeignKey(entity = Aktivnosti::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("id_aktivnosti"),
            onDelete = ForeignKey.CASCADE
        )
    ))

data class Kolicinske (

    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    @ColumnInfo(name = "id_aktivnosti")
    val id_aktivnosti : Int,

    @ColumnInfo(name = "kolicina")
    val kolicina : Int

)