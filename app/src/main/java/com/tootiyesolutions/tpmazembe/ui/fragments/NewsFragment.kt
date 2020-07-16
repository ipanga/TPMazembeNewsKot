package com.tootiyesolutions.tpmazembe.ui.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.DividerItemDecoration
import com.tootiyesolutions.tpmazembe.Injection
import com.tootiyesolutions.tpmazembe.R
import com.tootiyesolutions.tpmazembe.adapter.NewsAdapter
import com.tootiyesolutions.tpmazembe.databinding.FragmentNewsBinding
import com.tootiyesolutions.tpmazembe.ui.NewsViewModel
import com.tootiyesolutions.tpmazembe.util.Constants.Companion.DEFAULT_QUERY
import com.tootiyesolutions.tpmazembe.util.Constants.Companion.LAST_SEARCH_QUERY
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {

    // This property is only valid between onCreateView and onDestroyView.
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NewsViewModel
    private val adapter = NewsAdapter()

    private var searchJob: Job? = null

    private fun search(query: String) {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchNews(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // val root = inflater.inflate(R.layout.fragment_news, container, false)

        // binding = FragmentNewsBinding.inflate(inflater, container, false)
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val view = binding.root
        // setContentView(view)

        // get the view model
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
            .get(NewsViewModel::class.java)

        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.rvListNews.addItemDecoration(decoration)

        initAdapter()
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        search(query)
        initSearch(query)

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, binding.etSearchNews.text.trim().toString())
    }

    private fun initAdapter() {
        binding.rvListNews.adapter = adapter
    }

    private fun initSearch(query: String) {
        binding.etSearchNews.setText(query)

        binding.etSearchNews.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        binding.etSearchNews.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }

        lifecycleScope.launch {
            @OptIn(ExperimentalPagingApi::class)
            adapter.dataRefreshFlow.collect {
                binding.rvListNews.scrollToPosition(0)
            }
        }
    }

    private fun updateRepoListFromInput() {
        binding.etSearchNews.text.trim().let {
            if (it.isNotEmpty()) {
                search(it.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}