package com.example.projekat.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "kategorije",
    foreignKeys = arrayOf(
        ForeignKey(entity = TipoviAktivnosti::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("tip"),
            onDelete = ForeignKey.CASCADE
        )
    ))
data class Kategorije (

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "naziv")
    val naziv: String,

    @ColumnInfo(name = "tip")
    val tip: Int,

    @ColumnInfo(name = "osobina")
    val osobina: String

)