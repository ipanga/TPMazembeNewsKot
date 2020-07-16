package com.tootiyesolutions.tpmazembe.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tootiyesolutions.tpmazembe.R
import com.tootiyesolutions.tpmazembe.ui.SearchNewsViewModel

class SearchNewsFragment : Fragment() {

    private lateinit var dashboardViewModel: SearchNewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(SearchNewsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search_news, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}