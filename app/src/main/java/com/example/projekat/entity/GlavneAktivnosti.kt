package com.example.projekat.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "glavneAktivnosti",
    foreignKeys = arrayOf(
        ForeignKey(entity = Kategorije::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("id_kategorije"),
            onDelete = ForeignKey.CASCADE
        )))
data class GlavneAktivnosti (

    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    @ColumnInfo(name = "naziv")
    val naziv : String,

    @ColumnInfo(name = "broj")
    var broj : Int,

    @ColumnInfo(name = "inkrement")
    val inkrement : Int,

    @ColumnInfo(name = "unos")
    var unos : Int,

    @ColumnInfo(name = "proteklo_vrijeme")
    var proteklo_vrijeme : Long?,

    @ColumnInfo(name = "mjerna_jedinica")
    val mjerna_jedinica : String,

    @ColumnInfo(name = "id_kategorije")
    val id_kategorije : Int

)