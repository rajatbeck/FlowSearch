package com.jetpack.utils

import android.view.View
import androidx.appcompat.widget.SearchView

fun SearchView.onTextChange(
    onQueryTextSubmitCallback:(String?)->Boolean = {false},
    onQueryTextChangeCallback: (String?) -> Boolean = { false }
){
   this.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
       override fun onQueryTextSubmit(query: String?): Boolean {
           return onQueryTextSubmitCallback.invoke(query)
       }

       override fun onQueryTextChange(newText: String?): Boolean {
           return onQueryTextChangeCallback.invoke(newText)
       }

   })
}

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toInvisible() {
    this.visibility = View.GONE
}



