package com.example.projekat.DAO

import androidx.room.*
import com.example.projekat.entity.Kolicinske
import com.example.projekat.entity.TipoviAktivnosti
import com.example.projekat.entity.Vremenske

@Dao
interface KolicinskeDao {

    //suspend - da ne ometa glavu radnju prilikom izvšavanja
    //IGNORE - prije inserta pretrazi se tabela i ako ima kolona s aistim podacima ignorise unos
    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insert(kolicinske: Kolicinske)

    //REPLACE - prije inserta pretrazi se tabela i ako ima kolona s aistim podacima (npr. id isti) zamijeni se
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(kolicinske: Kolicinske)

    @Query("SELECT * FROM kolicinske")
    suspend fun getAll() : List<Kolicinske>

    @Query("DELETE FROM kolicinske")
    suspend fun deleteAll()

    @Query("DELETE FROM kolicinske WHERE id = :id_K")
    suspend fun deleteId(id_K : Int)

    @Update
    suspend fun update(kolicinske: Kolicinske?)

    @Query("SELECT id FROM kolicinske ORDER BY id DESC LIMIT 1")
    fun getLastId() : Int

    @Query("SELECT * FROM kolicinske WHERE id = :id_K")
    suspend fun getById(id_K : Int) : List<Kolicinske>

    @Query("SELECT kolicina FROM kolicinske WHERE id_aktivnosti = :id_K")
    fun getKolicinaById(id_K : Int) : Int

    @Query("SELECT * FROM kolicinske WHERE id_aktivnosti = :id")
    fun getKolicinskaByIdAktivnosti(id : Int) : Kolicinske

    @Query("UPDATE kolicinske SET kolicina= :kolicina WHERE id_aktivnosti = :id_I")
    fun updateKolicina(kolicina : Int, id_I : Int)
}