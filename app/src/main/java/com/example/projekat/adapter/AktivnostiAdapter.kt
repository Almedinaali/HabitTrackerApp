package com.example.projekat.adapter

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projekat.AppDatabase
import com.example.projekat.R
import com.example.projekat.entity.*

class AktivnostiAdapter(tip : Int, listaAktivnosti : List<Aktivnosti>, mOnElementListener: OnElementListener)
    : RecyclerView.Adapter<AktivnostiAdapter.AktivnostiViewHolder>() {

    private var listaAktivnosti : List<Aktivnosti>
    private lateinit var db : AppDatabase
    private var mOnElementListener : OnElementListener
    private var tip : Int

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AktivnostiViewHolder {
        db = AppDatabase.getInstance(parent.context)
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.aktivnosti_element, parent, false)
        return AktivnostiViewHolder(view, mOnElementListener)
    }

    override fun onBindViewHolder(holder: AktivnostiViewHolder, position: Int) {
        if(tip == 1) {
            holder.naziv.setText(listaAktivnosti.get(position).naziv)
            Log.d(TAG, "onBindViewHolder: " + db.inkrementalneDao().getInkrementalnaByIdAktivnosti(listaAktivnosti.get(position).id))
            holder.editBroj.setText(db.inkrementalneDao().getInkrementalnaByIdAktivnosti(listaAktivnosti.get(position).id).broj.toString())
            holder.editInkrement.setText(db.inkrementalneDao().getInkrementalnaByIdAktivnosti(listaAktivnosti.get(position).id).inkrement.toString())
           // holder.mjernaJedinica.setVisibility(View.GONE)
            holder.editMjernaJedinica.setText(db.mjerneJediniceDao().getByIdAktivnosti(listaAktivnosti.get(position).id))//setVisibility(View.GONE)
            holder.editKolicina.setVisibility(View.GONE)
            holder.kolicina.setVisibility(View.GONE)
            holder.pocetak.setVisibility(View.GONE)
            holder.editPocetak.setVisibility(View.GONE)
            holder.kraj.setVisibility(View.GONE)
            holder.editKraj.setVisibility(View.GONE)
        } else if(tip == 2) {
            holder.naziv.setText(listaAktivnosti.get(position).naziv)
            holder.editBroj.setVisibility(View.GONE)
            holder.broj.setVisibility(View.GONE)
            holder.editInkrement.setVisibility(View.GONE)
            holder.inkrement.setVisibility(View.GONE)
            holder.editMjernaJedinica.setText(db.mjerneJediniceDao().getByIdAktivnosti(listaAktivnosti.get(position).id))
            holder.editKolicina.setText(db.kolicinskeDao().getKolicinskaByIdAktivnosti(listaAktivnosti.get(position).id).kolicina.toString())
            holder.pocetak.setVisibility(View.GONE)
            holder.editPocetak.setVisibility(View.GONE)
            holder.kraj.setVisibility(View.GONE)
            holder.editKraj.setVisibility(View.GONE)
        } else {
            holder.naziv.setText(listaAktivnosti.get(position).naziv)
            holder.editBroj.setVisibility(View.GONE)
            holder.broj.setVisibility(View.GONE)
            holder.editInkrement.setVisibility(View.GONE)
            holder.inkrement.setVisibility(View.GONE)
            holder.editMjernaJedinica.setVisibility(View.GONE)
            holder.mjernaJedinica.setVisibility(View.GONE)
            holder.editKolicina.setVisibility(View.GONE)
            holder.kolicina.setVisibility(View.GONE)
//            holder.editPocetak.setText(db.vremenskeDao().getVremenskaByIdAktivnosti(listaAktivnosti.get(position).id).pocetak)
 //           holder.editKraj.setText(db.vremenskeDao().getVremenskaByIdAktivnosti(listaAktivnosti.get(position).id).kraj)
        }
    }

    override fun getItemCount(): Int {
        return listaAktivnosti.size
    }

    class AktivnostiViewHolder(itemView: View, onElementListener: OnElementListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var naziv : TextView
        var editBroj : TextView
        var broj : TextView
        var editInkrement : TextView
        var inkrement : TextView
        var editKolicina : TextView
        var kolicina : TextView
        var editPocetak : TextView
        var pocetak : TextView
        var editKraj : TextView
        var kraj : TextView
        var editMjernaJedinica : TextView
        var mjernaJedinica : TextView
        var mOnElementListener: OnElementListener

        override fun onClick(view: View) {
            Log.d(ContentValues.TAG, "onClick: $adapterPosition")
            mOnElementListener.onElementClick(adapterPosition)
        }

        init {
            naziv = itemView.findViewById(R.id.naziv)
            kolicina = itemView.findViewById(R.id.kolicina)
            kolicina.setText(R.string.kolicina)
            editKolicina = itemView.findViewById(R.id.editKolicina)
            broj = itemView.findViewById(R.id.broj)
            broj.setText(R.string.broj)
            editBroj = itemView.findViewById(R.id.editBroj)
            inkrement = itemView.findViewById(R.id.inkrement)
            inkrement.setText(R.string.inkrement)
            editInkrement = itemView.findViewById(R.id.editInkrement)
            pocetak = itemView.findViewById(R.id.pocetak)
            pocetak.setText(R.string.pocetak)
            editPocetak = itemView.findViewById(R.id.editPocetak)
            kraj = itemView.findViewById(R.id.kraj)
            kraj.setText(R.string.kraj)
            editKraj = itemView.findViewById(R.id.editKraj)
            editMjernaJedinica = itemView.findViewById(R.id.editMJ)
            mjernaJedinica = itemView.findViewById(R.id.mjernaJedinica)
            mjernaJedinica.setText(R.string.mjernaJedinica)

            mOnElementListener = onElementListener
            itemView.setOnClickListener(this)
        }
    }

    interface OnElementListener{
        fun onElementClick(position : Int);
    }

    init {
        this.listaAktivnosti = listaAktivnosti
        this.mOnElementListener = mOnElementListener
        this.tip = tip
    }
}

