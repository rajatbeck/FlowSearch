package com.jetpack.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jetpack.data.Resource
import com.jetpack.data.SearchRepository
import com.jetpack.data.dto.Breweries
import com.jetpack.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository): BaseViewModel(),SearchQueryEvents {

    private val searchEvent: Channel<String> = Channel(Channel.UNLIMITED)
    init {
        viewModelScope.launch {
            performSearch()
        }
    }

    private val _searchViewState:MutableLiveData<Resource<List<Breweries>>> = MutableLiveData()
    val searchViewState:LiveData<Resource<List<Breweries>>> = _searchViewState

    private suspend fun performSearch(){
        searchEvent.consumeAsFlow()
            .debounce(200)
            .filter { it.isNotEmpty() and it.isNotBlank() }
            .distinctUntilChanged()
            .flatMapLatest {
                repository.searchBreweries(searchQuery = it)
                    .onStart { emit(Resource.Loading()) }
            }
            .collect {
                _searchViewState.value = it
            }
    }

    override fun searchQueryEvent(query: String) {
        viewModelScope.launch {
            searchEvent.offer(query)
        }
    }

    override fun onCleared() {
        super.onCleared()
        searchEvent.close()
    }
}



interface SearchQueryEvents{
    fun searchQueryEvent(query:String)
}

sealed class SearchViewState {
    object Loading : SearchViewState()
    class UpdateList(list: List<Breweries>) : SearchViewState()
    class Error(message: String) : SearchViewState()
}