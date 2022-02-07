package com.example.projekat.ui.profile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.projekat.AppDatabase
import com.example.projekat.R
import com.example.projekat.entity.Profil
import org.w3c.dom.Text
import java.util.*

class ProfilFragment : Fragment() {

    private lateinit var db : AppDatabase
    private lateinit var btn : Button
    private lateinit var btnSlika : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = AppDatabase.getInstance(this.context!!)
        return inflater.inflate(R.layout.profil_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ucitajPodatke()

        btn = view!!.findViewById(R.id.sacuvajPromjene)
        btn.setOnClickListener {
            sacuvajPromjene()
        }

    }

    fun ucitajPodatke() {
        var podaci = db.profilDao().getAll();
        if(!podaci.isEmpty()) {
            view!!.findViewById<EditText>(R.id.ime).setText(podaci[0].ime)
            view!!.findViewById<EditText>(R.id.prezime).setText(podaci[0].prezime)
            view!!.findViewById<EditText>(R.id.oKorisniku).setText(podaci[0].oKorisniku)
        }
        var lastId = db.citatiDao().getLastId()
        var idCitata = Random().nextInt(lastId - 1) - 1
        var citat = db.citatiDao().getById(idCitata)

        view!!.findViewById<TextView>(R.id.citat).text = citat
    }

    fun sacuvajPromjene() {
        var ime = view!!.findViewById<EditText>(R.id.ime).text.toString()
        var prezime = view!!.findViewById<EditText>(R.id.prezime).text.toString()
        var about = view!!.findViewById<EditText>(R.id.oKorisniku).text.toString()
        Log.d(TAG, "sacuvajPromjene: " + ime + prezime + about)
        db.profilDao().insertOrUpdate(Profil(1, ime, prezime, about))
    }

}