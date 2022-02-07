package com.example.projekat.DAO

import androidx.room.*
import com.example.projekat.entity.Aktivnosti
import com.example.projekat.entity.GlavneAktivnosti

@Dao
interface GlavneAktivnostiDao {

    //suspend - da ne ometa glavu radnju prilikom izvšavanja
    //IGNORE - prije inserta pretrazi se tabela i ako ima kolona s aistim podacima ignorise unos
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(aktivnosti: GlavneAktivnosti)

    //REPLACE - prije inserta pretrazi se tabela i ako ima kolona s aistim podacima (npr. id isti) zamijeni se
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(aktivnosti: GlavneAktivnosti)

    @Query("SELECT * FROM glavneAktivnosti")
    fun getAll() : List<GlavneAktivnosti>

    @Query("DELETE FROM glavneAktivnosti")
    suspend fun deleteAll()

    @Query("DELETE FROM glavneAktivnosti WHERE id = :id_A")
    suspend fun deleteId(id_A : Int)

    @Update
    suspend fun update(aktivnosti: GlavneAktivnosti?)

    @Query("SELECT id FROM glavneAktivnosti ORDER BY id DESC LIMIT 1")
    suspend fun getLastId() : Int

    @Query("SELECT * FROM glavneAktivnosti WHERE id = :id_A")
    suspend fun getById(id_A : Int) : List<GlavneAktivnosti>

    @Query("SELECT * FROM glavneAktivnosti WHERE id_kategorije = :id_K")
    suspend fun getById_K(id_K : Int) : List<GlavneAktivnosti>

    @Query("SELECT * FROM glavneAktivnosti WHERE naziv = :naziv_A")
    fun getByNaziv(naziv_A : String) : GlavneAktivnosti

    @Query("UPDATE glavneAktivnosti SET broj = :broj WHERE id = :id_I")
    fun updateBroj(broj : Int, id_I : Int)

    @Query("UPDATE glavneAktivnosti SET unos = :kolicina WHERE id = :id_K")
    fun updateUnos(kolicina : Int, id_K : Int)

    @Query("UPDATE glavneAktivnosti SET proteklo_vrijeme = :vrijeme WHERE id = :id_V")
    fun updateProtekloVrijeme(vrijeme : Long, id_V : Int)

}