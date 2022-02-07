package com.example.projekat.ui.achievements

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projekat.R

class PostignucaFragment : Fragment() {

    companion object {
        fun newInstance() = PostignucaFragment()
    }

    private lateinit var viewModel: PostignucaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.postignuca_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PostignucaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}