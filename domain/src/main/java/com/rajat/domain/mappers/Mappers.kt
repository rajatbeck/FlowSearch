package com.rajat.domain.mappers

interface Mapper<I, O> {
    fun map(input: I): O
}