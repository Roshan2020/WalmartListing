package com.mysampleapp.repository

import com.mysampleapp.utils.Result
import com.mysampleapp.network.APIService
import com.mysampleapp.model.Country
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CountryRepositoryImpl @Inject constructor(private val apiService: APIService) :
    CountryRepository {

    override suspend fun getCountries(): Result {
        return try {
            val response = apiService.getCountries()
            if (response.isSuccessful) {
                Result.Success(response.body() ?: emptyList<Country>())
            } else {
                Result.Error("HTTP Error: ${response.code()}")
            }
        } catch (e: IOException) {
            Result.Error("Network Error: ${e.message}")
        } catch (e: HttpException) {
            Result.Error("HTTP Error: ${e.message}")
        } catch (e: Exception) {
            Result.Error("Error: ${e.message}")
        }
    }
}
