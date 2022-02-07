package com.example.projekat.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "aktivnosti",
    foreignKeys = arrayOf(
        ForeignKey(entity = Kategorije::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("id_kategorije"),
            onDelete = ForeignKey.CASCADE
        )))
data class Aktivnosti (

    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    @ColumnInfo(name = "naziv")
    val naziv : String,

    @ColumnInfo(name = "id_kategorije")
    val id_kategorije : Int

)
