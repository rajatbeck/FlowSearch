package com.rajat.data.mapper

import com.rajat.data.Brewery
import com.rajat.domain.mappers.Mapper
import com.rajat.domain.model.Breweries
import javax.inject.Inject

class SearchResponseMapper @Inject constructor() : Mapper<Brewery, Breweries> {
    override fun map(input: Brewery): Breweries {
        return Breweries(id = input.id, name = input.name)
    }
}