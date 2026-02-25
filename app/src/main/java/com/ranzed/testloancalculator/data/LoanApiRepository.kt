package com.ranzed.testloancalculator.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

interface LoanApiRepository {
    suspend fun submitLoan(amount: Int, period: Int, totalRepayment: Double): Result<Unit>
}

class LoanApiRepositoryImpl(
    private val client: OkHttpClient
) : LoanApiRepository {

    override suspend fun submitLoan(amount: Int, period: Int, totalRepayment: Double): Result<Unit> =
        withContext(Dispatchers.IO) {
            val json = JSONObject().apply {
                put("amount", amount)
                put("period", period)
                put("totalRepayment", totalRepayment)
            }
            val request = Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts")
                .post(json.toString().toRequestBody("application/json".toMediaType()))
                .build()

            // NOTE: delay to emitate heavy request and see progressbar on button
            delay(1000)
            runCatching {
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) error("HTTP ${response.code}")
                }
            }
        }
}
