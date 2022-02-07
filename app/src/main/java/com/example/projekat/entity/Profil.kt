package com.example.projekat.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "profil")
data class Profil (

    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    @ColumnInfo(name = "ime")
    val ime : String,

    @ColumnInfo(name = "prezime")
    val prezime : String,

    @ColumnInfo(name = "oKorisniku")
    val oKorisniku : String
)
