package com.example.projekat.ui.notes

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projekat.R

class NapomeneFragment : Fragment() {

    companion object {
        fun newInstance() = NapomeneFragment()
    }

    private lateinit var viewModel: NapomeneViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.napomene_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NapomeneViewModel::class.java)
        // TODO: Use the ViewModel
    }

}