package com.example.projekat

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.projekat.DAO.*
import com.example.projekat.entity.*

@Database(entities = [TipoviAktivnosti::class,
                      Kategorije::class,
                      Aktivnosti::class,
                      GlavneAktivnosti::class,
                      Inkrementalne::class,
                      Kolicinske::class,
                      Vremenske::class,
                      MjerneJedinice::class,
                      Profil::class,
                      Citati::class],
            version = 1,
            exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tipoviAktivnostiDao() : TipoviAktivnostiDao
    abstract fun kategorijeDao() : KategorijeDao
    abstract fun aktivnostiDao() : AktivnostiDao
    abstract fun glavneAktivnostiDao() : GlavneAktivnostiDao
    abstract fun inkrementalneDao() : InkrementalneDao
    abstract fun kolicinskeDao() : KolicinskeDao
    abstract fun vremenskeDao() : VremenskeDao
    abstract fun mjerneJediniceDao() : MjerneJediniceDao
    abstract fun citatiDao() : CitatiDao
    abstract fun profilDao() : ProfilDao

    companion object {

        // For Singleton instantiation
        //Volatile - osigurava da se tokom koristenja aplikacije koristi samo jedna instanca baze
        //svakim novim pozivom baze proslijedi se ova ista instanca
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            //lock - kad jedan thread "uđe" u sistem pobrine se da niko drugi ne može
            //uglavnom je neko "duplo" osiguranje da se instanca baze napravi samo ejdnom i uvijek koristi ta ista
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        //Kreiranje
        //private
        fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, java.lang.String.valueOf(R.string.db_name))
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                    }
                })
                .allowMainThreadQueries()
                .build()
        }
    }
}