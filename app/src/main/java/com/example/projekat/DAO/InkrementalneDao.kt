package com.example.projekat.DAO

import androidx.room.*
import com.example.projekat.entity.Inkrementalne
import com.example.projekat.entity.TipoviAktivnosti
import com.example.projekat.entity.Vremenske

@Dao
interface InkrementalneDao {

    //suspend - da ne ometa glavu radnju prilikom izvšavanja
    //IGNORE - prije inserta pretrazi se tabela i ako ima kolona s aistim podacima ignorise unos
    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insert(inkrementalne: Inkrementalne)

    //REPLACE - prije inserta pretrazi se tabela i ako ima kolona s aistim podacima (npr. id isti) zamijeni se
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(inkrementalne: Inkrementalne)

    @Query("SELECT * FROM inkrementalne")
    suspend fun getAll() : List<Inkrementalne>

    @Query("DELETE FROM inkrementalne")
    suspend fun deleteAll()

    @Query("DELETE FROM inkrementalne WHERE id = :id_I")
    suspend fun deleteId(id_I : Int)

    @Update
    fun update(inkrementalne: Inkrementalne?)

    @Query("SELECT id FROM inkrementalne ORDER BY id DESC LIMIT 1")
    fun getLastId() : Int

    @Query("SELECT * FROM inkrementalne WHERE id = :id_I")
    suspend fun getById(id_I : Int) : List<Inkrementalne>

    @Query("SELECT * FROM inkrementalne WHERE id = :id_I")
    fun getAktivnostById(id_I : Int) : Inkrementalne

    @Query("SELECT * FROM inkrementalne WHERE id_aktivnosti = :id_I")
    fun getInkrementalnaByIdAktivnosti(id_I : Int) : Inkrementalne

    @Query("SELECT broj FROM inkrementalne WHERE id_aktivnosti = :id_I")
    fun getBrojByIdAktivnosti(id_I : Int) : Int

    @Query("SELECT inkrement FROM inkrementalne WHERE id_aktivnosti = :id_I")
    fun getInkrementByIdAktivnosti(id_I : Int) : Int

    @Query("UPDATE inkrementalne SET broj= :broj WHERE id_aktivnosti = :id_I")
    fun updateBroj(broj : Int, id_I : Int)
}