package com.mylibrary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController


class main_fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val searchbtn: Button = view.findViewById(R.id.searchbtn)

        searchbtn.setOnClickListener {
            findNavController().navigate(R.id.action_main_fragment_to_search_fragment)
        }

        return view
    }
}