package com.jetpack.di

import androidx.fragment.app.Fragment
import com.jetpack.ui.search.adapter.SearchAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @Provides
    fun providesSearchAdapter(fragment: Fragment): SearchAdapter = SearchAdapter()
}