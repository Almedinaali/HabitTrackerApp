package com.example.projekat.DAO

import androidx.room.*
import com.example.projekat.entity.MjerneJedinice
import com.example.projekat.entity.TipoviAktivnosti

@Dao
interface MjerneJediniceDao {

    //suspend - da ne ometa glavu radnju prilikom izvšavanja
    //IGNORE - prije inserta pretrazi se tabela i ako ima kolona s aistim podacima ignorise unos
    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insert(mjerneJedinice: MjerneJedinice)

    //REPLACE - prije inserta pretrazi se tabela i ako ima kolona s aistim podacima (npr. id isti) zamijeni se
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(mjerneJedinice: MjerneJedinice)

    @Query("SELECT * FROM mjerneJedinice")
    suspend fun getAll() : List<MjerneJedinice>

    @Query("DELETE FROM mjerneJedinice")
    suspend fun deleteAll()

    @Query("DELETE FROM mjerneJedinice WHERE id = :id_MJ")
    suspend fun deleteId(id_MJ : Int)

    @Update
    suspend fun update(mjerneJedinice: MjerneJedinice?)

    @Query("SELECT id FROM mjerneJedinice ORDER BY id DESC LIMIT 1")
    fun getLastId() : Int

    @Query("SELECT * FROM mjerneJedinice WHERE id = :id_MJ")
    suspend fun getById(id_MJ : Int) : MjerneJedinice

    @Query("SELECT naziv FROM mjerneJedinice WHERE id_aktivnosti = :id_MJ")
    fun getByIdAktivnosti(id_MJ : Int) : String
}