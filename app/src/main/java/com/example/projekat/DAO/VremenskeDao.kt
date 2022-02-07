package com.example.projekat.DAO

import androidx.room.*
import com.example.projekat.entity.TipoviAktivnosti
import com.example.projekat.entity.Vremenske

@Dao
interface VremenskeDao {

    //suspend - da ne ometa glavu radnju prilikom izvšavanja
    //IGNORE - prije inserta pretrazi se tabela i ako ima kolona s aistim podacima ignorise unos
    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insert(vremenske : Vremenske)

    //REPLACE - prije inserta pretrazi se tabela i ako ima kolona s aistim podacima (npr. id isti) zamijeni se
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(vremenske: Vremenske)

    @Query("SELECT * FROM vremenske")
    suspend fun getAll() : List<Vremenske>

    @Query("DELETE FROM vremenske")
    suspend fun deleteAll()

    @Query("DELETE FROM vremenske WHERE id = :id_V")
    suspend fun deleteId(id_V : Int)

    @Update
    suspend fun update(vremenske: Vremenske?)

    @Query("SELECT id FROM vremenske ORDER BY id DESC LIMIT 1")
    fun getLastId() : Int

    @Query("SELECT * FROM vremenske WHERE id = :id_V")
    suspend fun getById(id_V : Int) : List<Vremenske>

    @Query("SELECT * FROM vremenske WHERE id_aktivnosti = :id")
    fun getVremenskaByIdAktivnosti(id : Int) : Vremenske

    @Query("SELECT pocetak FROM vremenske WHERE id_aktivnosti = :id")
    fun getVrijemePocetkaByIdAktivnosti(id : Int) : String

    @Query("SELECT kraj FROM vremenske WHERE id_aktivnosti = :id")
    fun getVrijemeKrajaByIdAktivnosti(id : Int) : String

    @Query("UPDATE vremenske SET pocetak= :vrijeme WHERE id_aktivnosti = :id")
    fun updatePocetak(vrijeme : String, id : Int)

    @Query("UPDATE vremenske SET kraj= :vrijeme WHERE id_aktivnosti = :id")
    fun updateKraj(vrijeme : String, id : Int)

}