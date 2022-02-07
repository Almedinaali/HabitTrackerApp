package com.example.projekat.ui.home

import android.content.ContentValues
import android.os.Bundle
import android.os.SystemClock
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projekat.AppDatabase
import com.example.projekat.R
import com.example.projekat.activity.MainActivity
import com.example.projekat.adapter.AktivnostiAdapter
import com.example.projekat.adapter.PocetneAktivnostiAdapter
import com.example.projekat.entity.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.pocetna_fragment.*
import kotlinx.android.synthetic.main.pocetna_vremenska_element.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class PocetnaFragment(db : AppDatabase, listaAktivnosti: List<Aktivnosti>) : Fragment() {

    private var db: AppDatabase

    // iz ove liste aktivnosti koja sadrzi sve aktivnosti mi treba samo nekoliko elemenata koje cu proslijediti adapteru
    private var listaAktivnosti: List<Aktivnosti>

    // lista s par pocetnih aktivnosti koju prosljedjujem adapteru
    lateinit private var glavneAktivnosti: ArrayList<GlavneAktivnosti>
    private var listaTipovaAktivnosti = arrayListOf<Int>()

    lateinit var fabPocetna: FloatingActionButton

    lateinit var odabraneAktivnosti: ArrayList<Int>  // u ovu listu smjestam aktivnosti iz alert dialoga koje korisnik odabere za dodavanje na pocetnu stranicu

    init {
        this.db = db
        this.listaAktivnosti = listaAktivnosti
    }

    //  private lateinit var viewModel: PocetnaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make sure to enable `retainInstance` so the fragment will not be destroyed
        // with the activity.
        // if the activity is rotated, we get back the same instance we created earlier.
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.pocetna_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerView node initialized here

        dodajListe()

        recyclerViewPocetna.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            /**
             * Calls the specified function with `this` value as its receiver and returns `this` value.*/
            // apply radimo da pristupimo fjama iz adaptera
            adapter = PocetneAktivnostiAdapter(glavneAktivnosti, listaTipovaAktivnosti).apply {
                    povecajInkrement = { aktivnost ->
                        // u bazi se nalazi inkrement koji je korisnik odabrao prilikom kreiranja aktivnosti
                        // za taj inkrement treba povecati broj
                        db.glavneAktivnostiDao().updateBroj(aktivnost.broj + aktivnost.inkrement, aktivnost.id) // postavlja u bazu
                        db.inkrementalneDao().updateBroj(aktivnost.broj + aktivnost.inkrement, aktivnost.id)
                        aktivnost.broj = aktivnost.broj + aktivnost.inkrement
                        adapter?.notifyDataSetChanged()
                    }
                smanjiInkrement = { aktivnost ->
                    if (aktivnost.broj > 0) {
                        db.glavneAktivnostiDao().updateBroj(aktivnost.broj - aktivnost.inkrement, aktivnost.id) // postavlja u bazu
                        db.inkrementalneDao().updateBroj(aktivnost.broj - aktivnost.inkrement, aktivnost.id)
                        aktivnost.broj = aktivnost.broj - aktivnost.inkrement
                        adapter?.notifyDataSetChanged()
                    }
                }
                unesiKolicinu = { aktivnost ->
                    // otvara se polje za unos
                    // korisnik unosi vrijednost za kolicinsku aktivnost nakon cega se update-a kolona "unos"
                    val builder = context?.let { AlertDialog.Builder(it) }
                    val inputField = EditText(context)
                    inputField.maxLines = 1

                    val layout = FrameLayout(context)

                    layout.setPadding(45,15,45,0) //postavljam padding
                    layout.addView(inputField)
                    inputField.setRawInputType(InputType.TYPE_CLASS_NUMBER)

                    if (builder != null) {
                        builder.setView(layout)
                    }

                    if (builder != null) {
                        builder.setTitle("Unesite količinu")
                    }

                    // dodajem OK i cancel dugmad
                    if (builder != null) {
                        builder.setPositiveButton("OK") { dialog, which ->
                            val kolicina = inputField.text.toString().toInt()

                            aktivnost.unos = kolicina
                            db.glavneAktivnostiDao().updateUnos(kolicina, aktivnost.id)
                            db.kolicinskeDao().updateKolicina(kolicina, aktivnost.id)
                            adapter?.notifyDataSetChanged()
                        }
                    }

                    if (builder != null) {
                        builder.setNegativeButton("Cancel", null)
                    }

                    // kreiram i prikazujem alert dialog
                    val dialog = builder?.create()
                    if (dialog != null) {
                        dialog.show()
                    }

                }
                zapocniStopericu = { aktivnost, chronometer, startStop ->
                    var vrijemeZaustavljanja : Long = 0 // varijabla koja cuva koliko je vremena proslo od pokretanja stoperice

                    if (startStop.text == getString(R.string.start)) {
                        chronometer.setBase(SystemClock.elapsedRealtime()); // uvijek pocinje od 00:00 brojanje
                        db.vremenskeDao().updatePocetak((0).toString(), aktivnost.id)
                        chronometer.start()
                        startStop.setText(getString(R.string.stop))
                    } else {
                        vrijemeZaustavljanja = SystemClock.elapsedRealtime() - chronometer.getBase() // vrijeme koje je proteklo od pokretanja stoperice u milisekundama
                        chronometer.stop()
                        db.glavneAktivnostiDao().updateProtekloVrijeme(vrijemeZaustavljanja, aktivnost.id) // sprema vrijeme zaustavljanja (proteklo vrijeme) u milisekundama
                        aktivnost.proteklo_vrijeme = vrijemeZaustavljanja
                        db.vremenskeDao().updateKraj(vrijemeZaustavljanja.toString(), aktivnost.id) // sprema vrijeme zaustavljanja stoperice u milisekundama
                        //chronometer.setBase(SystemClock.elapsedRealtime());
                        startStop.setText(getString(R.string.start))
                    }

                }
            }
        }

        fabPocetna = view.findViewById(R.id.fabPocetna)

        fabPocetna.setOnClickListener {
            dodajAktivnost(view)
        }
    }

    private fun dodajListe() {
        glavneAktivnosti = ArrayList(db.glavneAktivnostiDao().getAll())

        for (el in glavneAktivnosti) {
            listaTipovaAktivnosti.add(db.kategorijeDao().getTipById(el.id_kategorije))
        }
    }

    private fun dodajAktivnost(view: View) {
        /* korisniku se prikaze lista vec postojecih aktivnosti u bazi
        *   aktivnost koju odabere se dodaje na pocetnu stranicu */
        val naziviAktivnosti : ArrayList<String> = arrayListOf()
        odabraneAktivnosti = ArrayList<Int>()

        for (el in listaAktivnosti) {
            naziviAktivnosti.add(el.naziv)
        }

        val builder = context?.let { AlertDialog.Builder(it) }
        if (builder != null) {
            builder.setTitle("Odaberite aktivnost")
        }

        // dodajemo checkbox listu
        val checkedItems = null
        if (builder != null) {
            // setMultiChoiceItems() prikazuje listu aktivnosti u alert dialogu
            builder.setMultiChoiceItems(naziviAktivnosti.toTypedArray(), checkedItems) { dialog, which, isChecked ->
                // toTypedArray() pretvara arrayList u array jer setMultiChoiceItems prihvata samo array kao argument
                // which sadrzi indeks pozicije selektovanog elementa u listi
                // ako je data aktivnost bila checkirana pa je korisnik uncheckirao box
                // onda uklanjam tu aktivnost iz liste
                if (odabraneAktivnosti.contains(which)) {
                    odabraneAktivnosti.remove(Integer.valueOf(which))
                } else {
                    // ako je korisnik checkirao box, odgovarajucu aktivnost dodajem u listu odabraneAktivnosti
                    odabraneAktivnosti.add(which)
                }
            }
        }

        // dodajem OK i cancel dugmad
        if (builder != null) {
            builder.setPositiveButton("OK") { dialog, which ->
                // sad treba u listu aktivnosti koje cu proslijediti PocetneAktivnostiAdapter-u
                // dodati aktivnosti koje je korisnik odabrao u alert dialog-u
                for (el in odabraneAktivnosti) {
                    val naziv = naziviAktivnosti[el]

                    // ako vec postoji odabrana aktivnost na pocetnoj, insert je nece ponovo dodati
                    val aktivnostZaDodavanje = db.aktivnostiDao().getByNaziv(naziv)
                    db.glavneAktivnostiDao().insert(GlavneAktivnosti(aktivnostZaDodavanje.id, aktivnostZaDodavanje.naziv,
                        db.inkrementalneDao().getBrojByIdAktivnosti(aktivnostZaDodavanje.id),
                        db.inkrementalneDao().getInkrementByIdAktivnosti(aktivnostZaDodavanje.id),
                        db.kolicinskeDao().getKolicinaById(aktivnostZaDodavanje.id),
                        0,//db.vremenskeDao().getVrijemeKrajaByIdAktivnosti(aktivnostZaDodavanje.id).toLong(),
                        db.mjerneJediniceDao().getByIdAktivnosti(aktivnostZaDodavanje.id),
                        aktivnostZaDodavanje.id_kategorije))

                    //dodajListe()
                }

                dodajListe()

                /* nicemu ne koristi, nista ne radi
                recyclerViewPocetna.apply {
                    adapter?.notifyDataSetChanged()
                }*/
            }
        }

        if (builder != null) {
            builder.setNegativeButton("Cancel", null)
        }

        // kreiram i prikazujem alert dialog
        val dialog = builder?.create()
        if (dialog != null) {
            dialog.show()
        }
    }
}