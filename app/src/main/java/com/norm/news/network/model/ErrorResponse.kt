package com.norm.news.network.model

data class ErrorResponse(
    val status: String,
    val code: String,
    val message: String
)