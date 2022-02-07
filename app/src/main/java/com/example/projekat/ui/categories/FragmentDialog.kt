package com.example.projekat.ui.categories

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.projekat.AppDatabase
import com.example.projekat.R
import kotlinx.android.synthetic.main.fragment_dialog.*


class FragmentDialog(id : Int) : DialogFragment() {

    private lateinit var db : AppDatabase
    private var id_ : Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = AppDatabase.getInstance(this.context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.tekst).text = getString(R.string.alert_dialog)

        btnDA.setText(getString(R.string.DA))
        btnNE.setText(getString(R.string.NE))

        btnNE.setOnClickListener {
            dismiss()
        }
        btnDA.setOnClickListener {
            Log.d(TAG, "onViewCreated: " + db.kategorijeDao().getById(id_))
            db.kategorijeDao().deleteId(id_)
            Log.d(TAG, "onViewCreated: " + id_)
            var fr = getFragmentManager()?.beginTransaction()
            fr?.replace(R.id.fragment_container, KategorijeFragment())
            fr?.addToBackStack(null)
            fr?.commit()
            dismiss()
        }
    }

    init {
        this.id_ = id
    }
}