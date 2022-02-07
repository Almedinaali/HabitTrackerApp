package com.example.projekat.DAO

import androidx.room.*
import com.example.projekat.entity.MjerneJedinice
import com.example.projekat.entity.Profil

@Dao
interface ProfilDao {

    //suspend - da ne ometa glavu radnju prilikom izvšavanja
    //IGNORE - prije inserta pretrazi se tabela i ako ima kolona s aistim podacima ignorise unos
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(profil: Profil)

    //REPLACE - prije inserta pretrazi se tabela i ako ima kolona s aistim podacima (npr. id isti) zamijeni se
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(profil: Profil)

    @Query("SELECT * FROM profil")
    fun getAll() : List<Profil>

    @Query("DELETE FROM profil")
    suspend fun deleteAll()

    @Query("DELETE FROM profil WHERE id = :id_P")
    suspend fun deleteId(id_P : Int)

    @Update
    suspend fun update(profil: Profil)
}