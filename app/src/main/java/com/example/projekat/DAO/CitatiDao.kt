package com.example.projekat.DAO

import androidx.room.*
import com.example.projekat.entity.Citati
import com.example.projekat.entity.Profil

@Dao
interface CitatiDao {

    //suspend - da ne ometa glavu radnju prilikom izvšavanja
    //IGNORE - prije inserta pretrazi se tabela i ako ima kolona s aistim podacima ignorise unos
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(citati: Citati)

    //REPLACE - prije inserta pretrazi se tabela i ako ima kolona s aistim podacima (npr. id isti) zamijeni se
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(citati: Citati)

    @Query("SELECT * FROM citati")
    suspend fun getAll() : List<Citati>

    @Query("DELETE FROM citati")
    suspend fun deleteAll()

    @Query("DELETE FROM citati WHERE id = :id_C")
    suspend fun deleteId(id_C : Int)

    @Update
    suspend fun update(citati: Citati)

    @Query("SELECT citat FROM citati WHERE id = :id_C")
    fun getById(id_C : Int) : String

    @Query("SELECT MAX(id) FROM citati")
    fun getLastId() : Int
}