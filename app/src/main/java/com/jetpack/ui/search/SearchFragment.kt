package com.jetpack.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jetpack.data.Resource
import com.jetpack.data.dto.Breweries
import com.jetpack.databinding.FragmentSearchBinding
import com.jetpack.ui.common.BaseFragment
import com.jetpack.ui.search.adapter.SearchAdapter
import com.jetpack.utils.onTextChange
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
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
//        viewModel.searchViewState.observe(viewLifecycleOwner) { handleBreweryList(it) }
        lifecycleScope.launch {
            viewModel.viewState
                .collect {
                    handleBreweryList(it)
                }
        }
    }

    override fun init() {
        with(viewBinding.rvBreweyList){
            this.adapter = searchAdapter
        }
        viewBinding.progressLoader.hide()
        viewBinding.searchView.onTextChange { newText ->
            viewModel.onViewEvent(newText.orEmpty())
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