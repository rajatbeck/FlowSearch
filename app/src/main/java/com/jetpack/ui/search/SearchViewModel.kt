package com.jetpack.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetpack.ui.common.BaseViewModel
import com.rajat.domain.model.Breweries
import com.rajat.domain.model.ResultWrapper
import com.rajat.domain.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchUseCase: SearchUseCase) :
    BaseViewModel(), SearchQueryEvents {

    private val _searchEvent: MutableSharedFlow<String> = MutableSharedFlow()
    private val searchEvent: SharedFlow<String>
        get() = _searchEvent

    private val _viewState = MutableStateFlow<ResultWrapper<List<Breweries>>>(ResultWrapper.Success(listOf()))
    val viewState: StateFlow<ResultWrapper<List<Breweries>>>
        get() = _viewState

    init {
        viewModelScope.launch {
            processSearchText()
        }
    }


    private suspend fun processSearchText() {
        searchEvent
            .debounce(200)
            .distinctUntilChanged()
            .mapLatest { searchUseCase.performSearch(it) }
            .collect {
                _viewState.value = it
            }
    }


    override fun onViewEvent(query: String) {
        viewModelScope.launch {
            _viewState.value = ResultWrapper.Loading
            _searchEvent.emit(query)
        }
    }

}


interface SearchQueryEvents {
    fun onViewEvent(query: String)
}

