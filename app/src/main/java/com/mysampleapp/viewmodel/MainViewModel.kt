package com.mysampleapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysampleapp.model.Country
import com.mysampleapp.repository.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.mysampleapp.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val countryRepository: CountryRepository
) : ViewModel() {

    private val _countries = MutableStateFlow<Result>(Result.Success(emptyList<Country>()))
    val countries: StateFlow<Result> = _countries

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    fun getCountriesFromRepository() {
        _isLoading.value = true
        viewModelScope.launch {
            when (val result = countryRepository.getCountries()) {
                is Result.Success -> {
                    _countries.value = result
                }
                is Result.Error -> {
                    _errorMessage.value = result.message
                }
            }
            _isLoading.value = false
        }
    }
}