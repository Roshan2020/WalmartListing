package com.mysampleapp.repository

import com.mysampleapp.utils.Result

interface CountryRepository {
    suspend fun getCountries(): Result
}
