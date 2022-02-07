package com.example.projekat

import android.util.Log
import com.example.projekat.entity.*

class PodaciZaBazu(db : AppDatabase) {
    var db : AppDatabase

    fun onCreate() {
        try {

            db.tipoviAktivnostiDao().insert(TipoviAktivnosti(1, "Inkrementalne"))
            db.tipoviAktivnostiDao().insert(TipoviAktivnosti(2, "Kolicinske"))
            db.tipoviAktivnostiDao().insert(TipoviAktivnosti(3, "Vremenske"))

            db.kategorijeDao().insert(Kategorije(1, "Trčanje", 3, "Jutarnje/Večernje"))
            db.kategorijeDao().insert(Kategorije(2, "Doručak", 2, ""))
            db.kategorijeDao().insert(Kategorije(3, "Učenje", 1, ""))
            db.kategorijeDao().insert(Kategorije(4, "Tekućine", 1, ""))

            db.aktivnostiDao().insert(Aktivnosti(1, "Jutarnje trčanje", 1))
            db.glavneAktivnostiDao().insert(GlavneAktivnosti(1, "Jutarnje trčanje", 0, 0, 0, 0, "min", 1))
            db.vremenskeDao().insert(Vremenske(1, 1, "", ""))
            db.mjerneJediniceDao().insert(MjerneJedinice(2, "min", 1))

            db.aktivnostiDao().insert(Aktivnosti(5, "Večernje trčanje", 1))
            db.vremenskeDao().insert(Vremenske(2, 5, "", ""))
            db.mjerneJediniceDao().insert(MjerneJedinice(5, "min", 5))

            db.aktivnostiDao().insert(Aktivnosti(2, "Unos kalorija", 2))
            db.kolicinskeDao().insert(Kolicinske(1, 2, 200))
            db.mjerneJediniceDao().insert(MjerneJedinice(1, "kcal", 2))

            db.aktivnostiDao().insert(Aktivnosti(3, "Računarske mreže", 3))
            db.glavneAktivnostiDao().insert(GlavneAktivnosti(3, "Računarske mreže", 0, 1, 0,0,"sati",3))
            db.inkrementalneDao().insert(Inkrementalne(1, 3, 1, 0))
            db.mjerneJediniceDao().insert(MjerneJedinice(3, "sati", 3))

            db.aktivnostiDao().insert(Aktivnosti(4, "Voda", 4))
            db.inkrementalneDao().insert(Inkrementalne(2, 4, 1, 0))
            db.mjerneJediniceDao().insert(MjerneJedinice(4, "čaše", 4))


            db.citatiDao().insert(Citati(1, "The way to get started is to quit talking and begin doing!"))
            db.citatiDao().insert(Citati(2, "All our dreams can come true, if we have the courage to pursue them!"))
            db.citatiDao().insert(Citati(3, "The secret of getting ahead is getting started!"))
            db.citatiDao().insert(Citati(4, "Happiness is not something ready made. It comes from your own actions!"))
            db.citatiDao().insert(Citati(5, "When one door of happiness closes, another opens; but often we look so long at the closed door that we do not see the one which has been opened for us!"))
            db.citatiDao().insert(Citati(6, "Don’t be afraid to give up the good to go for the great!"))

            Log.d("USPJESNO", "USPJESNO")
            //Result.success()
        } catch (ex: Exception) {
            Log.e(TAG, "Greška!", ex)
            //Result.failure()
        }
    }

    init {
        this.db = db
    }

    companion object {
        private const val TAG = "PodaciZaBazu"
    }
}