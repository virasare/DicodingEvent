package com.dicoding.dicodingevent.core.data.remote

sealed class ApiResponse<out T> {
    data class Success<T>(val data: T): ApiResponse<T>()
    data class Error(val errorMessage: String): ApiResponse<Nothing>()
    object Empty: ApiResponse<Nothing>()
}