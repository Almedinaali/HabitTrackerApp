package com.example.projekat.ui.categories

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projekat.AppDatabase
import com.example.projekat.R
import com.example.projekat.adapter.KategorijeAdapter
import com.example.projekat.entity.Kategorije
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.kategorije_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class KategorijeFragment : Fragment(), KategorijeAdapter.OnElementListener, CoroutineScope {
    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private lateinit var btn : FloatingActionButton
    private lateinit var db : AppDatabase
    private lateinit var kategorijeList : List<Kategorije>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        db = AppDatabase.getInstance(this.context!!)
        kategorijeList = db.kategorijeDao().getAll()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.kategorije_fragment, container, false)

    // populate the views now that the layout has been inflated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerView node initialized here

        recyclerView.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = KategorijeAdapter(kategorijeList,this@KategorijeFragment)
        }

        btn = view.findViewById(R.id.floatingActionButton_kategorije)
        btn.setOnClickListener {
            dodajKategoriju()
        }
    }

    fun dodajKategoriju() {
        val fr = getFragmentManager()?.beginTransaction()
        fr?.replace(R.id.fragment_container, DodajKategorijuFragment())
        fr?.addToBackStack(null)
        fr?.commit()
    }

    override fun onElementClick(position: Int) {
        var fr = getFragmentManager()?.beginTransaction()
        Log.d(TAG, "onElementClick: " + db.kategorijeDao().getById(position))
        Log.d(TAG, "onElementClick: " + kategorijeList.get(position))
        fr?.replace(R.id.fragment_container, AktivnostiFragment(kategorijeList.get(position)))
        fr?.addToBackStack(null)
        fr?.commit()
    }

}