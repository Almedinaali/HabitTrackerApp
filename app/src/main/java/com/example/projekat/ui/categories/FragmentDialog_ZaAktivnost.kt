package com.example.projekat.ui.categories

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.projekat.AppDatabase
import com.example.projekat.R
import com.example.projekat.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FragmentDialog_ZaAktivnost(aktivnost : Aktivnosti) : DialogFragment(),
    CoroutineScope {
    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private lateinit var db : AppDatabase
    private var aktivnost : Aktivnosti
    private lateinit var btnIzbrisi : Button
    private lateinit var btnSacuvaj : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = AppDatabase.getInstance(this.context!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dodaj_novu_aktivnost, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postaviIzgled(db.tipoviAktivnostiDao().getNazivById(
            db.kategorijeDao().getById(aktivnost.id_kategorije).tip
        ))

        view.findViewById<TextView>(R.id.naziv).text = getString(R.string.naziv)
        view.findViewById<TextView>(R.id.inkrement).text = getString(R.string.inkrement)
        view.findViewById<TextView>(R.id.broj).text = getString(R.string.broj)
        view.findViewById<TextView>(R.id.kolicina).text = getString(R.string.kolicina)
        view.findViewById<TextView>(R.id.mjernaJedinica).text = getString(R.string.mjernaJedinica)
        //view.findViewById<TextView>(R.id.pocetak).text = getString(R.string.pocetak)
        //view.findViewById<TextView>(R.id.kraj).text = getString(R.string.kraj)

        btnIzbrisi = view.findViewById(R.id.izbrisi)
        view.findViewById<Button>(R.id.izbrisi).text = getString(R.string.button_izbrisi)
        btnIzbrisi.setOnClickListener {
            db.aktivnostiDao().deleteId(aktivnost.id)
            val fr = getFragmentManager()?.beginTransaction()
            fr?.replace(R.id.fragment_container, AktivnostiFragment(db.kategorijeDao().getById(aktivnost.id_kategorije)))
            fr?.addToBackStack(null)
            fr?.commit()
            dismiss()
        }
        btnSacuvaj = view.findViewById(R.id.novaAktivnost)
        view.findViewById<Button>(R.id.novaAktivnost).text = getString(R.string.button_sacuvaj)
        btnSacuvaj.setOnClickListener {
            provjeriUnos()
        }

    }

    open fun postaviIzgled(s : String) {
        if(s == "Inkrementalne") {
            //view?.findViewById<TextView>(R.id.mjernaJedinica)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editMJ)?.setText(db.mjerneJediniceDao().getByIdAktivnosti(aktivnost.id)) //setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editKolicina)?.setVisibility(View.GONE)
            view?.findViewById<TextView>(R.id.kolicina)?.setVisibility(View.GONE)
            view?.findViewById<TextView>(R.id.pocetak)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editPocetak)?.setVisibility(View.GONE)
            view?.findViewById<TextView>(R.id.kraj)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editKraj)?.setVisibility(View.GONE)

            view?.findViewById<TextView>(R.id.editNaziv)?.text = aktivnost.naziv
            view?.findViewById<TextView>(R.id.editBroj)?.text = db.inkrementalneDao().getInkrementalnaByIdAktivnosti(aktivnost.id).broj.toString()
            view?.findViewById<TextView>(R.id.editInkrement)?.text = db.inkrementalneDao().getInkrementalnaByIdAktivnosti(aktivnost.id).inkrement.toString()


        }else if(s == "Kolicinske") {

            view?.findViewById<TextView>(R.id.broj)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editBroj)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editInkrement)?.setVisibility(View.GONE)
            view?.findViewById<TextView>(R.id.inkrement)?.setVisibility(View.GONE)
            view?.findViewById<TextView>(R.id.pocetak)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editPocetak)?.setVisibility(View.GONE)
            view?.findViewById<TextView>(R.id.kraj)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editKraj)?.setVisibility(View.GONE)

            view?.findViewById<TextView>(R.id.editNaziv)?.text = aktivnost.naziv
            view?.findViewById<TextView>(R.id.editKolicina)?.text = db.kolicinskeDao().getKolicinskaByIdAktivnosti(aktivnost.id).kolicina.toString()
            view?.findViewById<TextView>(R.id.editMJ)?.text = db.mjerneJediniceDao().getByIdAktivnosti(aktivnost.id)

        }else {

            view?.findViewById<TextView>(R.id.mjernaJedinica)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editMJ)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editKolicina)?.setVisibility(View.GONE)
            view?.findViewById<TextView>(R.id.kolicina)?.setVisibility(View.GONE)
            view?.findViewById<TextView>(R.id.broj)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editBroj)?.setVisibility(View.GONE)
            view?.findViewById<TextView>(R.id.inkrement)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editInkrement)?.setVisibility(View.GONE)

            view?.findViewById<TextView>(R.id.editNaziv)?.text = aktivnost.naziv
            view?.findViewById<TextView>(R.id.pocetak)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editPocetak)?.setVisibility(View.GONE)
            view?.findViewById<TextView>(R.id.kraj)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editKraj)?.setVisibility(View.GONE)

        }
    }

    fun provjeriUnos() {
        val tip = db.kategorijeDao().getById(aktivnost.id_kategorije).tip
        val naziv = view?.findViewById<EditText>(R.id.editNaziv)?.text.toString()

        if(tip == 1) {
            val noviIdInkr = db.inkrementalneDao().getInkrementalnaByIdAktivnosti(aktivnost.id).id
            val inkrement = view?.findViewById<EditText>(R.id.editInkrement)?.text.toString()
            val broj = view?.findViewById<EditText>(R.id.editBroj)?.text.toString()
            val noviIdMJ = db.mjerneJediniceDao().getLastId()
            val mjernaJ = view?.findViewById<EditText>(R.id.editMJ)?.text.toString()

            if(TextUtils.isEmpty(naziv) || TextUtils.isEmpty(inkrement) || TextUtils.isEmpty(broj) || TextUtils.isEmpty(mjernaJ))
                Toast.makeText(requireActivity(), getString(R.string.unos_podataka), Toast.LENGTH_LONG).show()
            else {
                launch {
                    db.aktivnostiDao().insertOrUpdate(Aktivnosti(aktivnost.id, naziv, aktivnost.id_kategorije))
                    db.inkrementalneDao().insertOrUpdate(Inkrementalne(noviIdInkr ,aktivnost.id, inkrement.toInt(), broj.toInt()))
                    db.mjerneJediniceDao().insertOrUpdate(MjerneJedinice(noviIdMJ, mjernaJ, aktivnost.id))
                    zavrsi()
                }
            }
        } else if(tip == 2) {
            val noviIdKol = db.kolicinskeDao().getKolicinskaByIdAktivnosti(aktivnost.id).id
            val kolicina = view?.findViewById<EditText>(R.id.editKolicina)?.text.toString()
            val noviIdMJ = db.mjerneJediniceDao().getLastId()
            val mjernaJ = view?.findViewById<EditText>(R.id.editMJ)?.text.toString()

            if(TextUtils.isEmpty(naziv) || TextUtils.isEmpty(kolicina) || TextUtils.isEmpty(mjernaJ))
                Toast.makeText(requireActivity(), getString(R.string.unos_podataka), Toast.LENGTH_LONG).show()
            else {
                launch {
                    db.aktivnostiDao().insertOrUpdate(Aktivnosti(aktivnost.id, naziv, aktivnost.id_kategorije))
                    db.kolicinskeDao().insertOrUpdate(Kolicinske(noviIdKol, aktivnost.id, kolicina.toInt()))
                    db.mjerneJediniceDao().insertOrUpdate(MjerneJedinice(noviIdMJ, mjernaJ, aktivnost.id))
                    zavrsi()
                }
            }
        } else {
            val noviIdVr = db.vremenskeDao().getVremenskaByIdAktivnosti(aktivnost.id).id
            val pocetak = view?.findViewById<EditText>(R.id.editPocetak)?.text.toString()
            val kraj = view?.findViewById<EditText>(R.id.editKraj)?.text.toString()

            if(TextUtils.isEmpty(naziv)) //|| TextUtils.isEmpty(pocetak) || TextUtils.isEmpty(kraj))
                Toast.makeText(requireActivity(), getString(R.string.unos_podataka), Toast.LENGTH_LONG).show()
            else {
                launch {
                    db.aktivnostiDao().insertOrUpdate(Aktivnosti(aktivnost.id, naziv, aktivnost.id_kategorije))
                    db.vremenskeDao().insertOrUpdate(Vremenske(noviIdVr, aktivnost.id, pocetak, kraj))
                    zavrsi()
                }
            }
        }
    }

    fun zavrsi() {
        var fr = getFragmentManager()?.beginTransaction()
        fr?.replace(R.id.fragment_container, AktivnostiFragment(db.kategorijeDao().getById(aktivnost.id_kategorije)))
        fr?.addToBackStack(null)
        fr?.commit()
        dismiss()
    }

    init {
        this.aktivnost = aktivnost
    }
}