package com.trylaarsdam.assignmentcanvas.api

import android.content.ContentValues.TAG
import android.content.Context
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.awaitResponse
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.github.kittinunf.fuel.httpGet

suspend fun apiRequest(endpoint: String, context: Context): String {
    val sharedPref = context.getSharedPreferences("com.trylaarsdam.assignmentcanvas.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)

//    val email = sharedPref.getString("email", "null")
//    val password = sharedPref.getString("password", "null")

    val (request, response, result) = Fuel.get("https://canvasapi.toddr.org/$endpoint")
        .authentication()
        .basic(apiUsername, apiPassword)
        .awaitStringResponseResult()

    Log.d(TAG, String(response.data))
    return String(response.data)
}

suspend fun apiRequestNoAuth(endpoint: String): String {
    val (request, response, result) = Fuel.get("https://canvasapi.toddr.org/$endpoint")
        .awaitStringResponseResult()

    Log.d(TAG, String(response.data))
    return String(response.data)
}