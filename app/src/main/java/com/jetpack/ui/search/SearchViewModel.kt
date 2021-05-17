package com.jetpack.ui.search

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jetpack.data.Resource
import com.jetpack.data.SearchRepository
import com.jetpack.data.dto.Breweries
import com.jetpack.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository) :
    BaseViewModel(), SearchQueryEvents {

    private val _searchEvent: MutableSharedFlow<String> = MutableSharedFlow()
    val searchEvent: SharedFlow<String>
        get() = _searchEvent

    private val _viewState = MutableStateFlow<Resource<List<Breweries>>>(Resource.Loading)
    val viewState: StateFlow<Resource<List<Breweries>>>
        get() = _viewState

    init {
        viewModelScope.launch {
            processSearchText()
        }
    }


//    suspend fun performSearch() {
//        processSearchText()
//            .flatMapLatest {
//                repository.searchBreweries(searchQuery = it)
//            }
//            .onStart { emit(Resource.Loading()) }
//            .collect {
//                _searchViewState.value = it
//            }
//    }

    private suspend fun processSearchText() {
        searchEvent
//            .filter { it.isNotEmpty() and it.isNotBlank() }
            .distinctUntilChanged()
            .debounce(200)
            .mapLatest {
                repository.searchBreweries(searchQuery = it)
            }
            .onStart { emit(Resource.Loading) }
            .collect {
                _viewState.value = it
            }
    }

    // A | AB | ABC
    //  200 A | 200 AB
    override fun onViewEvent(query: String) {
        viewModelScope.launch {
            _searchEvent.emit(query)
        }
    }

}


interface SearchQueryEvents {
    fun onViewEvent(query: String)
}

sealed class SearchViewState {
    object Loading : SearchViewState()
    class UpdateList(list: List<Breweries>) : SearchViewState()
    class Error(message: String) : SearchViewState()
}