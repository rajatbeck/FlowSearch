package com.jetpack.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jetpack.R
import com.jetpack.data.Resource
import com.jetpack.data.dto.Breweries
import com.jetpack.databinding.FragmentSearchBinding
import com.jetpack.ui.common.BaseFragment
import com.jetpack.ui.search.adapter.SearchAdapter
import com.jetpack.utils.onTextChange
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    @Inject lateinit var searchAdapter: SearchAdapter
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var viewBinding: FragmentSearchBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSearchBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun observe() {
        viewModel.searchViewState.observe(viewLifecycleOwner) { handleBreweryList(it) }
    }

    override fun init() {
        with(viewBinding.rvBreweyList){
            this.adapter = searchAdapter
        }
        viewBinding.progressLoader.hide()
        viewBinding.searchView.onTextChange { newText ->
            viewModel.searchQueryEvent(newText.orEmpty())
            false
        }
    }

    private fun showLoading(){
        viewBinding.progressLoader.show()
    }

    private fun showSuccess(list: List<Breweries>){
        viewBinding.progressLoader.hide()
        searchAdapter.submitList(list)
    }

    private fun showError(){
        viewBinding.progressLoader.hide()
    }

    private fun handleBreweryList(result: Resource<List<Breweries>>) {
        when (result) {
            is Resource.Loading -> showLoading()
            is Resource.Success -> showSuccess(result.data.orEmpty())
            is Resource.DataError -> showError()
        }
    }
}