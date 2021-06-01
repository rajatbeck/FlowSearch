package com.jetpack.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jetpack.databinding.FragmentSearchBinding
import com.jetpack.ui.common.BaseFragment
import com.jetpack.ui.search.adapter.SearchAdapter
import com.jetpack.utils.onTextChange
import com.rajat.domain.model.Breweries
import com.rajat.domain.model.ResultWrapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
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

    private fun handleBreweryList(result: ResultWrapper<List<Breweries>>) {
        when (result) {
            is ResultWrapper.Loading -> showLoading()
            is ResultWrapper.Success -> showSuccess(result.value)
            is ResultWrapper.Error -> showError()
        }
    }
}