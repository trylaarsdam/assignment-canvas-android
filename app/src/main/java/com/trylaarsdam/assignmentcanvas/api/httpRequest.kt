package com.trylaarsdam.assignmentcanvas.api

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.authentication

fun apiRequest(endpoint: String) {
    Fuel.get("https://canvasapi.toddr.org/$endpoint")
        .authentication()
        .basic(username, password)
        .response { request, response, result ->
            println(request)
            println(response)
            val (bytes, error) = result
            if (bytes != null) {
                println("[response bytes] ${String(bytes)}")
            }
        }

}