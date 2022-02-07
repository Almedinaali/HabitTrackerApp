package com.example.projekat.adapter

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projekat.AppDatabase
import com.example.projekat.R
import com.example.projekat.activity.MainActivity.Companion.db
import com.example.projekat.entity.*

class PocetneAktivnostiAdapter(listaAktivnosti: List<GlavneAktivnosti>, listaTipova: List<Int>)
    : RecyclerView.Adapter<PocetneAktivnostiAdapter.BaseViewHolder>() {

    private lateinit var db: AppDatabase

    private var listaAktivnosti : List<GlavneAktivnosti>
    // iz main-a prosljedjujem i listu tipova aktivnosti tako da se element u listaAktivnosti
    // i njegov tip u listaTipovaAktivnosti nalaze na istom indeksu u listama
    private var listaTipovaAktivnosti : List<Int>

    // kad kao tip proslijedim GlavneAktivnosti onda u fragmentu mogu pristupiti kolonama aktivnosti(atributima), u suprotnom ne mogu
    // in order to make the callback work, we need to declare the callback variable as public in your adapter
    // ako funkcija ne vraca nikakvu korisnu vrijednost, njen tip returna je Unit
    var povecajInkrement: ((GlavneAktivnosti) -> Unit)? = null
    var smanjiInkrement: ((GlavneAktivnosti) -> Unit)? = null
    var unesiKolicinu: ((GlavneAktivnosti) -> Unit)? = null
    var zapocniStopericu: ((GlavneAktivnosti, Chronometer, Button) -> Unit)? = null

    init {
        this.listaAktivnosti = listaAktivnosti
        this.listaTipovaAktivnosti = listaTipova
    }

    companion object {
        private const val TYPE_INKREMENTALNE = 0
        private const val TYPE_VREMENSKE = 1
        private const val TYPE_KOLICINSKE = 2
    }

    /* When the Adapter is initialized, it will populate PocetneAktivnostiHoldere in the RecyclerView
     with the onCreateViewHolder(…) method. */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
        db = AppDatabase.getInstance(parent.context)
        return when (viewType) {
            TYPE_INKREMENTALNE -> {
                val view: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.pocetna_inkrementalna_element, parent, false)
                InkrementalneViewHolder(view).apply {
                    // ovo je kao receiver kod koji koristi proslijedjeni item
                    // a evente handlamo tamo gdje deklarisemo adapter (u PocetnaFragment.kt)
                    povecajInkrement = { aktivnost ->
                        this@PocetneAktivnostiAdapter.povecajInkrement?.invoke(aktivnost)
                    }
                    smanjiInkrement = { aktivnost ->
                        this@PocetneAktivnostiAdapter.smanjiInkrement?.invoke(aktivnost)
                    }
                }
            }
            TYPE_VREMENSKE -> {
                val view: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.pocetna_vremenska_element, parent, false)
                VremenskeViewHolder(view).apply {
                    zapocniStopericu = { aktivnost, chronometer, startStop ->
                        this@PocetneAktivnostiAdapter.zapocniStopericu?.invoke(aktivnost, chronometer, startStop)
                    }
                }
            }
            TYPE_KOLICINSKE -> {
                val view: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.pocetna_kolicinska_element, parent, false)
                KolicinskeViewHolder(view).apply {
                    unesiKolicinu = { aktivnost ->
                        this@PocetneAktivnostiAdapter.unesiKolicinu?.invoke(aktivnost)
                    }
                }
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return listaAktivnosti.size
    }

    /* The method onBindViewHolder(…) will take a data collection and apply a rotating rendering
    of visible data applied to those ViewHolders. */
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val element = listaAktivnosti[position]
        when (holder) {
            // prosljedjujem element (aktivnost) viewHolderu
            is InkrementalneViewHolder -> holder.bind(element)
            is VremenskeViewHolder -> holder.bind(element)
            is KolicinskeViewHolder -> holder.bind(element)
            else -> throw IllegalArgumentException()
        }
    }

    // odredjujemo tip aktivnosti
    // nakon sto se odredi tip, onBindViewHolder mora proslijediti  viewHolder odgovarajucoj bind funkciji
    override fun getItemViewType(position: Int): Int {
        val aktivnost = listaAktivnosti[position]
        val tipAktivnosti = listaTipovaAktivnosti[position]

        return when (tipAktivnosti) {
            1 -> TYPE_INKREMENTALNE
            3 -> TYPE_VREMENSKE
            2 -> TYPE_KOLICINSKE
            else -> throw IllegalArgumentException("Invalid type of data " + aktivnost.naziv)
        }
    }

    abstract class BaseViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        abstract fun bind (aktivnost : GlavneAktivnosti)
    }

    class InkrementalneViewHolder(itemView: View) : BaseViewHolder(itemView) {

        var nazivAktivnosti : TextView
        var unos : TextView
        var mjernaJedinica : TextView
        var plusDugme : Button
        var minusDugme : Button

        // deklarisemo Higher-Order funkcije
        // Basically this type of callback variable is a Kotlin shortcut for an interface that
        // would do the same thing in Java, when you create an interface to handle your adapter clicks.
        // da bi ovo radilo, moramo i ovdje definisati callback varijable
        var povecajInkrement: ((GlavneAktivnosti) -> Unit)? = null
        var smanjiInkrement: ((GlavneAktivnosti) -> Unit)? = null

        init {
            nazivAktivnosti = itemView.findViewById(R.id.naziv_aktivnosti_pocetna_inkrementalna) // ovo ima svaki tip aktivnosti
            unos = itemView.findViewById(R.id.unos_pocetna_inkrementalna) // ovo ima svaki tip aktivnosti
            mjernaJedinica = itemView.findViewById(R.id.mjerna_jedinica_pocetna_inkrementalna) // ovo nemaju vremenske aktivnosti
            plusDugme = itemView.findViewById(R.id.plus_btn) // imaju samo inkrementalne aktivnosti
            minusDugme = itemView.findViewById(R.id.minus_btn) // imaju samo inkrementalne aktivnosti
        }

        override fun bind(aktivnost: GlavneAktivnosti) {
            // broj i inkrement mozda trebaju unutar setonclicklistenera
            // val broj = db?.inkrementalneDao()?.getBrojByIdAktivnosti(aktivnost.id)
            // val broj = db.inkrementalneDao().getInkrementalnaByIdAktivnosti(aktivnost.id).broj
            val broj = aktivnost.broj
            // val inkrement = db?.inkrementalneDao()?.getInkrementByIdAktivnosti(aktivnost.id)
            //val inkrement = db.inkrementalneDao().getInkrementalnaByIdAktivnosti(aktivnost.id).inkrement
            val inkrement = aktivnost.inkrement

            nazivAktivnosti.text = aktivnost.naziv
            mjernaJedinica.text = aktivnost.mjerna_jedinica
            unos.text = broj.toString()

            plusDugme.setOnClickListener {
                /*db?.glavneAktivnostiDao()?.updateBroj(broj + inkrement, aktivnost.id)
                //db?.inkrementalneDao()?.updateBroj(broj + inkrement, aktivnost.id)
                // todo: kad se postavi novi broj u bazi treba azurirati prikaz tj azurirati unos.text
                broj = aktivnost.broj
                unos.text = broj.toString()*/

                // invoke() funkcija prosljedjuje vrijednost funkciji primatelju (receiver function)
                // ponasa se kao sender funkcija
                // The invoke function tells your callback that the button has been pressed.
                povecajInkrement?.invoke(aktivnost)
            }

            minusDugme.setOnClickListener {
                db?.glavneAktivnostiDao()?.updateBroj(broj - inkrement, aktivnost.id)
                //db?.inkrementalneDao()?.updateBroj(broj - inkrement, aktivnost.id)
                smanjiInkrement?.invoke(aktivnost)
            }
        }
    }

    class KolicinskeViewHolder(itemView: View) : BaseViewHolder(itemView) {

        var nazivAktivnosti : TextView
        var unos : TextView
        var mjernaJedinica : TextView
        var dodajUnos : Button

        var unesiKolicinu: ((GlavneAktivnosti) -> Unit)? = null

        init {
            nazivAktivnosti = itemView.findViewById(R.id.naziv_aktivnosti_pocetna_kolicinska) // ovo ima svaki tip aktivnosti
            unos = itemView.findViewById(R.id.unos_pocetna_kolicinska) // ovo ima svaki tip aktivnosti
            mjernaJedinica = itemView.findViewById(R.id.mjerna_jedinica_pocetna_kolicinska) // ovo nemaju vremenske aktivnosti
            dodajUnos = itemView.findViewById(R.id.unos_btn) // ovo imaju samo kolicinske aktivnosti
        }

        override fun bind(aktivnost: GlavneAktivnosti) {
            nazivAktivnosti.text = aktivnost.naziv
            mjernaJedinica.text = aktivnost.mjerna_jedinica
            unos.text = aktivnost.unos.toString()

            dodajUnos.setOnClickListener {
                unesiKolicinu?.invoke(aktivnost)
            }
        }
    }

    class VremenskeViewHolder(itemView: View) : BaseViewHolder(itemView) {

        var nazivAktivnosti : TextView
        var chronometer : Chronometer
        var startStop : Button

        var zapocniStopericu: ((GlavneAktivnosti, Chronometer, Button) -> Unit)? = null

        init {
            nazivAktivnosti = itemView.findViewById(R.id.naziv_aktivnosti_pocetna_vremenska) // ovo ima svaki tip aktivnosti
            chronometer = itemView.findViewById(R.id.chronometer) // ovo ima svaki tip aktivnosti
            startStop = itemView.findViewById(R.id.start_stop_btn) // imaju samo vremenske aktivnosti
        }
        override fun bind(aktivnost: GlavneAktivnosti) {
            nazivAktivnosti.text = aktivnost.naziv

            // trebam koristiti setonchronometerticklistener u kojem na svaki tik spremam
            // proteklo vrijeme u bazu jer kad izvrsim promjenu u aktivnosti nekog drugog tipa
            // stoperica se reseta i nastavlja od 00:00 brojanje zbog adapter?.notifyDataSetChanged() koji se tada poziva
            // ovako ce nastaviti od zadnje sekunde brojanje
            chronometer.setOnChronometerTickListener({
                aktivnost.proteklo_vrijeme = SystemClock.elapsedRealtime() - chronometer.getBase()
            })

            val protekleMilisekunde = aktivnost.proteklo_vrijeme!!
            chronometer.base = SystemClock.elapsedRealtime() - protekleMilisekunde  // ako nije započeta štoperica, chronometer ce prikazivati zadnje vrijeme trajanja stoperice

            startStop.setOnClickListener {
                zapocniStopericu?.invoke(aktivnost, chronometer, startStop)
            }
        }
    }
}