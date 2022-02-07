package com.example.projekat.ui.categories

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.projekat.AppDatabase
import com.example.projekat.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import android.widget.ArrayAdapter
import com.example.projekat.entity.Kategorije
import kotlin.coroutines.CoroutineContext

class DodajKategorijuFragment : Fragment(), CoroutineScope {
    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private lateinit var btn : Button
    private lateinit var db : AppDatabase
    private var onItemSelected = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        db = AppDatabase.getInstance(this.context!!)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        var view = inflater.inflate(R.layout.fragment_dodaj_kategoriju, container, false)

        val spinner = view.findViewById<Spinner>(R.id.tip)
        val l : List<String> = db.tipoviAktivnostiDao().getSveNazive()

        if (spinner != null) {
                spinner.adapter = ArrayAdapter(
                    requireActivity(),
                    android.R.layout.simple_spinner_dropdown_item,
                    l
                )
            }

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    onItemSelected = position
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    onItemSelected = 1
                }
            }
        return view
    }

    // populate the views now that the layout has been inflated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textview1).text = getString(R.string.dodaj_novu_kat)
        view.findViewById<TextView>(R.id.textview2).text = getString(R.string.naziv)
        view.findViewById<TextView>(R.id.textview3).text = getString(R.string.osobina)
        view.findViewById<TextView>(R.id.textview4).text = getString(R.string.tip)
        btn = view.findViewById(R.id.novaKategorija)
        btn.setText(R.string.dodaj)
        btn.setOnClickListener {
            val naziv = view.findViewById<EditText>(R.id.naziv).text.toString()
            val osobina = view.findViewById<EditText>(R.id.osobina).text.toString()
            val tip = view.findViewById<Spinner>(R.id.tip).toString()
            if(TextUtils.isEmpty(naziv)) {
                Toast.makeText(requireActivity(), getString(R.string.unesite_naziv_kategorije), Toast.LENGTH_LONG).show();
                return@setOnClickListener
            } else if(TextUtils.isEmpty(tip)) {
                Toast.makeText(requireActivity(), getString(R.string.odaberite_tip), Toast.LENGTH_LONG).show();
                return@setOnClickListener
            } else {
                val novaKat = Kategorije(db.kategorijeDao().getLastId()+1, naziv, onItemSelected+1, osobina)
                launch {
                    db.kategorijeDao().insert(novaKat)
                    Toast.makeText(requireActivity(), getString(R.string.dodana_nova), Toast.LENGTH_LONG).show()
                    dodanaNova()
                }
            }
        }
    }

    fun dodanaNova() {
        var fr = getFragmentManager()?.beginTransaction()
        fr?.replace(R.id.fragment_container, KategorijeFragment())
        fr?.addToBackStack(null)
        fr?.commit()
    }
}